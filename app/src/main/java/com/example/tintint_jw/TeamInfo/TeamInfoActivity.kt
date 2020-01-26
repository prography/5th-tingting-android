package com.example.tintint_jw.TeamInfo

import android.content.Intent
import android.hardware.camera2.params.MandatoryStreamCombination
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.load.data.DataFetcher
import com.example.tintint_jw.ApplyTeamInfo.ApplyTeamInfoActivity
import com.example.tintint_jw.Model.ModelTeam
import com.example.tintint_jw.Model.Profile.LookMyTeamInfoProfileResponse
import com.example.tintint_jw.Model.Team.LookMyTeamInfoDetail.LookMyTeamInfoDetailResponse
import com.example.tintint_jw.Model.TeamDataCallback
import com.example.tintint_jw.SearchTeam.MakeTeamPacakge.ReviseTeam
import com.example.tintint_jw.R
import com.example.tintint_jw.SharedPreference.App
import kotlinx.android.synthetic.main.activity_apply_team_info.back
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_team_info.*
import kotlinx.android.synthetic.main.dialog_view.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TeamInfoActivity : AppCompatActivity() {

    val model: ModelTeam = ModelTeam(this)

    lateinit var info: LookMyTeamInfoProfileResponse
    lateinit var  Adapter :TeamInfoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_info)

        var teamlist = arrayListOf<TeamInfoData>()
        var matchinglist = arrayListOf<MatchingData>()

        var a = intent.getIntExtra("MyTeamId", 0)


        var intent = Intent(this, TeamInfoDetailActivity::class.java);

        intent.putExtra("MyTeamId",a)

        Adapter = TeamInfoAdapter(this, teamlist) {

                teamInfoData -> startActivity(intent)
        }

        var intent3 = Intent(this, ApplyTeamInfoActivity::class.java)


        val MatchingAdapter = MatchingAdapter(this, matchinglist)

        //init screen data
        model.LookMyTeamInfopPofile(a, object : TeamDataCallback {
            override fun LookMyTeamInfoListProfile (data: LookMyTeamInfoProfileResponse) {
                info = data

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
                        for (i in 0..info.data.teamMembers.size-1) {
                            //owner 와 팀원 ID가 같으면 팀장 아니면 팀원.
                            Log.d("TeamPeopleAdd","팀원이 추가되었습니다.")
                            if(info.data.teamInfo.owner_id == info.data.teamMembers.get(i).id){
                                teamlist.add(TeamInfoData(info.data.teamMembers.get(i).thumbnail,"팀장",info.data.teamMembers.get(i).name))

                            }else{

                                teamlist.add(TeamInfoData(info.data.teamMembers.get(i).thumbnail,"팀원",info.data.teamMembers.get(i).name))
                            }
                        }

                        for(i in 0..info.data.teamMatchings.size-1){
                            //이름 추가
                           var name = info.data.teamMatchings.get(i).sendTeam.name+"/"+
                                        info.data.teamMatchings.get(i).sendTeam.max_member_number + "/"+
                                        info.data.teamMatchings.get(i).sendTeam.place

                            matchinglist.add(MatchingData("1",name))

                        }

                        Adapter.notifyDataSetChanged()
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

        // 팀 떠나기
        TeamLeave.setOnClickListener(){
            val logoutDialog = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.dialog_team_destruct,null)

            logoutDialog.setView(dialogView)
            val check = logoutDialog.show()

            dialogView.dialogCancel.setOnClickListener(){
                check.dismiss()
            }

            dialogView.dialogOK.setOnClickListener(){
                model.TeamLeave(info.data.teamInfo.id)
                finish()
                check.dismiss()
            }

        }


        //값 수정하기.
        EditTeamInfo.setOnClickListener() {
            val intent = Intent(this, ReviseTeam::class.java)
            intent.putExtra("teamBossId", 5)
            startActivity(intent)
        }

        // 팀장이면 background change하는 코드 추가.

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
