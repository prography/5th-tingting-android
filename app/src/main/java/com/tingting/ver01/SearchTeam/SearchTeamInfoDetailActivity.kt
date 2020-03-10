package com.tingting.ver01.SearchTeam

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tingting.ver01.Matching.FirstPagerFragment
import com.tingting.ver01.Matching.TeamInfoPagerAdapter
import com.tingting.ver01.model.ModelTeam
import com.tingting.ver01.model.profile.GetTeammberProfileResponse
import com.tingting.ver01.model.profile.ModelProfile
import com.tingting.ver01.model.ProfileCallBack
import com.tingting.ver01.model.team.LookIndivisualTeam.IndivisualTeamResponse
import com.tingting.ver01.model.TeamDataCallback
import com.tingting.ver01.R
import com.tingting.ver01.sharedPreference.App
import kotlinx.android.synthetic.main.activity_matching_request.MatchingViewPager
import kotlinx.android.synthetic.main.activity_team_info_detail.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.util.*

class SearchTeamInfoDetailActivity : AppCompatActivity() {

    val model: ModelTeam = ModelTeam(this)
    val pModel : ModelProfile = ModelProfile(contextA = this)

    lateinit var adapter : TeamInfoPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_info_detail)

        var info: IndivisualTeamResponse?


        backButton.setOnClickListener(){
            finish()
        }

        adapter= TeamInfoPagerAdapter(supportFragmentManager)
        MatchingViewPager.adapter=adapter
        tab.setupWithViewPager(MatchingViewPager)


        var a = intent.getIntExtra("MyTeamId", 0)

        //init screen data
        model.showIndivisualTeamList(App.prefs.myToken.toString(),a, object : TeamDataCallback {
            override fun onIndivisualResult(data: IndivisualTeamResponse?, start: Int, end: Int) {
                info = data

                //adapter set Data
                runBlocking {
                    for (i in 0..info!!.data.teamMembers.size -1) {
                        pModel.getTeammemberInfo(
                            info!!.data.teamMembers.get(i).id,
                            object : ProfileCallBack {
                                override fun onTeammemberProfileSuccess(data: GetTeammberProfileResponse) {
                                    var scope = CoroutineScope(Dispatchers.Main)

                                    adapter.addItem(
                                        FirstPagerFragment(
                                            data.data.userInfo.thumbnail,
                                            data.data.userInfo.name,
                                            calAge(data.data.userInfo.birth),
                                            data.data.userInfo.height.toString(),
                                            data.data.userInfo.schoolName
                                        )
                                    )
                                    adapter.notifyDataSetChanged()
                                }
                            })

                    }
                    MatchingViewPager.setCurrentItem(0)
                    adapter.notifyDataSetChanged()
                }

            }
        })

    }

    fun calAge(age : String) : String{
        var year = Calendar.getInstance().get(Calendar.YEAR)
        var b = age.substring(0,4);
        var peopleAge = year - b.toInt()+1

        return peopleAge.toString()
    }
}
