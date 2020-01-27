package com.example.tintint_jw.TeamInfo

import LookMyTeamInfoDetailResponse
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tintint_jw.ApplyTeamInfo.ApplyTeamInfoActivity
import com.example.tintint_jw.Model.ModelMatching
import com.example.tintint_jw.Model.ModelTeam
import com.example.tintint_jw.Model.Profile.LookMyTeamInfoProfileResponse
import com.example.tintint_jw.Model.TeamDataCallback
import com.example.tintint_jw.R
import com.example.tintint_jw.SearchTeam.MakeTeamPacakge.ReviseTeam
import com.example.tintint_jw.SharedPreference.App
import kotlinx.android.synthetic.main.activity_apply_team_info.back
import kotlinx.android.synthetic.main.activity_team_info.*
import kotlinx.android.synthetic.main.dialog_view.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TeamInfoActivity : AppCompatActivity() {

    val model: ModelTeam = ModelTeam(this)
    val matchingModel: ModelMatching = ModelMatching(Acontext = this)

    lateinit var info: LookMyTeamInfoDetailResponse
    lateinit var Adapter: TeamInfoAdapter

    lateinit var MatchingAdapter: MatchingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_info)

        var teamlist = arrayListOf<TeamInfoData>()
        var matchinglist = arrayListOf<MatchingData>()

        var myTeamId = intent.getIntExtra("MyTeamId", 0)
        var matchingTeamId: Int
        var matchingId: Int



        var intent = Intent(this, TeamInfoProfileDetailActivity::class.java);
        intent.putExtra("MyTeamId", myTeamId)

        Adapter = TeamInfoAdapter(this, teamlist) { teamInfoData ->

            startActivity(intent)
        }



        MatchingAdapter = MatchingAdapter(this, matchinglist)

        val intentapply: Intent = Intent(this, ApplyTeamInfoActivity::class.java)
        intentapply.putExtra("myTeamId", myTeamId)


        MatchingAdapter.click = object : MatchingAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                model.LookMyTeamInfo(myTeamId, object : TeamDataCallback {
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
        model.LookMyTeamInfopPofile(myTeamId, object : TeamDataCallback {
            override fun LookMyTeamInfoListProfile(data: LookMyTeamInfoProfileResponse) {
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
                            }
                        }

                                val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
                                runBlocking {
                                    coroutineScope.launch {
                                            if (info.data.teamMembers.get(0).name.equals(App.prefs.myname)) {
                                                App.prefs.myPersonalId = info.data.teamMembers.get(0).id.toString()
                                                Log.i("myPersonalIdSize",info.data.teamMembers.size.toString() )
                                            }
                                            for (i in 0..info.data.teamMembers.size - 1) {
                                                //owner 와 팀원 ID가 같으면 팀장 아니면 팀원.
                                                if (info.data.teamInfo.owner_id == info.data.teamMembers.get(i).id) {
                                                    teamlist.add(
                                                        TeamInfoData(
                                                            info.data.teamMembers.get(i).thumbnail,
                                                            "0",
                                                            info.data.teamMembers.get(i).name
                                                        )
                                                    )

                                                } else {
                                                    teamlist.add(
                                                        TeamInfoData(
                                                            info.data.teamMembers.get(i).thumbnail,
                                                            "1",
                                                            info.data.teamMembers.get(i).name
                                                        )
                                                    )
                                                }
                                            }
                                            for (i in 0..info.data.teamMatchings.size - 1) {
                                                //이름 추가
                                                var name = info.data.teamMatchings.get(i).sendTeam.name + "/" +
                                                            info.data.teamMatchings.get(i).sendTeam.max_member_number + "/" +
                                                            info.data.teamMatchings.get(i).sendTeam.place
                                                matchinglist.add(MatchingData("", name))

                                            }
                                            noMatching(matchinglist, textNoMatching)

                                            Adapter.notifyDataSetChanged()

                                        Adapter.notifyDataSetChanged()
                                        MatchingAdapter.notifyDataSetChanged()
                                    }

                                }

                    }
                })
            }
        })

        // 처리
        val size = resources.getDimensionPixelSize(R.dimen.wide_size)

        // back button event
        back.setOnClickListener() {
            finish()
        }

        // 팀 떠나기
        TeamLeave.setOnClickListener() {
            val logoutDialog = AlertDialog.Builder(this@TeamInfoActivity)
            val dialogView = layoutInflater.inflate(R.layout.dialog_team_destruct, null)

            logoutDialog.setView(dialogView)
            val check = logoutDialog.show()

            dialogView.dialogCancel.setOnClickListener() {
                check.dismiss()
            }

            dialogView.dialogOK.setOnClickListener() {
                model.TeamLeave(info.data.teamInfo.id)
                finish()
                check.dismiss()
            }

        }


        //값 수정하기.
        EditTeamInfo.setOnClickListener() {

            val intent = Intent(this@TeamInfoActivity, ReviseTeam::class.java)
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

        val lm = LinearLayoutManager(
            this@TeamInfoActivity,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        val lm2 = LinearLayoutManager(
            this@TeamInfoActivity,
            LinearLayoutManager.VERTICAL,
            false
        )
        teamRecyclerView1.layoutManager = lm
        teamRecyclerView2.layoutManager = lm2
        teamRecyclerView1.setHasFixedSize(true)
        teamRecyclerView2.setHasFixedSize(true)


    }

    fun noMatching(ssize: List<Any>, view:TextView){
        if(ssize.size!=0){
            view.visibility = View.INVISIBLE
        }
    }

}

