package com.tingting.ver01.teamInfo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.analytics.FirebaseAnalytics
import com.tingting.ver01.BR.viewmodel
import com.tingting.ver01.view.Main.MainActivity
import com.tingting.ver01.view.Main.ProfileDetailActivity
import com.tingting.ver01.view.Main.SettingsActivity
import com.tingting.ver01.databinding.ProfileFragmentBinding
import com.tingting.ver01.model.profile.ModelProfile
import com.tingting.ver01.profileTeamInfo.ProflieTeamInfoAdapter
import com.tingting.ver01.profileTeamInfo.profileApply.ProfileResponseReAdapter
import com.tingting.ver01.sharedPreference.App
import com.tingting.ver01.viewModel.ProfileFragmentViewModel
import kotlinx.android.synthetic.main.activity_profile_not_ready.*
import kotlinx.android.synthetic.main.profile_fragment.*


class ProfileFragment : Fragment() {

    var model: ModelProfile = ModelProfile(activity)
    lateinit var myResponseAdapter: ProfileResponseReAdapter
    lateinit var myTeamAdapter: ProflieTeamInfoAdapter
    lateinit var dataBinding: ProfileFragmentBinding

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val bundle = Bundle()
        firebaseAnalytics = FirebaseAnalytics.getInstance(activity!!.applicationContext)
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, App.prefs.mylocal_id.toString())
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
        initShared()


        dataBinding = ProfileFragmentBinding.inflate(inflater, container, false).apply {
            viewmodel = ViewModelProviders.of(this@ProfileFragment).get(ProfileFragmentViewModel::class.java)
            lifecycleOwner = viewLifecycleOwner
        }

        //setting
        dataBinding.settings.setOnClickListener {
            val intent = Intent(activity!!.applicationContext, SettingsActivity::class.java)
            startActivity(intent)
        }

        dataBinding.ProfileEdit.setOnClickListener {
            val intent = Intent(activity!!.applicationContext, ProfileDetailActivity::class.java)
            startActivity(intent)

        }

        return dataBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.viewmodel?.fetchuserInfo()

        setObserver()
        setTeamInfoAdapter()
        setApplyAdapter()
    }

    private fun setObserver() {
        dataBinding.viewmodel?.profileUserLiveData?.observe(viewLifecycleOwner, Observer {
            myTeamAdapter.updateTeamData(it)
            myResponseAdapter.updateData(it)
        })

    }

    private fun setTeamInfoAdapter() {
        val viewModel = dataBinding.viewmodel

        if (viewModel != null) {
            myTeamAdapter = ProflieTeamInfoAdapter(dataBinding.viewmodel!!,activity!!.applicationContext)
            val layoutManager = LinearLayoutManager(activity)
            dataBinding.newteamRecyclerView1.layoutManager = layoutManager
            dataBinding.newteamRecyclerView1.adapter = myTeamAdapter
        }

    }

    private fun setApplyAdapter() {
        val viewModel = dataBinding.viewmodel

        if (viewModel != null) {
            val deco = TeamInfoRecyclerViewMargin(10)
            dataBinding.newteamRecyclerView2.addItemDecoration(deco)
            myResponseAdapter = ProfileResponseReAdapter(dataBinding.viewmodel!!,activity!!.applicationContext)
            val layoutManager = LinearLayoutManager(activity)
            dataBinding.newteamRecyclerView2.layoutManager = layoutManager
            dataBinding.newteamRecyclerView2.adapter = myResponseAdapter
        }
    }

    //메인의 onResuem()이 실행되고 fragmentOnReume
    override fun onResume() {
        super.onResume()
        MainActivity.allowRefreshProfile = true
        MainActivity.allowRefreshMatching = false
        MainActivity.allowRefreshSearch = false

    }

    fun initShared() {
        App.prefs.mythumnail = ""
        App.prefs.mybirth = ""
        App.prefs.myheight = ""
        App.prefs.myauthenticated_address = ""
        App.prefs.myautoLogin = "true"
        App.prefs.myisMaking = "false"

    }

    //Binding Adapter는 compainion object로 실행해줘야 하는구나..!
    companion object{
        @BindingAdapter("imageUrl")
        @JvmStatic
        fun getimgurl1( view:ImageView?, url : String?){
            if(view!=null && url !=null){
                MainActivity.glide.setImage(view.context,
                    MainActivity.glide.DecryptUrl(url),view)
            }
        }
    }

}
