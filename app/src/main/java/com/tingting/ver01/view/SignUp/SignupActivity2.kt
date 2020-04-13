package com.tingting.ver01.view.SignUp

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.niwattep.materialslidedatepicker.SlideDatePickerDialogCallback
import com.tingting.ver01.R
import com.tingting.ver01.model.CodeCallBack
import com.tingting.ver01.model.ModelSignUp
import com.tingting.ver01.model.ProfileCallBack
import com.tingting.ver01.sharedPreference.App
import com.tingting.ver01.view.Auth.PictureRegisterActivity
import kotlinx.android.synthetic.main.activity_sign_up2.*
import kotlinx.coroutines.*
import java.util.*

class SignupActivity2 : AppCompatActivity(), SlideDatePickerDialogCallback {

    @SuppressLint("ResourceAsColor")
    var model: ModelSignUp = ModelSignUp(this)
    var nickNameval = false
    var heightInput = false
    var dateInput = false
    var byear=0
    var currentYear = Calendar.getInstance().get(Calendar.YEAR)

    lateinit var mHandler: Handler
    lateinit var mRunnable: Runnable
    var scope = CoroutineScope(Dispatchers.Main)

    override fun onPositiveClick(day: Int, month: Int, year: Int, calendar: Calendar) {

        pickBirth.text = year.toString() + "-" + month.toString() + "-" + day.toString()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up2)

        mHandler = Handler()
        changeButton()

        //변수 초기화
        var male: Boolean = true
        var female: Boolean = false

        val cal = Calendar.getInstance()

        val c = Calendar.getInstance()
        byear = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        //set Toolbar


        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        //initialize date picker dialog
        val dpd = DatePickerDialog(
            this@SignupActivity2,
            android.R.style.Theme_Holo_Dialog,

            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                var month = ""
                var day = ""
                if (monthOfYear < 10) {
                    month = "0" + (monthOfYear + 1).toString()
                } else {
                    month = (monthOfYear + 1).toString()
                }
                if (dayOfMonth < 10) {
                    day = "0" + dayOfMonth.toString()
                } else {
                    day = dayOfMonth.toString()
                }

                pickBirth.text = year.toString() + "-" + month + "-" + day
                byear = year
                dateInput = true
                changeButton()

            },
            byear,
            month,
            day
        )

        // 1990~2002년생
        c.add(Calendar.YEAR, -30)
        dpd.datePicker.minDate = c.timeInMillis
        c.add(Calendar.YEAR, 12)
        dpd.datePicker.maxDate = c.timeInMillis
        // 2000.01.01로 초기화
        dpd.datePicker.init(2000, 1, 1, null)

        pickBirth.setOnClickListener {
            dpd.show()
            height.requestFocus()
        }


        //back button
        back.setOnClickListener {
            finish()
        }

        /*CoroutineScope(Dispatchers.IO).launch {
            launch(Dispatchers.Main) {
                if(checkEmptyField(NickName.text.toString(),
                        pickBirth.text.toString(),
                        height.text.toString())&&nickNameval)
                {
            next.isEnabled = true
            Log.i("next", "enabled")
            next.setOnClickListener {
                App.prefs.myname = NickName.text.toString()
                App.prefs.mybirth = pickBirth.text.toString()
                App.prefs.myheight = height.text.toString()

                if (female) {
                    App.prefs.mygender = "1"
                } else {
                    App.prefs.mygender = "0"
                }
                val intent =
                    Intent(applicationContext, PictureRegisterActivity::class.java);
                //
                startActivity(intent)
            }}
            else{
                next.isEnabled = false
                Log.i("next", "disabled")

            }
        }}*/

        next.setOnClickListener {

            if (checkEmptyField(
                    NickName.text.toString(),
                    pickBirth.text.toString(),
                    height.text.toString()
                ) && currentYear - byear >= 19 && heightInput) {
                App.prefs.myname = NickName.text.toString()
                App.prefs.mybirth = pickBirth.text.toString()
                App.prefs.myheight = height.text.toString()

                if (female) {
                    App.prefs.mygender = "1"
                } else {
                    App.prefs.mygender = "0"
                }
                if (nickNameval) {

                    if(App.prefs.myLoginType.equals("local")){
                        model.signUP(App.prefs.mylocal_id.toString(),App.prefs.mypassword.toString()
                            ,App.prefs.mygender.toString(),App.prefs.myname.toString(),App.prefs.mybirth.toString()
                            ,App.prefs.myauthenticated_address.toString(),App.prefs.myheight.toString(),
                            applicationContext)

                    }else if(App.prefs.myLoginType.equals("kakao")){

                        model.KakaoSignUp(App.prefs.myname.toString(),App.prefs.mybirth.toString(),App.prefs.myheight.toString()
                            ,App.prefs.myauthenticated_address.toString(),App.prefs.mygender.toString(), object :
                                ProfileCallBack {
                                override fun kakaoLogin(success: String) {
                                    if(success.equals("success")){
                                        Toast.makeText(applicationContext,"회원 가입에 성공했습니다",Toast.LENGTH_LONG).show()
                                    }else{
                                        Toast.makeText(applicationContext,"회원 가입에 실패했습니다.",Toast.LENGTH_LONG).show()
                                    }
                                }
                            },applicationContext)
                    }
                } else {
                    Toast.makeText(applicationContext, "닉네임 중복검사를 해주세요", Toast.LENGTH_LONG)
                        .show()
                }
        }else if (currentYear - byear < 19){
                Toast.makeText(applicationContext, "20살 미만은 가입 할 수 없습니다.", Toast.LENGTH_LONG)
                    .show()
            }
            else if (!heightInput){
                Toast.makeText(applicationContext, "키는 50cm 와 250cm 사이여야 합니다.", Toast.LENGTH_LONG)
                    .show()
            }

        }

        NickName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkNick(checknickmessage,NickName)
                changeButton()
            }
        })

        height.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, count: Int) {
                try{

                    heightInput = Integer.parseInt(height.text.toString())< 250 && Integer.parseInt(height.text.toString()) > 50
                changeButton()
            }catch (e : java.lang.Exception){
                    if(height.text.toString() !=null)
                    Toast.makeText(applicationContext,"키는 숫자만 입력 가능합니다.",Toast.LENGTH_LONG).show()
                }
            }
        })
        //닉네임 체크 버튼
        checkNickname.setOnClickListener {
            var keyBoardDown = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            keyBoardDown.hideSoftInputFromWindow(NickName.windowToken,0)

            if (model.CheckDuplicateName(NickName.text.toString(), object : CodeCallBack {
                    override fun onSuccess(code: String, value: String) {
                        var scope = CoroutineScope(Dispatchers.Main)
                        runBlocking {
                            scope.launch {
                                try{
                                    if(code.equals("200")){
                                        nickNameval = true
                                        checknickmessage.text = "사용 가능한 닉네임입니다."
                                        checknickmessage.visibility = View.VISIBLE
                                        checknickmessage.setTextColor(getColor(R.color.green))
                                    }else if(code.equals("400")){
                                        nickNameval = false
                                        checknickmessage.layoutParams.height =
                                            (20 * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
                                        checknickmessage.visibility = View.VISIBLE
                                        checknickmessage.text = "이미 사용중인 닉네임입니다."
                                        checknickmessage.setTextColor(getColor(android.R.color.holo_red_dark))
                                    }else{
                                        nickNameval = false
                                        Toast.makeText(applicationContext, "일시적인 서버 오류입니다", Toast.LENGTH_LONG).show()
                                    }
                                }
                                catch (e:Exception){

                                }
                                changeButton()
                            }

                        }
                    }
                })) {
            }

        }

        //성별 여자를 클릭하면 색이 바뀜
        genderFemale.setOnClickListener {
            female = true
            male = false
            bgToWhite(genderMale, genderMain, genderMaleTv)
            bgToPink(genderFemale, genderMain, genderFemaleTv)
        }

        //성별 남자를 클릭하면 색이 바뀜
        genderMale.setOnClickListener {
            male = true
            female = false
            bgToPink(genderMale, genderMain, genderMaleTv)
            bgToWhite(genderFemale, genderMain, genderFemaleTv)
        }

    }

    //배경화면을 흰색으로 바꿔주는 코드
    @SuppressLint("ResourceAsColor")
    fun bgToWhite(li: LinearLayout, li2: LinearLayout, text: TextView) {
        li.setBackgroundResource(R.drawable.whole_white)
        li2.setBackgroundResource(R.drawable.edge_gray_whole)
        text.setTextColor(getColor(R.color.subtext))
    }

    //배경화면을 핑크색으로 바꿔주는 코드
    @SuppressLint("ResourceAsColor")
    fun bgToPink(li: LinearLayout, li2: LinearLayout, text: TextView) {
        li.setBackgroundResource(R.drawable.whole_pink)
        li2.setBackgroundResource(R.drawable.edge_gray_whole)
        text.setTextColor(Color.WHITE)
    }


    // password legnth is more than 8, At least one number and one character should be include .


    fun checkEmptyField(
        nickName: String,
        pickBirth: String,
        height: String
        /*school: String,
        hobby: String,
        character: String*/
    ): Boolean {

        if (nickName.isEmpty()) {
            Toast.makeText(applicationContext, "닉네임값을 확인해주세요", Toast.LENGTH_LONG).show()
            return false
        }
        if (pickBirth.isEmpty()) {
            Toast.makeText(applicationContext, "생년월일을 확인 해주세요.", Toast.LENGTH_LONG).show()
            return false
        }

        if (height.isEmpty()) {
            Toast.makeText(applicationContext, "키 값을 확인해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        return true

    }

    fun checkNick(cw: TextView, nick:EditText): Boolean {
        var regExpId = Regex("^[0-9a-z가-힣]")

        if (regExpId.matches(nick.text.toString())) {
            if(nick.text.length>1 &&nick.text.length<9){
                cw.layoutParams.height =
                    (20 * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
                cw.text = "사용가능한 닉네임입니다."
            }else{
                cw.layoutParams.height =
                    (20 * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
                cw.text = " 2~8자,영어,한글,숫자만 입력가능합니다 "

                nickNameval = false
                checknickmessage.visibility = View.VISIBLE
                checknickmessage.setTextColor(getColor(android.R.color.holo_red_dark))
            }
        } else {
            cw.text = "중복확인을 해주세요"
            checknickmessage.visibility = View.VISIBLE
            nickNameval = false
            checknickmessage.setTextColor(getColor(android.R.color.holo_red_dark))
        }
        return true
    }

    private fun changeButton(){
        next.isEnabled = nickNameval&&dateInput&&heightInput
    }
}