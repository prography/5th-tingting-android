package com.example.tintint_jw.TeamInfo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tintint_jw.ApplyTeamInfo.ApplyTeamInfoActivity
import com.example.tintint_jw.SearchTeam.MakeTeamPacakge.ReviseTeam
import com.example.tintint_jw.R
import kotlinx.android.synthetic.main.activity_apply_team_info.back
import kotlinx.android.synthetic.main.activity_team_info.*

class TeamInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_info)

        var teamlist = arrayListOf<TeamInfoData>()
        var matchinglist = arrayListOf<MatchingData>()

        // 처리
        val size = resources.getDimensionPixelSize(R.dimen.wide_size)

        // back button event
        back.setOnClickListener() {
            finish()
        }

        //Edit Team info button click
        var intent2 = Intent(
            this,
            ReviseTeam::class.java
        )

    //값 수정하기.
        EditTeamInfo.setOnClickListener() {

            val intent = Intent(this, ReviseTeam::class.java)
            intent.putExtra("teamBossId", 5)
            startActivity(intent)
        }

        // 팀장이면 background change하는 코드 추가.
        teamlist.add(TeamInfoData(R.drawable.iu, "팀장", "iu"))
        teamlist.add(TeamInfoData(R.drawable.iu, "팀장", "iu2"))

        var intent = Intent(this, TeamInfoDetailActivity::class.java);
        val Adapter = TeamInfoAdapter(this, teamlist) { teamInfoData ->
            startActivity(intent)
        }

        matchinglist.add(MatchingData("1"))
        matchinglist.add(MatchingData("2"))
        var intent3 = Intent(this, ApplyTeamInfoActivity::class.java)

        val MatchingAdapter = MatchingAdapter(this, matchinglist) { it ->
            startActivity(intent3)
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
