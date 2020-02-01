package com.tingting.ver01.SearchTeam.MakeTeamPacakge

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tingting.ver01.MakeTeam.RegionSpinnerAdapter
import com.tingting.ver01.Model.IdCallBack
import com.tingting.ver01.Model.ModelTeam
import com.tingting.ver01.R
import com.tingting.ver01.SharedPreference.App
import kotlinx.android.synthetic.main.activity_create_team2.*

class MTeam : AppCompatActivity() {
    var model = ModelTeam(this)
    var TeamNamevar = false
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
        /*addTag.setOnClickListener(){
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
*/
        checkTeamName.setOnClickListener(){
            var a = teamnameET.text.toString()

            model.TeamName(a, object :IdCallBack{
                override fun onSuccess(value: String) {
                        if(value.equals("t")){
                            checkIdMessage.setText("사용 가능한 팀명입니다.")
                            TeamNamevar=true
                        }else{
                            checkIdMessage.setText("이미 존재하는 팀명입니다.")
                            TeamNamevar = false
                        }
                }
            })
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
            if(makeTeam(teamnameET.text.toString(),selectedRegion.text.toString(), number,TeamIntro.text.toString(),teamkakaoET.text.toString())){
                model.makeTeam(App.prefs.myToken.toString(),App.prefs.mygender!!.toInt(),teamnameET.text.toString(), selectedRegion.text.toString(),
                    number,TeamIntro.text.toString(),teamkakaoET.text.toString())
                finish()
            }

        }
        //set radio button color
        TeamSegmentationButton.setTintColor(resources.getColor(R.color.tingtingMain),resources.getColor(R.color.white))
    }

    //this function post teaminformation to server
    fun makeTeam(TeamName:String, Place:String, PeopleNum:Int, TeamIntro:String, KaKaoUrl : String) : Boolean{
        if(TeamName.isEmpty()) {
            Toast.makeText(this, "팀 명을 입력해주세요", Toast.LENGTH_LONG).show()
            return false;
        }

        if(Place.isEmpty()){
            Toast.makeText(this, "지역을 입력해주세요", Toast.LENGTH_LONG).show()
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
