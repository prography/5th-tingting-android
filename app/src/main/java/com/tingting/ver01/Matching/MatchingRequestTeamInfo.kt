package com.tingting.ver01.Matching

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tingting.ver01.model.CodeCallBack
import com.tingting.ver01.model.Matching.ShowMatchingTeamInfoResponse
import com.tingting.ver01.model.ModelMatching
import com.tingting.ver01.model.TeamDataCallback
import com.tingting.ver01.R
import com.tingting.ver01.TeamInfo.TeamInfoAdapter
import com.tingting.ver01.TeamInfo.TeamInfoData
import com.tingting.ver01.TeamInfo.TeamInfoRecyclerViewMargin
import kotlinx.android.synthetic.main.activity_matching_request_team_info.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MatchingRequestTeamInfo:AppCompatActivity() {

    val model : ModelMatching = ModelMatching(Acontext = this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matching_request_team_info)

        var teamlist = arrayListOf<TeamInfoData>()
        val size =resources.getDimensionPixelSize(R.dimen.wide_size)

        // 상대팀 id
        var matchingTeamId = intent.getIntExtra("matchingTeamId",0)

        // 매칭 id
        var matchingId = intent.getIntExtra("matchingId", 0)

        // 내가 현재 속한 팀 id
        var myTeamId = intent.getIntExtra("myTeamId", 0)
        val Adapter = TeamInfoAdapter(this.applicationContext, teamlist){
                TeamInfoData ->
            val intent= Intent(this.applicationContext, MatchingDetail::class.java)
            intent.putExtra("MatchingTeamId", matchingTeamId)
            intent.putExtra("MyTeamId", myTeamId)
            startActivity(intent)
        }
        //init screen

        back.setOnClickListener {
            finish()
        }

        model.lookMatchingTeam(matchingTeamId, myTeamId, object : TeamDataCallback {
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


        // 우리 팀 멤버가 보낸 하트 동의하기
        accept.setOnClickListener {
            model.sendHeart(matchingId, object : CodeCallBack{
                override fun onSuccess(code: String, value: String) {
                    try{
                        if(code.equals("201")){
                            Toast.makeText(applicationContext, "매칭 신청하기 성공", Toast.LENGTH_LONG).show()
                        }
                        else if(code.equals("400")){
                            Toast.makeText(applicationContext, "매칭 정보가 없거나 이미 전원이 하트를 보냈습니다!", Toast.LENGTH_LONG).show()

                        }else if(code.equals("403")){
                            Toast.makeText(applicationContext, "팀에 속해있지 않습니다!", Toast.LENGTH_LONG).show()
                        }
                        else if(code.equals("500")){
                            Toast.makeText(applicationContext, "매칭 신청하기 실패", Toast.LENGTH_LONG).show()

                        }
                    }catch (e:Exception){

                    }
                }
            })
            finish()
        }

        // 거절하기
        reject.setOnClickListener {
            Toast.makeText(applicationContext, "거절했습니다", Toast.LENGTH_LONG).show()
            finish()
        }

        // 팀원 정보
        val deco = TeamInfoRecyclerViewMargin(size)
        teamRecyclerView.addItemDecoration(deco)

        teamRecyclerView.adapter = Adapter
        val lm = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        teamRecyclerView.layoutManager = lm
        teamRecyclerView.setHasFixedSize(true)

    }
}