package com.tingting.ver01.SearchTeam.MakeTeamPacakge

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tingting.ver01.MakeTeam.RegionSpinnerAdapter
import com.tingting.ver01.Model.ModelTeam
import com.tingting.ver01.Model.Team.LookIndivisualTeam.IndivisualTeamResponse
import com.tingting.ver01.Model.TeamDataCallback
import com.tingting.ver01.R
import com.tingting.ver01.SharedPreference.App
import kotlinx.android.synthetic.main.activity_create_team2.*
import kotlinx.android.synthetic.main.activity_create_team2.back

class ReviseTeam : AppCompatActivity() {
    val model : ModelTeam = ModelTeam(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_revise_team)
        back.setOnClickListener(){

            finish()
        }

        // 지역 선택
        val listItem = arrayOf("서울", "부산", "인천", "대구", "대전", "광주"
            , "수원", "울산", "창원", "고양", "용인", "성남", "부천",
            "청주", "안산", "화성", "전주", "천안", "남양주")

        var spinnerAdapter: RegionSpinnerAdapter = RegionSpinnerAdapter(applicationContext, listItem)
        spinner.adapter=spinnerAdapter

        spinner?.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener{

            override fun onNothingSelected(parent: AdapterView<*>) {
                selectedRegion.setText("지역을 선택해주세요.")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View, position:Int, p3:Long) {

                selectedRegion.setText(parent!!.getItemAtPosition(position).toString())
            }
        })

        var bossId  = intent.getIntExtra("teamBossId",0)
        var teamId = intent.getIntExtra("teamId", 0)
        Log.i("teamId", teamId.toString())

        model.showIndivisualTeamList(App.prefs.myToken.toString(), teamId ,object:
            TeamDataCallback {
            override fun onIndivisualResult(data: IndivisualTeamResponse?, start: Int, end: Int) {
                super.onIndivisualResult(data, start, end)
                var a = data!!.data.teamInfo
                //var b = data!!.data.teamMembers
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

