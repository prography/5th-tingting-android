package com.example.tintint_jw.TeamInfo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tintint_jw.ApplyTeamInfo.ApplyTeamInfoActivity
import com.example.tintint_jw.Matching.MatchingDetail
import com.example.tintint_jw.Model.ModelMatching
import com.example.tintint_jw.Model.ModelTeam
import com.example.tintint_jw.Model.Team.LookMyTeamInfoDetail.LookMyTeamInfoDetailResponse
import com.example.tintint_jw.Model.TeamDataCallback
import com.example.tintint_jw.R
import com.example.tintint_jw.SearchTeam.MakeTeamPacakge.ReviseTeam
import com.example.tintint_jw.SharedPreference.App
import kotlinx.android.synthetic.main.activity_apply_team_info.back
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_team_info.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TeamInfoActivity : AppCompatActivity() {

    val model: ModelTeam = ModelTeam(this)
    val matchingModel:ModelMatching = ModelMatching(Acontext = this)

    lateinit var info: LookMyTeamInfoDetailResponse
    lateinit var  Adapter :TeamInfoAdapter
    lateinit var MatchingAdapter:MatchingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_info)

        var teamlist = arrayListOf<TeamInfoData>()
        var matchinglist = arrayListOf<MatchingData>()

        var myTeamId = intent.getIntExtra("MyTeamId", 0)
        var matchingTeamId:Int
        var matchingId:Int


        Adapter = TeamInfoAdapter(this, teamlist) { teamInfoData ->
            val intent = Intent(this.applicationContext, MatchingDetail::class.java)
            startActivity(intent)
        }
        val intent:Intent = Intent(this, ApplyTeamInfoActivity::class.java)
        intent.putExtra("myTeamId", myTeamId)

        MatchingAdapter = MatchingAdapter(this, matchinglist)
        MatchingAdapter.click = object : MatchingAdapter.ItemClick{
            override fun onClick(view: View, position: Int) {
                model.LookMyTeamInfo(myTeamId, object :TeamDataCallback{
                    override fun LookMyTeaminfoList(data: LookMyTeamInfoDetailResponse) {

                        matchingTeamId = data.data.teamMatchings.get(position).sendTeam.id
                        matchingId = data.data.teamMatchings.get(position).id
                        Log.i("matchingTeamId", matchingTeamId.toString())
                        intent.putExtra("matchingTeamId", matchingTeamId)
                        intent.putExtra("matchingId", matchingId)

                        startActivity(intent)

                    }

                })

            }
        }

        //init screen data
        model.LookMyTeamInfo(myTeamId, object : TeamDataCallback {
            override fun LookMyTeaminfoList(data: LookMyTeamInfoDetailResponse) {
                info = data
                Log.i("myName", App.prefs.myname)

                var scope = CoroutineScope(Dispatchers.Main)

                runBlocking {
                    scope.launch {
                        //이름 값 설정
                        teamName.setText(info.data.teamInfo.name)
                        //성별 값 설정
                        if (info.data.teamInfo.gender == 0) {
                            genderInfo.setText("남자")
                        } else {
                            genderInfo.setText("여자")
                        }
                        //N:N 부분 설정
                        numberInfo.setText(info.data.teamInfo.max_member_number.toString() + ":" + info.data.teamInfo.max_member_number)
                        //팀 설명 설정
                        TeamInfoExplain.setText(info.data.teamInfo.intro)

                        //팀원 숫자만큼 반복문
                        val coroutineScope:CoroutineScope = CoroutineScope(Dispatchers.Main)
                        runBlocking {
                            coroutineScope.launch {
                                try{
                                    for (i in 0..info.data.teamMembers.size - 1) {
                                        //owner 와 팀원 ID가 같으면 팀장 아니면 팀원.
                                        Log.d("TeamPeopleAdd","팀원이 추가되었습니다.")
                                        teamlist.add(TeamInfoData(info.data.teamMembers.get(i).thumbnail,i.toString(),info.data.teamMembers.get(i).name))

                                        /*if(info.data.teamInfo.owner_id == info.data.teamMember.get(i).id){
                                            teamlist.add(TeamInfoData(info.data.teamMember.get(i).thumbnail,i.toString(),info.data.teamMember.get(i).name))
                                        }else{
                                            teamlist.add(TeamInfoData(info.data.teamMember.get(i).thumbnail,i.toString(),info.data.teamMember.get(i).name))
                                        }*/
                                    }
                                }catch (e:Exception){

                                    }
                                try{
                                    for(i in 0..info.data.teamMatchings.size - 1){
                                        //Log.i("teamMatching size", info.data.teamMatchings.size.toString())
                                        matchinglist.add(MatchingData(info.data.teamMatchings.get(i).sendTeam.name, info.data.teamMatchings.get(i).sendTeam.membersInfo.size,info.data.teamMatchings.get(i).sendTeam.place, 1))
                                    }
                                }catch (e:Exception){
                                    Log.d("exception", e.toString())
                                }
                                for(i in 0..info.data.teamMembers.size-1){
                                if(info.data.teamMembers.get(i).name.equals(App.prefs.myname)){
                                    App.prefs.myPersonalId = info.data.teamMembers.get(i).id.toString()
                                    Log.i("myPersonalId", App.prefs.myPersonalId)
                                }

                            }
                                Adapter.notifyDataSetChanged()
                                MatchingAdapter.notifyDataSetChanged()
                        }

                        }

                    }

                }


            }
        })

        // 처리
        val size = resources.getDimensionPixelSize(R.dimen.wide_size)

        // back button event
        back.setOnClickListener() {
            finish()
        }


        //값 수정하기.
        EditTeamInfo.setOnClickListener() {

            val intent = Intent(this, ReviseTeam::class.java)
            intent.putExtra("teamBossId", 5)
            startActivity(intent)
        }

        //apply maring to adapter.
        val deco = TeamInfoRecyclerViewMargin(size)
        teamRecyclerView1.addItemDecoration(deco)
        teamRecyclerView2.addItemDecoration(deco)

        teamRecyclerView1.adapter = Adapter
        teamRecyclerView2.adapter = MatchingAdapter

        val lm = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val lm2 = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        teamRecyclerView1.layoutManager = lm
        teamRecyclerView2.layoutManager = lm2
        teamRecyclerView1.setHasFixedSize(true)
        teamRecyclerView2.setHasFixedSize(true)


    }

}
