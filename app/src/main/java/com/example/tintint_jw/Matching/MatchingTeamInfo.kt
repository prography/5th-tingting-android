package com.example.tintint_jw.Matching

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tintint_jw.Model.ModelMatching
import com.example.tintint_jw.Model.ModelTeam
import com.example.tintint_jw.Model.Team.LookIndivisualTeam.IndivisualTeamResponse
import com.example.tintint_jw.Model.TeamDataCallback
import com.example.tintint_jw.R
import com.example.tintint_jw.SharedPreference.App
import com.example.tintint_jw.TeamInfo.TeamInfoAdapter
import com.example.tintint_jw.TeamInfo.TeamInfoData
import com.example.tintint_jw.TeamInfo.TeamInfoRecyclerViewMargin
import com.example.tintint_jw.View.MainActivity
import kotlinx.android.synthetic.main.dialog_view.view.*
import kotlinx.android.synthetic.main.fragment_search_team_info.*

class MatchingTeamInfo : AppCompatActivity() {

    val model = ModelTeam( this)
    lateinit var Adapter : TeamInfoAdapter
    var teamlist = arrayListOf<TeamInfoData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matching_team_info)

        // 처리
        val size =resources.getDimensionPixelSize(R.dimen.wide_size)

        var bossId  = intent.getIntExtra("teamBossId",0)

        // back button event
        back.setOnClickListener(){
            finish()
        }

        //Edit Team info button click



        //init screen
        model.showIndivisualTeamList(App.prefs.myToken.toString(), bossId ,object:
            TeamDataCallback {
            override fun onIndivisualResult(data: IndivisualTeamResponse?, start: Int, end: Int) {
                super.onIndivisualResult(data, start, end)
                var a = data!!.data.teamInfo
                var b = data!!.data.teamMembers
                var people = a.max_member_number.toString()
                teamName.setText(a.name)
                Log.d("test",a.name)
                Log.d("test",b.size.toString())


                if(a.gender==0){
                    genderInfo.setText("남자")
                }

                numberInfo.setText(people +":" + people)

                teamExplain.setText(a.intro)

            }
        })

        teamJoin.setOnClickListener() {

            val builder = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.dialog_team_info, null)

            val mbuilder = builder.setView(dialogView).show()

            dialogView.dialogOK.setOnClickListener(){

                model.JoinTeam(App.prefs.myToken.toString(),bossId)

                val intent = Intent(this!!.applicationContext, MainActivity::class.java)
                startActivity(intent)

            }

            dialogView.dialogCancel.setOnClickListener(){
                mbuilder.dismiss()
            }
        }
        Adapter = TeamInfoAdapter(this!!.applicationContext,teamlist){
            //      teamInfoData -> fragmentManager?.beginTransaction()
            //  ?.add(R.id.mainFragment,TeamInfoDetailFragment())?.commit()
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
