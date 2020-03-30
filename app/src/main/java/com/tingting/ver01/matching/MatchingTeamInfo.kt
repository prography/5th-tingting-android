package com.tingting.ver01.matching

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tingting.ver01.R
import com.tingting.ver01.view.Main.MainActivity
import com.tingting.ver01.model.ModelTeam
import com.tingting.ver01.teamInfo.TeamInfoAdapter
import com.tingting.ver01.teamInfo.TeamInfoData
import com.tingting.ver01.teamInfo.TeamInfoRecyclerViewMargin
import kotlinx.android.synthetic.main.activity_search_team_info.*
import kotlinx.android.synthetic.main.dialog_view.view.*

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
        back.setOnClickListener {
            finish()
        }

        //Edit Team info button click



//        //init screen
//        model.showIndivisualTeamList(App.prefs.myToken.toString(), bossId ,object:
//            TeamDataCallback {
//            override fun onIndivisualResult(data: IndivisualTeamResponse?, start: Int, end: Int) {
//                super.onIndivisualResult(data, start, end)
//                var a = data!!.data.teamInfo
//                var b = data!!.data.teamMembers
//                var people = a.max_member_number.toString()
//                teamName.setText(a.name)
//                Log.d("test",a.name)
//                Log.d("test",b.size.toString())
//
//
//                if(a.gender==0){
//                    genderInfo.setText("남자")
//                }
//
//                numberInfo.setText(people +":" + people)
//
//                teamExplain.setText(a.intro)
//
//            }
//        })

        teamJoin.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.dialog_team_info, null)

            val mbuilder = builder.setView(dialogView).show()

            dialogView.dialogOK.setOnClickListener {

                model.JoinTeam(bossId,""){ isSuccess: Boolean, response: Int? ->
                    if(isSuccess && response==201){

                        val intent = Intent(this.applicationContext, MainActivity::class.java)
                        startActivity(intent)
                    }
                }


            }

            dialogView.dialogCancel.setOnClickListener {
                mbuilder.dismiss()
            }
        }
        Adapter = TeamInfoAdapter(this.applicationContext,teamlist){
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