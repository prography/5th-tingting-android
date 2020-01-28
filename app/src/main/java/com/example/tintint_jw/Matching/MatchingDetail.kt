package com.example.tintint_jw.Matching


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.tintint_jw.Model.Matching.ShowMatchingTeamInfoResponse
import com.example.tintint_jw.Model.ModelMatching
import com.example.tintint_jw.Model.Profile.GetTeammberProfileResponse
import com.example.tintint_jw.Model.Profile.ModelProfile
import com.example.tintint_jw.Model.ProfileCallBack
import com.example.tintint_jw.Model.TeamDataCallback
import com.example.tintint_jw.R
import kotlinx.android.synthetic.main.activity_matching_detail.*
import kotlinx.android.synthetic.main.activity_matching_request.MatchingViewPager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import me.relex.circleindicator.CircleIndicator
import java.util.*


class MatchingDetail : AppCompatActivity(){

    val model : ModelMatching = ModelMatching(Acontext = this)
    val pModel : ModelProfile = ModelProfile(contextA = this)

    lateinit var matchingdata : ShowMatchingTeamInfoResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matching_detail)

        val adapter = TeamInfoPagerAdapter(this.supportFragmentManager)
        MatchingViewPager.adapter = adapter
        MatchingTab.setupWithViewPager(MatchingViewPager)


        var matchingId = intent.getIntExtra("MatchingTeamId",0)
        var myTeamId = intent.getIntExtra("MyTeamId", 0)
        //init screen

        model.lookMatchingTeam(matchingId, myTeamId, object : TeamDataCallback{
            override fun LookMatchingTeamInfo(data: ShowMatchingTeamInfoResponse) {
                runBlocking {
                    for(i in 0..data.data.teamInfo.max_member_number-1){
                        pModel.getTeammemberInfo(
                            data!!.data.teamMembers.get(i).id,
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
                                    //Log.d("viewPager추가",calAge(data.data.userInfo.birth).toString())
                                    adapter.notifyDataSetChanged()
                                }
                            })
                    }
                    adapter.notifyDataSetChanged()

                }

            }
        })


        backButtonMatching.setOnClickListener {
            finish()
        }
    }

    fun calAge(age : String) : String{
        var year = Calendar.getInstance().get(Calendar.YEAR)
        var b = age.substring(0,4);
        var peopleAge = year - b.toInt();

        return peopleAge.toString()
    }
}