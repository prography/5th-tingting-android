package com.tingting.ver01.searchTeam.MakeTeamPacakge

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.nex3z.togglebuttongroup.MultiSelectToggleGroup
import com.nex3z.togglebuttongroup.button.LabelToggle
import com.tingting.ver01.MakeTeam.RegionSpinnerAdapter
import com.tingting.ver01.R
import com.tingting.ver01.model.CodeCallBack
import com.tingting.ver01.model.ModelTeam
import com.tingting.ver01.sharedPreference.App
import com.tingting.ver01.view.Main.MainActivity
import kotlinx.android.synthetic.main.activity_create_team2.*
import kotlinx.android.synthetic.main.dialog_tag.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class MTeam : AppCompatActivity() {
    var model = ModelTeam(this)
    var TeamNamevar = false
    var clicked: Boolean = false
    var isKaKaoUrlVaild = true
    var tags: ArrayList<Int> = ArrayList<Int>()
    var checkedList: ArrayList<Tag> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_team2)


//        kakaoUrlCheckTrueMessage.visibility = View.INVISIBLE
//        kakaoUrlCheckFalseMessage.visibility = View.INVISIBLE


        back.setOnClickListener {


            finish()
        }

        // 지역 선택
        val listItem = arrayOf(
            "서울", "부산", "인천", "대구", "대전", "광주"
            , "수원", "울산", "창원", "고양", "용인", "성남", "부천",
            "청주", "안산", "화성", "전주", "천안", "남양주"
        )

        var spinnerAdapter: RegionSpinnerAdapter =
            RegionSpinnerAdapter(applicationContext, listItem)
        spinner.adapter = spinnerAdapter

        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>) {
                selectedRegion.text = "지역을 선택해주세요."
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                p3: Long
            ) {

                selectedRegion.text = parent!!.getItemAtPosition(position).toString()
            }
        }


        // 태그
        val root:ChipGroup = findViewById(R.id.tagGroup)
        val inflater = LayoutInflater.from(this@MTeam)

        addTag.setOnClickListener() {
            val tagDialog = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.dialog_tag, null)

            tagDialog.setView(dialogView)
            val check = tagDialog.show()

            // 태그 목록 처리

            var multi: MultiSelectToggleGroup = dialogView.findViewById(R.id.tagRoot)

            // listener
            multi.setOnCheckedChangeListener(object :
                MultiSelectToggleGroup.OnCheckedStateChangeListener {
                override fun onCheckedStateChanged(
                    group: MultiSelectToggleGroup?,
                    checkedId: Int,
                    isChecked: Boolean
                ) {
                }
            })

            // 기존에 선택된 태그 표시
            for(i in 0..multi.childCount-1){
                var toggle:LabelToggle = multi.getChildAt(i) as LabelToggle
                for(c in checkedList){
                    if(toggle.text.equals(c)){
                        toggle.isChecked = true
                    }
                }

            }

            // 태그 추가 완료 버튼 이벤트 처리
            dialogView.dialogOK.setOnClickListener {
                // 배열 초기화
                checkedList.clear()
                tags.clear()
                // checked label -> checkList
                for (id in multi!!.checkedIds) {
                    var toggle: LabelToggle = multi.findViewById(id)
                    // 중복 선택 제거
                    var temp = Tag(toggle.text.toString(), toggle.tag.toString().toInt())
                    if(!checkedList.contains(temp)) {
                        // text 저장
                        checkedList.add(Tag(toggle.text.toString(), toggle.tag.toString().toInt()))
                        // tag 저장
                        tags.add(toggle.tag.toString().toInt())
                    }
                }
                // chip group 초기화
                root.removeAllViews()
                // 추가된 태그 표시
                for(c in checkedList){
                    val chip_item = inflater.inflate(R.layout.chip_item, null, false) as Chip
                    chip_item.text = c.tagName
                    chip_item.tag = c.tagId
                    // 태그 삭제
                    chip_item.setOnCloseIconClickListener { view->
                        root.removeView(view)
                        checkedList.remove(Tag(c.tagName, c.tagId))
                        tags.remove(c.tagId)

                    }
                    root.addView(chip_item)
                }
                check.dismiss()

                // 로그
                for (t in tags) {
                    Log.i("tag : ", t.toString())

                }
            }
        }

        checkTeamName.setOnClickListener {
            var a = teamnameET.text.toString()

            model.TeamName(a, object : CodeCallBack {
                @SuppressLint("ResourceAsColor")
                override fun onSuccess(code: String, value: String) {


                    try {
                        if(a.length > 8){
                            checkIdMessage.text = "팀명을 8자 이하로 만들어주세요."
                            checkIdMessage.setTextColor(getColor(android.R.color.holo_red_dark))
                            TeamNamevar = false
                        }else if (code.equals("200")) {
                            checkIdMessage.text = "사용 가능한 팀명입니다."
                            checkIdMessage.setTextColor(getColor(R.color.green))
                            TeamNamevar = true
                        } else if (code.equals("400")) {
                            checkIdMessage.text = "이미 존재하는 팀명입니다."
                            checkIdMessage.setTextColor(getColor(android.R.color.holo_red_dark))
                            TeamNamevar = false
                        } else {
                            Toast.makeText(applicationContext, "일시적인 서버 오류입니다", Toast.LENGTH_LONG)
                                .show()
                            TeamNamevar = false
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            })
        }

        // 태그 다이얼로그
        @SuppressLint("ResourceAsColor")
        fun tagOnClick(v: Button) {
            v.setBackgroundResource(R.color.tingtingMain)
            v.setTextColor(Color.parseColor("#ffffff"))
        }

        createteam2RegisterBtn.setOnClickListener {
            val number: Int = NumberOfPeople()
            Log.d("MakeTeamNumber", number.toString())

                if (makeTeam(
                        teamnameET.text.toString(),
                        TeamNamevar,
                        selectedRegion.text.toString(),
                        number,
                        teamPwET.text.toString(),
                        tags.size
                    )
                ) {
                    // 방 비밀번호 설정 X
                    if (!hasPassword.isChecked) {
                        model.makeTeam(
                            App.prefs.myToken.toString(),
                            MainActivity.gender,
                            teamnameET.text.toString(),
                            selectedRegion.text.toString(),
                            "",
                            number,
                            tags,
                            object : CodeCallBack {
                                override fun onSuccess(code: String, value: String) {
                                    try {
                                        if (code.equals("201")) {
                                            Toast.makeText(
                                                applicationContext,
                                                "팀 생성 성공",
                                                Toast.LENGTH_LONG
                                            ).show()

                                        } else if (code.equals("400")) {
                                            Toast.makeText(
                                                applicationContext,
                                                "팀 생성 실패",
                                                Toast.LENGTH_LONG
                                            ).show()

                                        } else {
                                            Toast.makeText(
                                                applicationContext,
                                                "일시적인 서버 오류입니다",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }
                                }
                            })
                    }
                    // 방 비밀번호 설정 O
                    else {
                        model.makeTeam(App.prefs.myToken.toString(),
                            MainActivity.gender,
                            teamnameET.text.toString(),
                            selectedRegion.text.toString(),
                            teamPwET.text.toString(),
                            number,
                            tags,
                            object : CodeCallBack {
                                override fun onSuccess(code: String, value: String) {
                                    try {
                                        if (code.equals("201")) {
                                            Toast.makeText(
                                                applicationContext,
                                                "팀 생성 성공",
                                                Toast.LENGTH_LONG
                                            ).show()

                                        } else if (code.equals("400")) {
                                            Toast.makeText(
                                                applicationContext,
                                                "팀 생성 실패",
                                                Toast.LENGTH_LONG
                                            ).show()

                                        } else {
                                            Toast.makeText(
                                                applicationContext,
                                                "일시적인 서버 오류입니다",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }
                                }
                            })
                    }
                    finish()
                }

        }

//        teamkakaoET.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable?) {
//
//            }
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                val regexKakao = Regex("^https://open.kakao.com/.*")
//                if (regexKakao.matches(teamkakaoET.text.toString())) {
//                    isKaKaoUrlVaild = true
//                    kakaoUrlCheckTrueMessage.visibility = View.VISIBLE
//                    kakaoUrlCheckFalseMessage.visibility = View.INVISIBLE
//
//                } else {
//                    isKaKaoUrlVaild = false
//                    kakaoUrlCheckTrueMessage.visibility = View.INVISIBLE
//                    kakaoUrlCheckFalseMessage.visibility = View.VISIBLE
//                }
//            }
//        })
        //set radio button color
        TeamSegmentationButton.setTintColor(
            resources.getColor(R.color.tingtingMain),
            resources.getColor(R.color.white)
        )
    }

    //this function post teaminformation to server
    fun makeTeam(
        TeamName: String,
        TeamNamevar: Boolean,
        Place: String,
        PeopleNum: Int,
        Password:String,
        TagsLength:Int
    ): Boolean {
        if (TeamName.isEmpty()) {
            Toast.makeText(this, "팀명을 입력해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if (TeamNamevar != true) {
            Toast.makeText(this, "팀명을 확인해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if (Place.isEmpty()) {
            Toast.makeText(this, "지역을 입력해주세요", Toast.LENGTH_LONG).show()
            return false
        }
        if (PeopleNum == 0) {
            Toast.makeText(this, "인원수를 선택해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(Password.length!=0){

            if(Password.length!=4){
                Toast.makeText(this, "비밀번호는 4자리 숫자로 입력해주세요", Toast.LENGTH_LONG).show()
                return false
            }

        }

        if(TagsLength < 2 || TagsLength > 5){
            Toast.makeText(this, "태그는 최소 2개, 최대 5개로 입력해주세요", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    fun NumberOfPeople(): Int {

        if (teammemberBtn2.isChecked) {
            return 2
        }

        if (teammemberBtn3.isChecked) {
            return 3
        }

        if (teammemberBtn4.isChecked) {
            return 4
        }

        return 0
    }
    data class Tag(var tagName:String, var tagId:Int)

}