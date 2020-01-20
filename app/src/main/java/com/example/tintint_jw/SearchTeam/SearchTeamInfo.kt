package com.example.tintint_jw.SearchTeam
import android.app.AlertDialog
import android.content.Intent
import android.graphics.ColorSpace
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tintint_jw.Matching.TeamData
import com.example.tintint_jw.Model.ModelTeam
import com.example.tintint_jw.Model.Team.LookIndivisualTeam.IndivisualTeamResponse
import com.example.tintint_jw.Model.Team.LookTeamList.TeamResponse
import com.example.tintint_jw.Model.Team.MakeTeam.MakeTeamResponse
import com.example.tintint_jw.Model.TeamDataCallback
import com.example.tintint_jw.R
import com.example.tintint_jw.SearchTeam.MakeTeamPacakge.MTeam
import com.example.tintint_jw.SearchTeam.MakeTeamPacakge.ReviseTeam
import com.example.tintint_jw.SharedPreference.App
import com.example.tintint_jw.TeamInfo.MatchingData
import com.example.tintint_jw.TeamInfo.TeamInfoAdapter
import com.example.tintint_jw.TeamInfo.TeamInfoData
import com.example.tintint_jw.TeamInfo.TeamInfoRecyclerViewMargin
import com.example.tintint_jw.View.MainActivity
import kotlinx.android.synthetic.main.dialog_view.view.*
import kotlinx.android.synthetic.main.fragment_search_team_info.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SearchTeamInfo :  AppCompatActivity() {
    val model :ModelTeam = ModelTeam(this)

    var teamlist = arrayListOf<TeamInfoData>()
    var matchinglist = arrayListOf<MatchingData>()
    val scope = CoroutineScope(Dispatchers.Main)
    lateinit var Adapter : TeamInfoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_search_team_info)

        // 처리
        val size =resources.getDimensionPixelSize(R.dimen.wide_size)

        var bossId  = intent.getIntExtra("teamBossId",0)

        // back button event
     back.setOnClickListener(){
           finish()
        }

        //Edit Team info button click



        //init screen
        model.showIndivisualTeamList(App.prefs.myToken.toString(), bossId ,object:TeamDataCallback{
            override fun onIndivisualResult(data: IndivisualTeamResponse?, start: Int, end: Int) {
                super.onIndivisualResult(data, start, end)
                var a = data!!.data.teamInfo
                var kingNumber = a.owner_id
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
                runBlocking {
                    scope.launch {
                        for( i in 0..b.size - 1) {
                            if (kingNumber == b.get(i).id) {
                                teamlist.add(TeamInfoData(b.get(i).thumbnail, "팀장", b.get(i).name))
                            } else {
                                teamlist.add(TeamInfoData(b.get(i).thumbnail, "팀원", b.get(i).name))
                            }
                        }
                    }
                    Adapter.notifyDataSetChanged()
                }

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
            val intent = Intent(this!!.applicationContext, MainActivity::class.java)

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

