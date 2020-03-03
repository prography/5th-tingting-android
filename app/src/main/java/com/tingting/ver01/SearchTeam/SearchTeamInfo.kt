package com.tingting.ver01.SearchTeam
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tingting.ver01.model.ModelTeam
import com.tingting.ver01.model.team.LookIndivisualTeam.IndivisualTeamResponse
import com.tingting.ver01.model.TeamDataCallback
import com.tingting.ver01.R
import com.tingting.ver01.SharedPreference.App
import com.tingting.ver01.TeamInfo.*
import kotlinx.android.synthetic.main.dialog_view.view.*
import kotlinx.android.synthetic.main.activity_search_team_info.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class  SearchTeamInfo :  AppCompatActivity() {
    val model :ModelTeam = ModelTeam(this)

    var teamlist = arrayListOf<TeamInfoData>()
    var matchinglist = arrayListOf<MatchingData>()

    val scope = CoroutineScope(Dispatchers.Main)
    lateinit var Adapter : TeamInfoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_team_info)

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
                Log.d("test",b.size.toString())

                if(a.gender==0){
                    genderInfo.setText("남자")
                }else{
                    genderInfo.setText("여자")
                }
                numberInfo.setText(people +":" + people)
                regionInfo.text = a.place
                teamExplain.setText(a.intro)

                runBlocking {
                    scope.launch {
                        Log.d("SearchTeamInfoSize",b.size.toString())

                        for( i in b.size-1 downTo 0) {
                            Log.d("SearchTeamInfoSize","데이터 추가됨")
                            if (kingNumber == b.get(i).id) {
                                teamlist.add(TeamInfoData(b.get(i).thumbnail, "0", b.get(i).name))
                            } else {
                                teamlist.add(TeamInfoData(b.get(i).thumbnail, "1", b.get(i).name))
                            }
                        }
                        Adapter.notifyDataSetChanged()
                    }

                }

            }
        })

        teamJoin.setOnClickListener() {

            val builder = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.dialog_team_info, null)

            val mbuilder = builder.setView(dialogView).show()

            dialogView.dialogOK.setOnClickListener(){

                model.JoinTeam(App.prefs.myToken.toString(),bossId)

                finish()

            }

            dialogView.dialogCancel.setOnClickListener(){
                mbuilder.dismiss()
            }
        }

        Adapter = TeamInfoAdapter(this!!.applicationContext,teamlist){
            var id = intent.getIntExtra("teamBossId",0)
            var intent = Intent(this, SearchTeamInfoDetailActivity::class.java)
            intent.putExtra("MyTeamId",id)
            startActivity(intent)

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

