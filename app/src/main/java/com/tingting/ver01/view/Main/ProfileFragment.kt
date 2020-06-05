package com.tingting.ver01.teamInfo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.google.gson.Gson
import com.tingting.ver01.R
import com.tingting.ver01.databinding.ProfileFragmentBinding
import com.tingting.ver01.model.profile.ModelProfile
import com.tingting.ver01.profileTeamInfo.ProflieTeamInfoAdapter
import com.tingting.ver01.profileTeamInfo.profileApply.ProfileResponseReAdapter
import com.tingting.ver01.sharedPreference.App
import com.tingting.ver01.socket.NotificationMessage
import com.tingting.ver01.socket.SocketListener
import com.tingting.ver01.socket.socketData
import com.tingting.ver01.view.Auth.LoginActivity
import com.tingting.ver01.view.Main.MainActivity
import com.tingting.ver01.view.Main.ProfileDetailActivity
import com.tingting.ver01.view.Main.SearchTeamFragment
import com.tingting.ver01.view.Main.SettingsActivity
import com.tingting.ver01.viewModel.ProfileFragmentViewModel
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject


class ProfileFragment : Fragment() {

    var model: ModelProfile = ModelProfile(activity)
    lateinit var myResponseAdapter: ProfileResponseReAdapter
    lateinit var myTeamAdapter: ProflieTeamInfoAdapter
    lateinit var dataBinding: ProfileFragmentBinding
    private lateinit var firebaseAnalytics: FirebaseAnalytics
   lateinit var fragContext: Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val bundle = Bundle()
        firebaseAnalytics = FirebaseAnalytics.getInstance(activity!!.applicationContext)
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, App.prefs.mylocal_id.toString())
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
        initShared()

        fragContext = activity!!.applicationContext


        dataBinding = ProfileFragmentBinding.inflate(inflater, container, false).apply {
            viewmodel = ViewModelProviders.of(this@ProfileFragment).get(ProfileFragmentViewModel::class.java)
            lifecycleOwner = viewLifecycleOwner
        }

        //setting
        dataBinding.settings.setOnClickListener {
            val intent = Intent(activity!!.applicationContext, SettingsActivity::class.java)
            startActivity(intent)
        }

        dataBinding.newteamEditProfileTV.setOnClickListener {
            val intent = Intent(activity!!.applicationContext, ProfileDetailActivity::class.java)
            startActivity(intent)

        }

        dataBinding.searchTeamBtn.setOnClickListener(){
            activity!!.supportFragmentManager.beginTransaction().replace(
                R.id.mainFragment,
                SearchTeamFragment()
            ).commit()

            activity!!.searchTeam.setImageResource(R.drawable.support_pink)
            activity!!.searchTeamText.setTextColor(resources.getColor(R.color.tingtingMain))
            activity!!.profile.setImageResource(R.drawable.user)
            activity!!.profileText.setTextColor(resources.getColor(R.color.gray))

        }

//        dataBinding.imageView.setOnClickListener(){
//           var message = NotificationMessage(activity!!.applicationContext)
//            message.createNotificationChannel()
//            message.makeMessage("매칭 되었습니다.")
//            emitData()
//        }

        socketConnect()

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
            MainActivity.gender = dataBinding.viewmodel?.profileUserLiveData!!.value!!.data.myInfo.gender
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
        Log.d("OnResumeProfile","OnResumeProfile")

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

    fun socketConnect(){

            var so = SocketListener()
            Log.d("connectEmmit","Test")
            LoginActivity.msocket.on("matched",matched)
            LoginActivity.msocket.on("disconnect", so.onReConnect)
            LoginActivity.msocket.on("load",so.onReLoad)

    }

    fun emitData(){

        val data = socketData("1")
        LoginActivity.msocket.emit("enroll", Gson().toJson(data))
        Log.d("testEmmit", Gson().toJson(data).toString())

    }

    val matched =
        Emitter.Listener { args ->

            try {

                val data = args[0] as JSONObject
                Log.d("socketData",data.toString())

               GlobalScope.launch(Dispatchers.Main) {

             Log.d("socketDatatt",fragContext.toString())
            var message = NotificationMessage(fragContext)
            message.createNotificationChannel()
            message.makeMessage("매칭 되었습니다.")


        }

            } catch (e: JSONException) {

            }

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
