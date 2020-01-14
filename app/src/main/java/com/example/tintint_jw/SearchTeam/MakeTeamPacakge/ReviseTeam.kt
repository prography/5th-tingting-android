package com.example.tintint_jw.SearchTeam.MakeTeamPacakge

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tintint_jw.Model.ModelTeam
import com.example.tintint_jw.Model.Team.MakeTeam.MakeTeamResponse
import com.example.tintint_jw.Model.TeamDataCallback
import com.example.tintint_jw.R
import com.example.tintint_jw.SharedPreference.App
import com.example.tintint_jw.TeamInfo.TeamInfoData
import kotlinx.android.synthetic.main.activity_create_team2.*
import kotlinx.android.synthetic.main.activity_create_team2.back
import kotlinx.android.synthetic.main.fragment_search_team_info.*

class ReviseTeam : AppCompatActivity() {
    val model : ModelTeam = ModelTeam(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_revise_team)
        back.setOnClickListener(){

            finish()
        }

        var bossId  = intent.getIntExtra("teamBossId",0)

        model.showIndivisualTeamList(App.prefs.myToken.toString(), bossId ,object:
            TeamDataCallback {
            override fun onIndivisualResult(data: MakeTeamResponse?, start: Int, end: Int) {
                super.onIndivisualResult(data, start, end)
                var a = data!!.data.teamInfo
                var b = data!!.data.teamMember
                when(a.max_member_number){
                    1->teammemberBtn1.isChecked = true
                    2->teammemberBtn2.isChecked = true
                    3->teammemberBtn2.isChecked = true
                    4->teammemberBtn2.isChecked = true
                }
                teamnameET.setText(a.name)
                TeamIntro.setText(a.intro)
                teamkakaoET.setText(a.chat_address)
            }
        })


        createteam2RegisterBtn.setOnClickListener(){
            val number : Int = NumberOfPeople()
            Log.d("MakeTeamNumber",number.toString())
            if(makeTeam(teamnameET.text.toString(),number,TeamIntro.text.toString(),teamkakaoET.text.toString())){
                //send info to server
                model.ReviseTeamInfo(App.prefs.myToken.toString(),bossId,"",App.prefs.mygender.toString()
                ,teamnameET.text.toString(),number.toString(),TeamIntro.text.toString(),"",teamkakaoET.text.toString())

                finish()

            }

        }
        //set radio buttono color
        TeamSegmentationButton.setTintColor(resources.getColor(R.color.tingtingMain),resources.getColor(R.color.white))
    }

    //this function post revise team info to server
    fun makeTeam(TeamName:String, PeopleNum:Int, TeamIntro:String, KaKaoUrl : String) : Boolean{
        if(TeamName.isEmpty()) {
            Toast.makeText(this, "팀 명을 입력해주세요", Toast.LENGTH_LONG).show()
            return false;
        }

        if(PeopleNum==0){
            Toast.makeText(this,"인원수를 선택해주세요", Toast.LENGTH_LONG).show()
            return false;
        }

        if(TeamIntro.isEmpty()){
            Toast.makeText(this,"팀소개를 입력해주세요", Toast.LENGTH_LONG).show()
            return false;
        }

        if(KaKaoUrl.isEmpty()) {
            Toast.makeText(this, "KaKao 주소를 입력해주세요", Toast.LENGTH_LONG).show()
            return false;
        }
        return true;
    }

    fun NumberOfPeople(): Int{
        if(teammemberBtn1.isChecked){
            return 1;
        }
        if(teammemberBtn2.isChecked){
            return 2;
        }
        if(teammemberBtn3.isChecked){
            return 3;
        }
        if(teammemberBtn4.isChecked){
            return 4;
        }

        return 0;
    }

    }

