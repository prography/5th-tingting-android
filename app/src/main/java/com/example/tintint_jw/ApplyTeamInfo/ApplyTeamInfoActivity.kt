package com.example.tintint_jw.ApplyTeamInfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tintint_jw.R
import com.example.tintint_jw.TeamInfo.TeamInfoAdapter
import com.example.tintint_jw.TeamInfo.TeamInfoData
import com.example.tintint_jw.TeamInfo.TeamInfoRecyclerViewMargin
import kotlinx.android.synthetic.main.activity_apply_team_info.*

class ApplyTeamInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apply_team_info)

        var teamlist = arrayListOf<TeamInfoData>()
        val size =resources.getDimensionPixelSize(R.dimen.wide_size)

        // back button event
        back.setOnClickListener(){
        finish()
        }

        //Edit Team info button click

        // 팀장이면 background change하는 코드 추가.
        teamlist.add(TeamInfoData(R.drawable.haein, "팀장", "iu"))
        teamlist.add(TeamInfoData(R.drawable.haein, "팀원", "iu2"))


        acceptBtn.setOnClickListener() {
            Toast.makeText(this,"수락 되었습니다.", Toast.LENGTH_LONG).show()
            finish()
            //이 아이템 데이터를 삭제 하는 쿼리 수행.
        }

        rejectBtn.setOnClickListener(){
            Toast.makeText(this,"거절 되었습니다.", Toast.LENGTH_LONG).show()
           finish()
            //이 아이템 데이터를 삭제 하는 쿼리 수행.
        }

        //var intent = Intent(this,)
        val Adapter = TeamInfoAdapter(this,teamlist){
            //add Intent function go to team detail.

            //teamInfoData -> intent = Intent(this,)
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

