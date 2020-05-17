package com.tingting.ver01.searchTeam.MakeTeamPacakge


import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
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
import com.tingting.ver01.model.TeamDataCallback
import com.tingting.ver01.model.profile.LookMyTeamInfoProfileResponse
import com.tingting.ver01.model.team.lookMyTeamInfoDetail.LookMyTeamInfoDetailResponse
import com.tingting.ver01.model.team.lookMyTeamInfoDetail.LookTeamTagResponse
import com.tingting.ver01.profileTeamInfo.profileTeamInfoNotReady.ProfileTeamInfoNotReadyActivity
import com.tingting.ver01.sharedPreference.App
import kotlinx.android.synthetic.main.activity_revise_team.*
import kotlinx.android.synthetic.main.dialog_tag.view.*

class ReviseTeam : AppCompatActivity() {
    val model : ModelTeam = ModelTeam(this)
    lateinit var region:String
    var spinnerPosition:Int = 0
    var TeamNamevar = false
    var isKaKaoUrlVaild = false
    lateinit var initialTeamname:String
    var tagList : ArrayList<Tag> = ArrayList()
    var tagDatas:ArrayList<Tag> = ArrayList()
    var tags:ArrayList<Int> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_revise_team)

        var teamId = intent.getIntExtra("teamId", 0)

        back.setOnClickListener {
           // val intent = Intent(this, ProfileTeamInfoNotReadyActivity::class.java)
           // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
          //  intent.putExtra("MyTeamId", teamId)
          //  startActivity(intent)
            finish()
        }

        teamkakaoET.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
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

        checkTeamName.setOnClickListener {
            var a = teamnameET.text.toString()

            if(a.equals(initialTeamname)){
                checkIdMessage.text = "사용 가능한 팀명입니다."
                checkIdMessage.setTextColor(getColor(R.color.green))
                TeamNamevar=true
            }else{
                model.TeamName(a, object : CodeCallBack {
                    override fun onSuccess(code: String, value: String) {
                        try{
                            if(code.equals("200")){
                                checkIdMessage.text = "사용 가능한 팀명입니다."
                                checkIdMessage.setTextColor(getColor(R.color.green))
                                TeamNamevar=true
                            }else if(code.equals("400")){
                                checkIdMessage.text="이미 존재하는 팀명입니다."
                                checkIdMessage.setTextColor(getColor(android.R.color.holo_red_dark))
                                TeamNamevar = false
                            }else{
                                Toast.makeText(applicationContext, "일시적인 서버 오류입니다", Toast.LENGTH_LONG).show()
                            }
                        }catch (e:Exception){

                        }

                    }
                })
            }
        }

        // 지역 선택
        val listItem = arrayOf("서울", "부산", "인천", "대구", "대전", "광주"
            , "수원", "울산", "창원", "고양", "용인", "성남", "부천",
            "청주", "안산", "화성", "전주", "천안", "남양주")

        var spinnerAdapter: RegionSpinnerAdapter = RegionSpinnerAdapter(applicationContext, listItem)
        spinner.adapter=spinnerAdapter

        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onNothingSelected(parent: AdapterView<*>) {
                selectedRegion.text = "지역을 선택해주세요."
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View, position:Int, p3:Long) {

                selectedRegion.text = parent!!.getItemAtPosition(position).toString()
                region = parent.getItemAtPosition(position).toString()
            }
        }

        // 태그
        val root: ChipGroup = findViewById(R.id.tagGroup)
        val inflater = LayoutInflater.from(this@ReviseTeam)

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
                var toggle: LabelToggle = multi.getChildAt(i) as LabelToggle
                for(c in tagDatas){
                    if(toggle.text.equals(c)){
                        toggle.isChecked = true
                    }
                }

            }

            // 태그 추가 완료 버튼 이벤트 처리
            dialogView.dialogOK.setOnClickListener {
                // 배열 초기화
                tagDatas.clear()
                tags.clear()
                // checked label -> checkList
                for (id in multi!!.checkedIds) {
                    var toggle: LabelToggle = multi.findViewById(id)
                    // 중복 선택 제거
                    var temp = Tag(toggle.text.toString(), toggle.tag.toString().toInt())
                    if(!tagDatas.contains(temp)) {
                        // text 저장
                        tagDatas.add(Tag(toggle.text.toString(), toggle.tag.toString().toInt()))
                        // tag 저장
                        tags.add(toggle.tag.toString().toInt())
                    }
                }
                // chip group 초기화
                root.removeAllViews()
                // 추가된 태그 표시
                for(c in tagDatas){
                    val chip_item = inflater.inflate(R.layout.chip_item, null, false) as Chip
                    chip_item.text = c.tagName
                    chip_item.tag = c.tagId
                    // 태그 삭제
                    chip_item.setOnCloseIconClickListener { view->
                        root.removeView(view)
                        tagDatas.remove(Tag(c.tagName, c.tagId))
                        tags.remove(c.tagId)

                    }
                    root.addView(chip_item)
                }
                check.dismiss()

                // 로그
                for (t in tags) {
                    Log.i("tag : ", t.toString())

                }
            }}

        var bossId  = intent.getIntExtra("teamBossId",0)
        Log.i("teamId", teamId.toString())

        model.lookTeamTag(object :TeamDataCallback{
            override fun LookTeamTag(data: LookTeamTagResponse) {
                Log.i("size", data.data.tags.size.toString())
                for(i in 0..data.data.tags.size-1) {
                    tagList.add(Tag(data.data.tags.get(i).name, data.data.tags.get(i).id))
                    Log.i("tagList : ", tagList.get(i).tagName)
                }
            }
        })

        model.LookMyTeamInfopPofile(teamId, object : TeamDataCallback {
            override fun LookMyTeamInfoListProfile(data: LookMyTeamInfoProfileResponse) {

                model.LookMyTeamInfo2(teamId, object :TeamDataCallback{
                    override fun LookMyTeaminfoList(data: LookMyTeamInfoDetailResponse) {
                        var a = data.data.teamInfo
                        //var b = data!!.data.teamMembers
                        when(a.max_member_number){
                            2->teammemberBtn2.isChecked = true
                            3->teammemberBtn3.isChecked = true
                            4->teammemberBtn4.isChecked = true
                        }
                        when(a.hasPassword){
                            true -> {
                                hasPassword.isChecked = true
                                Log.i("teamPw", a.password)
                                teamPwET.setText(a.password)
                            }
                            false -> hasPassword.isChecked = false
                        }
                        initialTeamname = a.name
                        spinnerPosition = getPosition(listItem, a.place)
                        spinner.setSelection(spinnerPosition)
                        teamnameET.setText(a.name)
                        // 태그
                        for(i in 0..tagList.size-1){
                            for(num in a.tags){
                                if(tagList.get(i).tagName.equals(num)){
                                    tagDatas.add(Tag(num, tagList.get(i).tagId))
                                }
                            }
                        }
                        for(c in tagDatas){
                            Log.i("tagDatas : ", c.toString())
                            val chip_item = inflater.inflate(R.layout.chip_item, null, false) as Chip
                            chip_item.text = c.tagName
                            chip_item.tag = c.tagId
                            // 태그 삭제
                            chip_item.setOnCloseIconClickListener { view->
                                root.removeView(view)
                                tagDatas.remove(Tag(c.tagName, c.tagId))
                                tags.remove(c.tagId)
                            }
                            root.addView(chip_item)
                        }
                        // 오픈 채팅방 주소
                        teamkakaoET.setText(a.chat_address)

                    }
                })

            }
        })

        createteam2RegisterBtn.setOnClickListener {
            val number : Int = NumberOfPeople()
            Log.d("MakeTeamNumber",number.toString())
            if(makeTeam(teamnameET.text.toString(), TeamNamevar, number,teamkakaoET.text.toString(), hasPassword.isChecked, teamPwET.text.toString(), tags.size)){
                //send info to server
                if(hasPassword.isChecked){
                    model.ReviseTeamInfo(
                        App.prefs.myToken.toString(),region, teamId,teamPwET.text.toString(),App.prefs.mygender.toString()
                        ,teamnameET.text.toString(),number.toString(), tags,teamkakaoET.text.toString(), object :CodeCallBack{
                            override fun onSuccess(code: String, value: String) {
                                if(code.equals("201")){
                                    Toast.makeText(applicationContext, "내 팀 수정에 성공했습니다", Toast.LENGTH_LONG).show()
                                }
                                else if(code.equals("400")){
                                    Toast.makeText(applicationContext, "태그의 수가 올바르지 않습니다", Toast.LENGTH_LONG).show()
                                }
                                else if(code.equals("403")){
                                    Toast.makeText(applicationContext, "수정하고자 하는 팀에 속해있지 않습니다", Toast.LENGTH_LONG).show()

                                }else if(code.equals("500")){
                                    Toast.makeText(applicationContext, "팀 수정 실패", Toast.LENGTH_LONG).show()
                                }else{
                                    Toast.makeText(applicationContext, "일시적인 서버 오류입니다", Toast.LENGTH_LONG).show()
                                    TeamNamevar = false
                                }
                            }
                        })
                }
                else{
                    model.ReviseTeamInfo(App.prefs.myToken.toString(),region, teamId,"",App.prefs.mygender.toString()
                        ,teamnameET.text.toString(),number.toString(),tags,teamkakaoET.text.toString(), object :CodeCallBack{
                            override fun onSuccess(code: String, value: String) {
                                if(code.equals("201")){
                                    Toast.makeText(applicationContext, "내 팀 수정에 성공했습니다", Toast.LENGTH_LONG).show()
                                }
                                else if(code.equals("400")){
                                    Toast.makeText(applicationContext, "태그의 수가 올바르지 않습니다", Toast.LENGTH_LONG).show()
                                }
                                else if(code.equals("403")){
                                    Toast.makeText(applicationContext, "수정하고자 하는 팀에 속해있지 않습니다", Toast.LENGTH_LONG).show()

                                }else if(code.equals("500")){
                                    Toast.makeText(applicationContext, "팀 수정 실패", Toast.LENGTH_LONG).show()
                                }else{
                                    Toast.makeText(applicationContext, "일시적인 서버 오류입니다", Toast.LENGTH_LONG).show()
                                    TeamNamevar = false
                                }
                            }
                        })
                }

                val intent = Intent(this, ProfileTeamInfoNotReadyActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.putExtra("MyTeamId", teamId)
                startActivity(intent)
                finish()

            }

        }
        //set radio buttono color
        TeamSegmentationButton.setTintColor(resources.getColor(R.color.tingtingMain),resources.getColor(R.color.white))
    }

    //this function post revise team info to server
    fun makeTeam(TeamName:String, TeamNamevar:Boolean, PeopleNum:Int, KaKaoUrl : String, hasPassword:Boolean, TeamPw:String, TagsLength:Int
    ) : Boolean{
        if(TeamName.isEmpty()) {
            Toast.makeText(this, "팀명을 입력해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if(TeamNamevar != true) {
            Toast.makeText(this, "팀명 중복 여부를 확인해주세요", Toast.LENGTH_LONG).show()
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

        // 비밀 방의 경우
        if(hasPassword){
            if(TeamPw.isEmpty()){
                Toast.makeText(this, "비밀번호를 설정해주세요", Toast.LENGTH_LONG).show()
                return false
            }else if(TeamPw.length!=4){
                Toast.makeText(this, "비밀번호를 4자리 숫자로 설정해주세요", Toast.LENGTH_LONG).show()
                return false
            }
        }
        if(TagsLength < 2 || TagsLength > 5){
            Toast.makeText(this, "태그는 최소 2개, 최대 5개로 입력해주세요", Toast.LENGTH_LONG).show()
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

    fun getPosition(listItem:Array<String>, locationId:String): Int {
        var position = 0

        for(i in 0..listItem.size-1){
            if(listItem.get(i).equals(locationId))
                position = i
        }

        return position

    }
    data class Tag(var tagName:String, var tagId:Int)
}
