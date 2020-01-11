package com.example.tintint_jw.MakeTeam

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Toast
import com.example.tintint_jw.Model.ModelTeam
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.tintint_jw.R
import com.example.tintint_jw.SharedPreference.App
import kotlinx.android.synthetic.main.activity_create_team2.*
import kotlinx.android.synthetic.main.dialog_tag.view.*

class MakeTeam : AppCompatActivity() {
    var model = ModelTeam(this)

    var clicked:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_team2)

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

            override fun onNothingSelected(parent:AdapterView<*>) {
                selectedRegion.setText("지역을 선택해주세요.")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View, position:Int, p3:Long) {

                selectedRegion.setText(parent!!.getItemAtPosition(position).toString())
            }
        })


        // 태그
        addTag.setOnClickListener(){
            val tagDialog = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.dialog_tag,null)

            tagDialog.setView(dialogView)
            val check = tagDialog.show()


            dialogView.dialogCancel.setOnClickListener{
                check.dismiss()
            }

            dialogView.dialogOK.setOnClickListener{
                finish()
            }

        }

        // 태그 다이얼로그
        @SuppressLint("ResourceAsColor")
        fun tagOnClick(v:Button){
            v.setBackgroundResource(R.color.tingtingMain)
            v.setTextColor(Color.parseColor("#ffffff"))
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
        //set radio button color
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
