package com.tingting.ver01.TeamInfo

import GetProfileResponse
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.analytics.FirebaseAnalytics
import com.kakao.usermgmt.StringSet.name
import com.tingting.ver01.Matching.MatchingRequestTeamInfo
import com.tingting.ver01.Model.Profile.ModelProfile
import com.tingting.ver01.Model.Profile.SentMatchingsCallback
import com.tingting.ver01.Model.ProfileCallBack
import com.tingting.ver01.ProfileResponseRequest.ProfileResponseReAdapter
import com.tingting.ver01.ProfileResponseRequest.ProfileResponseReData
import com.tingting.ver01.ProfileTeamInfo.ProfileTeamInfoData
import com.tingting.ver01.ProfileTeamInfo.ProflieTeamInfoAdapter
import com.tingting.ver01.R
import com.tingting.ver01.SharedPreference.App
import com.tingting.ver01.View.MainActivity
import com.tingting.ver01.View.MainActivity.Companion.glide
import com.tingting.ver01.View.ProfileDetailActivity
import com.tingting.ver01.View.SettingsActivity
import kotlinx.android.synthetic.main.profile_fragment.*
import kotlinx.android.synthetic.main.profile_fragment.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig
import uk.co.deanwild.materialshowcaseview.shape.OvalShape


class ProfileFragment : Fragment() {

    var model: ModelProfile = ModelProfile(activity)
    var teamList = arrayListOf<ProfileTeamInfoData>()
    var requestData  = arrayListOf<ProfileResponseReData>()
    var receiveTeamId:Int = 0
    var sentmyTeamId:Int = 0
    lateinit var profileEdit:LinearLayout
    lateinit var myTeamlist:TextView
    lateinit var requestlist:TextView
    lateinit var Readapter :ProfileResponseReAdapter
    lateinit var MyTeamAdapter : ProflieTeamInfoAdapter

    val SHOWCASE_ID:String = "tooltip_profile"

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

        val view = inflater.inflate(R.layout.profile_fragment, null)

        profileEdit = view.findViewById(R.id.ProfileEdit)
        myTeamlist = view.findViewById(R.id.newteamTeamlistTV)
        requestlist = view.findViewById(R.id.newteamRequestlistTV)
        // settings
        view.settings.setOnClickListener {
            val intentSetting = Intent(activity, SettingsActivity::class.java)
            activity!!.startActivity(intentSetting)
        }

        // move to detail profile fragment
        view.ProfileEdit.setOnClickListener() {

            val intent = Intent(activity, ProfileDetailActivity::class.java)
            activity!!.startActivity(intent)
        }

        /*view.reset.setOnClickListener {
            MaterialShowcaseView.resetSingleUse(activity, SHOWCASE_ID)

        }*/
        var myTeamdata: List<GetProfileResponse.Data.MyTeam> = listOf()

        teamList = arrayListOf<ProfileTeamInfoData>()

        MyTeamAdapter =  ProflieTeamInfoAdapter(activity!!.applicationContext, teamList)

        //프로파일 기본 init
        model.getProfile(App.prefs.myToken.toString(), object : ProfileCallBack {
            override fun onSuccess(
                name: String,
                birth: String,
                height: String,
                thumnail: String,
                gender: String,
                data: List<GetProfileResponse.Data.MyTeam>
            ) {

                myTeamdata = data

                App.prefs.mygender = gender
                teamList.clear()
                val scope = CoroutineScope(Dispatchers.Main)
                runBlocking {
                    scope.launch {
                        for (i in 0..myTeamdata.size - 1) {
                            //if(myTeamdata.get(i).)
                            teamList.add(
                                ProfileTeamInfoData(
                                    myTeamdata.get(i).name,
                                    myTeamdata.get(i).id,
                                    false
                                )
                            )
                            MyTeamAdapter.notifyDataSetChanged()
                        }
                        try {
                        myTeamMessageView(teamList, alertNoTeam)
                        }catch (e : Exception){
                        e.printStackTrace()
                    }

                    }
                }

                //this is code for teamList
                //this is testcode.

                newteamTeamlistTV.setText(name+"님의 팀")
                NickName_View.setText(name+" 님")
                App.prefs.myname = name

                val glideUrl = glide.DecryptUrl(thumnail)

                context?.let { glide.setImage(it, glideUrl, view.newteamProfileImg) }

            }

        })


        MyTeamAdapter.itemClick = object : ProflieTeamInfoAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {

                var intent = Intent(activity, TeamInfoActivity::class.java)
                intent.putExtra("MyTeamId", teamList.get(position).id)

                startActivity(intent)

            }
        }

        //teamName 넘김 --> 서버에서 teamName이랑 일치하는 정보 받아온 후 화면에 띄워줌.


        val deco = ProfileTeamInfoMargin(5)
        view.newteamRecyclerView1.addItemDecoration(deco)
        view.newteamRecyclerView1.adapter = MyTeamAdapter

        val lm = LinearLayoutManager(activity!!.applicationContext)
        view.newteamRecyclerView1.layoutManager = lm
        view.newteamRecyclerView1.setHasFixedSize(true)


        // 응답요청
        var requestData = arrayListOf<ProfileResponseReData>()

        Readapter = ProfileResponseReAdapter(activity!!.applicationContext,requestData)

        var applyTeamIntent = Intent(activity, MatchingRequestTeamInfo::class.java)

        model.getSentMatchings(App.prefs.myToken.toString(), object :SentMatchingsCallback{
            override fun sentMatchings(data: GetProfileResponse) {
                var coroutineScope:CoroutineScope = CoroutineScope(Dispatchers.Main)
                runBlocking {
                    coroutineScope.launch {
                        try{
                            for(i in 0..data.data.sentMatchings.size-1){
                                requestData.add(ProfileResponseReData(data.data.sentMatchings.get(i).receiveTeam.name))


                            Readapter.notifyDataSetChanged()

                            sentTeamMessageView(requestData, alertNoApply)
                        }
                    }catch (e:Exception){
                        }
                }

            }
        }})

        Readapter.itemClick = object : ProfileResponseReAdapter.ItemClick{
            override fun Onclick(view: View, position: Int) {
                model.getSentMatchings(App.prefs.myToken.toString(), object:SentMatchingsCallback{

                    override fun sentMatchings(data: GetProfileResponse) {
                        var sentmyTeamName = data.data.sentMatchings.get(position).sendTeam.id
                        var receiveTeamName = data.data.sentMatchings.get(position).receiveTeam.id
                        var matchingId = data.data.sentMatchings.get(position).id

                        applyTeamIntent.putExtra("myTeamId", sentmyTeamName)
                        applyTeamIntent.putExtra("matchingTeamId", receiveTeamName)
                        applyTeamIntent.putExtra("matchingId", matchingId)

                        startActivity(applyTeamIntent)

                    }
                })
            }

        }

        view.newteamRecyclerView2.addItemDecoration(deco)
        view.newteamRecyclerView2.adapter = Readapter

        val lm2 = LinearLayoutManager(activity!!.applicationContext)
        view.newteamRecyclerView2.layoutManager = lm2
        view.newteamRecyclerView2.setHasFixedSize(true)

        presentShowcaseView()

        return view
    }

    fun presentShowcaseView(){

        val showcaseConfig: ShowcaseConfig = ShowcaseConfig()
        showcaseConfig.delay = 1000

        val sequence: MaterialShowcaseSequence = MaterialShowcaseSequence(activity, SHOWCASE_ID)
        sequence.setConfig(showcaseConfig)

        sequence.addSequenceItem(MaterialShowcaseView.Builder(activity)
            .setSkipText("건너뛰기")
            .setTarget(myTeamlist)
            .setDismissText("닫기")
            .setMaskColour(resources.getColor(R.color.tooltip_mask))
            .setDelay(0)
            .setContentText("내가 속한 팀 혹은 만든 팀이 이곳에 표시됩니다.")
            .setDismissTextColor(Color.parseColor("#EAEAEA"))
            .setContentTextColor(Color.parseColor("#EAEAEA"))
            .build())

        sequence.addSequenceItem(
            MaterialShowcaseView.Builder(activity)
            .setTarget(requestlist)
            .setDismissText("닫기")
                .setMaskColour(resources.getColor(R.color.tooltip_mask))
                .setDelay(100)
                .setContentText("팀원이 하트를 보낸 팀을 수락 또는 거절 할 수 있습니다.")
            .setDismissTextColor(Color.parseColor("#EAEAEA"))
            .setContentTextColor(Color.parseColor("#EAEAEA"))
            .build())

        sequence.start()
        /*MaterialShowcaseView.Builder(activity)
            .setTarget(myTeamlist)
            .setShape(object : OvalShape() {
                override fun setAdjustToTarget(adjustToTarget: Boolean) {

                }
            })
            .setContentText("속한 팀 혹은 만든 팀이 이곳에 표시됩니다.")
            .setDismissText("닫기")
            .setDismissTextColor(Color.parseColor("#EAEAEA"))
            .setContentTextColor(Color.parseColor("#EAEAEA"))
            .setMaskColour(resources.getColor(R.color.tooltip_mask))
            .setDelay(0)
            .singleUse(SHOWCASE_ID)
            .show()*/
    }

    //메인의 onResuem()이 실행되고 fragmentOnReume
    override fun onResume() {
        super.onResume()

        MainActivity.allowRefreshProfile = true;
        MainActivity.allowRefreshMatching =false;
        MainActivity.allowRefreshSearch = false;


    }

    fun myTeamMessageView(lsize: List<Any>, view: TextView) {


        if(lsize.size!=0){
            view.visibility=View.INVISIBLE

        }
    }

    fun sentTeamMessageView(stsize:List<Any>, view:TextView){

        if(stsize.size!=0){
            view.visibility = View.INVISIBLE
        }
    }

    fun initShared() {
        App.prefs.mythumnail = ""
        App.prefs.mybirth = ""
        App.prefs.myheight = ""
        App.prefs.myauthenticated_address = ""
        App.prefs.myautoLogin = "true"
        App.prefs.myisMaking="false"

    }



}
