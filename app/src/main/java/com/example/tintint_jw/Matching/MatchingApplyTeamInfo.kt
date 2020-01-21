package com.example.tintint_jw.Matching

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tintint_jw.Model.Matching.ShowMatchingTeamInfoResponse
import com.example.tintint_jw.Model.ModelMatching
import com.example.tintint_jw.Model.TeamDataCallback
import com.example.tintint_jw.R
import com.example.tintint_jw.TeamInfo.TeamInfoAdapter
import com.example.tintint_jw.TeamInfo.TeamInfoData
import com.example.tintint_jw.TeamInfo.TeamInfoRecyclerViewMargin
import kotlinx.android.synthetic.main.activity_matching_apply_team_info.*
import kotlinx.coroutines.*

class MatchingApplyTeamInfo:AppCompatActivity() {

    val model : ModelMatching = ModelMatching(Acontext = this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matching_apply_team_info)

        var teamlist = arrayListOf<TeamInfoData>()
        val size =resources.getDimensionPixelSize(R.dimen.wide_size)

        var matchingId = intent.getIntExtra("MatchingTeamId",0)
        var myTeamId = intent.getIntExtra("MyTeamId", 0)
        val Adapter = TeamInfoAdapter(this.applicationContext, teamlist){

            TeamInfoData ->
            val intent= Intent(this.applicationContext, MatchingDetail::class.java)
            startActivity(intent)
        }
        //init screen

        back.setOnClickListener {
            finish()
        }

        model.lookMatchingTeam(matchingId, myTeamId, object : TeamDataCallback {
            override fun LookMatchingTeamInfo(data: ShowMatchingTeamInfoResponse) {
                teamName.setText(data.data.teamInfo.name)
                Log.d("teamname", data.data.teamInfo.name)
                if(data.data.teamInfo.gender==0){
                    genderInfo.setText("남자")
                }else{
                    genderInfo.setText("여자")
                }

                when(data.data.teamMembers.size){
                    1-> numberInfo.text = "1:1"
                    2-> numberInfo.text = "2:2"
                    3-> numberInfo.text = "3:3"
                    4-> numberInfo.text = "4:4"
                }

                regionInfo.text = data.data.teamInfo.place

                applyTeamIntro.text=data.data.teamInfo.intro

                // coroutine
                val scope:CoroutineScope = CoroutineScope(Dispatchers.Main)
                runBlocking {
                    scope.launch {
                        try{
                            for(i in 0..data.data.teamInfo.max_member_number-1){
                                teamlist.add(TeamInfoData(data.data.teamMembers.get(i).thumbnail, i.toString(), data.data.teamMembers.get(i).name))

                            }
                            Adapter.notifyDataSetChanged()
                        }catch (e:Exception){

                        }

                    }
                }

            }
        })

        // 팀원 정보
        val deco = TeamInfoRecyclerViewMargin(size)
        teamRecyclerView.addItemDecoration(deco)

        teamRecyclerView.adapter = Adapter
        val lm = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        teamRecyclerView.layoutManager = lm
        teamRecyclerView.setHasFixedSize(true)

    }
}