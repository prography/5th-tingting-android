package com.example.tintint_jw.Matching

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tintint_jw.Model.CodeCallBack
import com.example.tintint_jw.Model.Matching.ShowMatchingTeamInfoResponse
import com.example.tintint_jw.Model.ModelMatching
import com.example.tintint_jw.Model.TeamDataCallback
import com.example.tintint_jw.R
import com.example.tintint_jw.TeamInfo.TeamInfoAdapter
import com.example.tintint_jw.TeamInfo.TeamInfoData
import com.example.tintint_jw.TeamInfo.TeamInfoRecyclerViewMargin
import kotlinx.android.synthetic.main.activity_matching_apply_team_info.*
import kotlinx.android.synthetic.main.dialog_send_message.view.*
import kotlinx.coroutines.*

class MatchingApplyTeamInfo:AppCompatActivity() {

    val model : ModelMatching = ModelMatching(Acontext = this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matching_apply_team_info)

        var teamlist = arrayListOf<TeamInfoData>()
        val size =resources.getDimensionPixelSize(R.dimen.wide_size)

        // 상대팀 id
        var matchingId = intent.getIntExtra("MatchingTeamId",0)

        // 내가 현재 속한 팀 id
        var myTeamId = intent.getIntExtra("MyTeamId", 0)
        val Adapter = TeamInfoAdapter(this.applicationContext, teamlist){

            TeamInfoData ->
            val intent= Intent(this.applicationContext, MatchingDetail::class.java)
            intent.putExtra("MatchingTeamId", matchingId)
            intent.putExtra("MyTeamId", myTeamId)
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
                            if(data.data.isHeartSent){
                                // 하트 보내기 성공시 버튼 텍스트 변경
                                var coroutineScope:CoroutineScope = CoroutineScope(Dispatchers.Main)
                                runBlocking {
                                    coroutineScope.launch{
                                        try{
                                            button.text = "수락 대기중..."
                                        }catch (e:Exception){

                                        }
                                    }
                                }
                            }else{
                                button.text = "좋아요"
                            }
                            Adapter.notifyDataSetChanged()
                        }catch (e:Exception){

                        }

                    }
                }

            }
        })


        button.setOnClickListener {
            val messgDialog = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.dialog_send_message, null)

            messgDialog.setView(dialogView)

            val check = messgDialog.show()
            val drawable = resources.getDrawable(R.drawable.dialog)

            dialogView.send.setOnClickListener{

                try{
                model.firstSendHeart(matchingId, myTeamId, dialogView.message.text.toString(), object : CodeCallBack{
                    override fun onSuccess(code: String, value: String) {

                        try{
                            if(code.equals("201")){
                                Toast.makeText(applicationContext, "매칭 신청하기 성공", Toast.LENGTH_LONG).show()


                            }else if(code.equals("400")){
                                Toast.makeText(applicationContext, "매칭을 신청할 수 있는 팀이 아닙니다!", Toast.LENGTH_LONG).show()

                            }else if(code.equals("403")){
                                Toast.makeText(applicationContext, "팀에 속해있지 않습니다!", Toast.LENGTH_LONG).show()
                            }
                        }catch (e:Exception){

                        }
                        finish()


                    }
                })
            }catch (t:Throwable){
                    t.printStackTrace()
                    Toast.makeText(applicationContext, "메시지를 입력해주세요", Toast.LENGTH_LONG).show()
                }}

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