package com.example.tintint_jw.SearchTeam.MakeTeam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.tintint_jw.Model.ModelTeam
import com.example.tintint_jw.R
import com.example.tintint_jw.SharedPreference.App
import kotlinx.android.synthetic.main.activity_create_team2.*

class MakeTeam : AppCompatActivity() {
    var model = ModelTeam(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_team2)

        back.setOnClickListener(){

            finish()

        }

        createteam2RegisterBtn.setOnClickListener(){
            val number : Int = NumberOfPeople()
            Log.d("MakeTeamNumber",number.toString())
            if(makeTeam(teamnameET.text.toString(),number,TeamIntro.text.toString(),teamkakaoET.text.toString())){
                //send info to server
                //token: String, owner_id:String,  password:String,  gender:Int,  name:String,
                //                  max_member_number:Int, intro : String,  chat_address:String

                model.makeTeam(App.prefs.myToken.toString(),"0000",App.prefs.mygender!!.toInt(),teamnameET.text.toString(),
                    number,TeamIntro.text.toString(),teamkakaoET.text.toString())

                finish()
            }
            //this is only for test
            finish()
        }
        //set radio buttono color
        TeamSegmentationButton.setTintColor(resources.getColor(R.color.tingtingMain),resources.getColor(R.color.white))
    }

    //this function post teaminformation to server
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
