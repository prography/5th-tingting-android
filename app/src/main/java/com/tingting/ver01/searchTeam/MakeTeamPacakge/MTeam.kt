package com.tingting.ver01.searchTeam.MakeTeamPacakge

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Color.WHITE
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.tingting.ver01.MakeTeam.RegionSpinnerAdapter

import com.tingting.ver01.R
import com.tingting.ver01.model.CodeCallBack
import com.tingting.ver01.model.ModelTeam
import com.tingting.ver01.sharedPreference.App

import kotlinx.android.synthetic.main.activity_create_team2.*
import kotlinx.android.synthetic.main.dialog_tag.view.*
import kotlinx.android.synthetic.main.dialog_univ_list.view.*

class MTeam : AppCompatActivity() {
    var model = ModelTeam(this)
    var TeamNamevar = false
    var clicked:Boolean = false
    var isKaKaoUrlVaild = false
    var tags:ArrayList<String> = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_team2)


        kakaoUrlCheckTrueMessage.visibility = View.INVISIBLE
        kakaoUrlCheckFalseMessage.visibility = View.INVISIBLE


        back.setOnClickListener {


            finish()
        }

        // 지역 선택
        val listItem = arrayOf("서울", "부산", "인천", "대구", "대전", "광주"
            , "수원", "울산", "창원", "고양", "용인", "성남", "부천",
            "청주", "안산", "화성", "전주", "천안", "남양주")

        var spinnerAdapter: RegionSpinnerAdapter = RegionSpinnerAdapter(applicationContext, listItem)
        spinner.adapter=spinnerAdapter

        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onNothingSelected(parent:AdapterView<*>) {
                selectedRegion.text = "지역을 선택해주세요."
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View, position:Int, p3:Long) {

                selectedRegion.text = parent!!.getItemAtPosition(position).toString()
            }
        }


        // 태그
        addTag.setOnClickListener(){
            val tagDialog = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.dialog_tag,null)

            tagDialog.setView(dialogView)
            val check = tagDialog.show()

            dialogView.dialogOK.setOnClickListener{
                //finish()
                check.dismiss()
            }

            // 태그 목록 처리
            val root:LinearLayout = dialogView.findViewById<LinearLayout>(R.id.tagRootLayout)
            var count:Int = root.childCount
            Log.i("count", count.toString())
            for(i in 0..count-1){
                var child:LinearLayout = root.getChildAt(i) as LinearLayout
                var childCount:Int = child.childCount
                Log.i("childcount", childCount.toString())
                for(j in 0..childCount-1){
                    var children:AppCompatButton = child.getChildAt(j) as androidx.appcompat.widget.AppCompatButton
                    Log.i("children", children.text.toString())
                    // 클릭 이벤트
                    children.setOnClickListener {
                        var tagInInt:Int = children.tag.toString().trim().toInt()
                        when(tagInInt){
                            in 1..12->{tags.add(children.text.toString())
                                Log.i("tag", children.text.toString())
                                children.setTextColor(Color.WHITE)
                                children.setBackgroundDrawable(resources.getDrawable(R.drawable.round_edge_pink))
                            }
                        }
                        for(i in 0..tags.size-1)
                            Log.i("tags", tags.get(i))
                    }
                    }
                }
            }
        checkTeamName.setOnClickListener {
            var a = teamnameET.text.toString()

            model.TeamName(a, object : CodeCallBack {
                @SuppressLint("ResourceAsColor")
                override fun onSuccess(code: String, value: String) {
                    try{
                        if(code.equals("200")){
                            checkIdMessage.text="사용 가능한 팀명입니다."
                            checkIdMessage.setTextColor(getColor(R.color.green))
                            TeamNamevar=true
                        }else if(code.equals("400")){
                            checkIdMessage.text="이미 존재하는 팀명입니다."
                            checkIdMessage.setTextColor(getColor(android.R.color.holo_red_dark))
                            TeamNamevar = false
                        }else{
                            Toast.makeText(applicationContext, "일시적인 서버 오류입니다", Toast.LENGTH_LONG).show()
                            TeamNamevar = false
                        }
                    }catch (e:Exception){

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

        createteam2RegisterBtn.setOnClickListener {
            val number : Int = NumberOfPeople()
            Log.d("MakeTeamNumber",number.toString())
            if(isKaKaoUrlVaild){
                if(makeTeam(teamnameET.text.toString(),TeamNamevar, selectedRegion.text.toString(), number,teamkakaoET.text.toString())){
                    // 방 비밀번호 설정 X
                    if(!hasPassword.isChecked){
                        model.makeTeam(
                            App.prefs.myToken.toString(),App.prefs.mygender!!.toInt(),teamnameET.text.toString(), selectedRegion.text.toString(),
                            null.toString(), number,tags,teamkakaoET.text.toString(), object :CodeCallBack{
                                override fun onSuccess(code: String, value: String) {
                                    try{
                                        if(code.equals("201")){
                                            Toast.makeText(applicationContext, "팀 생성 성공", Toast.LENGTH_LONG).show()

                                        }else if(code.equals("400")){
                                            Toast.makeText(applicationContext, "팀 생성 실패", Toast.LENGTH_LONG).show()

                                        }else{
                                            Toast.makeText(applicationContext, "일시적인 서버 오류입니다", Toast.LENGTH_LONG).show()
                                        }
                                    }catch (e:Exception){

                                    }
                                }
                            })
                    }
                    // 방 비밀번호 설정 O
                    else{
                        model.makeTeam(App.prefs.myToken.toString(),App.prefs.mygender!!.toInt(),teamnameET.text.toString(), selectedRegion.text.toString(),
                            teamPwET.text.toString(), number,tags,teamkakaoET.text.toString(), object :CodeCallBack{
                                override fun onSuccess(code: String, value: String) {
                                    try{
                                        if(code.equals("201")){
                                            Toast.makeText(applicationContext, "팀 생성 성공", Toast.LENGTH_LONG).show()

                                        }else if(code.equals("400")){
                                            Toast.makeText(applicationContext, "팀 생성 실패", Toast.LENGTH_LONG).show()

                                        }else{
                                            Toast.makeText(applicationContext, "일시적인 서버 오류입니다", Toast.LENGTH_LONG).show()
                                        }
                                    }catch (e:Exception){

                                    }
                                }
                            })
                    }
                    finish()
                }
            }else{
                Toast.makeText(applicationContext,"오픈 카카오톡 주소를 확인해주세요",Toast.LENGTH_LONG).show()
            }
        }

        teamkakaoET.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val regexKakao = Regex("^https://open.kakao.com/.*")
                if(regexKakao.matches(teamkakaoET.text.toString())){
                    isKaKaoUrlVaild = true
                    kakaoUrlCheckTrueMessage.visibility = View.VISIBLE
                    kakaoUrlCheckFalseMessage.visibility = View.INVISIBLE

                }else{
                    isKaKaoUrlVaild = false
                    kakaoUrlCheckTrueMessage.visibility = View.INVISIBLE
                    kakaoUrlCheckFalseMessage.visibility = View.VISIBLE
                }
            }
        })
        //set radio button color
        TeamSegmentationButton.setTintColor(resources.getColor(R.color.tingtingMain),resources.getColor(R.color.white))
    }

    //this function post teaminformation to server
    fun makeTeam(TeamName:String, TeamNamevar:Boolean, Place:String, PeopleNum:Int, KaKaoUrl : String) : Boolean{
        if(TeamName.isEmpty()) {
            Toast.makeText(this, "팀명을 입력해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(TeamNamevar != true) {
            Toast.makeText(this, "팀명 중복 여부를 확인해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(Place.isEmpty()){
            Toast.makeText(this, "지역을 입력해주세요", Toast.LENGTH_LONG).show()
            return false
        }
        if(PeopleNum==0){
            Toast.makeText(this,"인원수를 선택해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(KaKaoUrl.isEmpty()) {
            Toast.makeText(this, "KaKao 주소를 입력해주세요", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    fun NumberOfPeople(): Int{

        if(teammemberBtn2.isChecked){
            return 2
        }

        if(teammemberBtn3.isChecked){
            return 3
        }

        if(teammemberBtn4.isChecked){
            return 4
        }

        return 0
    }

}