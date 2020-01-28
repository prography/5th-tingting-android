package com.example.tintint_jw.ApplyTeamInfo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tintint_jw.Matching.MatchingDetail
import com.example.tintint_jw.Model.CodeCallBack
import com.example.tintint_jw.Model.Matching.ShowAppliedTeamInfoResponse
import com.example.tintint_jw.Model.ModelMatching
import com.example.tintint_jw.Model.TeamDataCallback
import com.example.tintint_jw.R
import com.example.tintint_jw.SharedPreference.App
import com.example.tintint_jw.TeamInfo.TeamInfoAdapter
import com.example.tintint_jw.TeamInfo.TeamInfoData
import com.example.tintint_jw.TeamInfo.TeamInfoRecyclerViewMargin
import kotlinx.android.synthetic.main.activity_apply_team_info.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ApplyTeamInfoActivity : AppCompatActivity() {

    var model : ModelMatching = ModelMatching(Acontext = this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apply_team_info)

        var teamlist = arrayListOf<TeamInfoData>()

        val size =resources.getDimensionPixelSize(R.dimen.wide_size)
        var myTeamId = intent.getIntExtra("myTeamId", 0)
        var matchingTeamId = intent.getIntExtra("matchingTeamId", 0)
        val matchingId = intent.getIntExtra("matchingId", 0)

        val Adapter = TeamInfoAdapter(this,teamlist){
                teamInfoData -> val intent = Intent(this, MatchingDetail::class.java)
                intent.putExtra("MatchingTeamId", matchingTeamId)
                intent.putExtra("MyTeamId", myTeamId)
            startActivity(intent)
        }

        // back button event
        back.setOnClickListener(){
        finish()
        }

        model.lookAppliedMatchingTeam(matchingTeamId, myTeamId, object : TeamDataCallback{
            override fun LookAppliedTeamInfo(data: ShowAppliedTeamInfoResponse) {

                var scope:CoroutineScope = CoroutineScope(Dispatchers.Main)
                runBlocking {
                    scope.launch {
                        teamName.text = data.data.teamInfo.name
                        if(data.data.teamInfo.gender==0){
                            genderInfo.text = "남자"
                        }else{
                            genderInfo.text = "여자"
                        }
                        numberInfo.text = data.data.teamInfo.max_member_number.toString()+":"+data.data.teamInfo.max_member_number.toString()
                        regionInfo.text = data.data.teamInfo.place
                        applyTeamInfo.text = data.data.teamInfo.intro
                        // 팀원 목록
                        val coroutineScope:CoroutineScope = CoroutineScope(Dispatchers.Main)
                        runBlocking {
                            coroutineScope.launch {
                                try{
                                    for(i in 0..data.data.teamMembers.size - 1){
                                        teamlist.add(TeamInfoData(data.data.teamMembers.get(i).thumbnail, i.toString(), data.data.teamMembers.get(i).name))

                                    }
                                }catch (e:Exception){

                                }
                                Adapter.notifyDataSetChanged()

                            }

                        }

                        message.text = data.data.message


                    }
                }
                Log.i("appliedTeam", data.data.teamInfo.id.toString())
            }
        })
  
        acceptBtn.setOnClickListener {

            model.receiveHeart(matchingId, object : CodeCallBack{
                override fun onSuccess(code: String, value: String) {
                    if(code.equals("201")){
                        Toast.makeText(applicationContext,"수락 되었습니다.", Toast.LENGTH_LONG).show()
                        finish()
                    }else if(code.equals("400")){
                        Toast.makeText(applicationContext,"매칭 정보가 없습니다!", Toast.LENGTH_LONG).show()
                        finish()
                    }else if(code.equals("403")){
                        Toast.makeText(applicationContext,"팀에 속해있지 않습니다!", Toast.LENGTH_LONG).show()
                        finish()
                    }else{
                        Toast.makeText(applicationContext,"매칭 수락하기 실패", Toast.LENGTH_LONG).show()
                        finish()
                    }
                }
            })

            //이 아이템 데이터를 삭제 하는 쿼리 수행.
        }

        //apply maring to adapter.
        val deco = TeamInfoRecyclerViewMargin(size)
        teamRecyclerView.addItemDecoration(deco)

        teamRecyclerView.adapter = Adapter
        val lm = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        teamRecyclerView.layoutManager = lm
        teamRecyclerView.setHasFixedSize(true)

    }
}

