package com.example.tintint_jw.TeamInfo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.tintint_jw.Matching.FirstPagerFragment
import com.example.tintint_jw.Matching.TeamInfoPagerAdapter
import com.example.tintint_jw.Model.ModelTeam
import com.example.tintint_jw.Model.Profile.GetTeammberProfileResponse
import com.example.tintint_jw.Model.Profile.LookMyTeamInfoProfileResponse
import com.example.tintint_jw.Model.Profile.ModelProfile
import com.example.tintint_jw.Model.ProfileCallBack
import com.example.tintint_jw.Model.TeamDataCallback
import com.example.tintint_jw.R
import kotlinx.android.synthetic.main.activity_matching_request.MatchingViewPager
import kotlinx.android.synthetic.main.activity_team_info_detail.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.util.*

class TeamInfoProfileDetailActivity : AppCompatActivity() {

    val model: ModelTeam = ModelTeam(this)
    val pModel : ModelProfile = ModelProfile(contextA = this)
    lateinit var adapter : TeamInfoPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_info_detail)

        var info: LookMyTeamInfoProfileResponse


        backButton.setOnClickListener(){
           finish()
        }

        adapter= TeamInfoPagerAdapter(supportFragmentManager)
        MatchingViewPager.adapter=adapter
        tab.setupWithViewPager(MatchingViewPager)



        var a = intent.getIntExtra("MyTeamId", 0)

        //init screen data
        model.LookMyTeamInfopPofile(a, object : TeamDataCallback {
            override fun LookMyTeamInfoListProfile (data: LookMyTeamInfoProfileResponse) {
                info = data

                //adapter set Data
                runBlocking {
                    for (i in 0..info.data.teamMembers.size-1) {
                        pModel.getTeammemberInfo(
                            info.data.teamMembers.get(i).id,
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
                                    Log.d("viewPager추가",calAge(data.data.userInfo.birth).toString())
                                    adapter.notifyDataSetChanged()
                                }
                            })

                    }

                    adapter.notifyDataSetChanged()
                }

                }
            })

        }

    fun calAge(age : String) : String{
        var year = Calendar.getInstance().get(Calendar.YEAR)
        var b = age.substring(0,4);
        var peopleAge = year - b.toInt();

        return peopleAge.toString()
    }
}
