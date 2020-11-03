package com.tingting.ver01.view.SignUp

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.app.DatePickerDialog
import android.content.Intent
import android.content.res.Resources
import android.graphics.Point
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.tingting.ver01.R
import com.tingting.ver01.model.CodeCallBack
import com.tingting.ver01.model.ModelSignUp
import com.tingting.ver01.sharedPreference.App
import com.tingting.ver01.view.Main.MainActivity
import kotlinx.android.synthetic.main.activity_sign_up1_v2.*
import kotlinx.android.synthetic.main.activity_sign_up1_v2.birth
import kotlinx.android.synthetic.main.activity_sign_up1_v2.birthInputLayout
import kotlinx.android.synthetic.main.activity_sign_up1_v2.genderPicker
import kotlinx.android.synthetic.main.activity_sign_up1_v2.height
import kotlinx.android.synthetic.main.activity_sign_up1_v2.heightInputLayout
import kotlinx.android.synthetic.main.activity_sign_up1_v2.introTv
import kotlinx.android.synthetic.main.activity_sign_up1_v2.manRadio
import kotlinx.android.synthetic.main.activity_sign_up1_v2.next
import kotlinx.android.synthetic.main.activity_sign_up1_v2.nickNameId
import kotlinx.android.synthetic.main.activity_sign_up1_v2.nickNameInputLayout
import kotlinx.android.synthetic.main.activity_sign_up1_v2.toolbar2
import kotlinx.android.synthetic.main.activity_sign_up2_v2.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.text.SimpleDateFormat
import java.util.*


class SignupActivity1 : AppCompatActivity(), TextView.OnEditorActionListener,  View.OnFocusChangeListener  {



    var model: ModelSignUp = ModelSignUp(this)

    var idcheckValue = false
    var passwordCheckValue = false
    var passwordDuplicateCheckValue = false
    var nickNameval = false
    var heightInput = false
    var dateInput = false
    var cal = Calendar.getInstance()
    lateinit var dateSetListener: DatePickerDialog.OnDateSetListener
    var genderValue = 0
    var scope = CoroutineScope(Dispatchers.Main)

    val introBirth = "생일을 선택해 주세요."
    val introHeight = "키를 선택해 주세요."


    var windowHeight = 0
    lateinit var loginTextWatcher: TextWatcher
    lateinit var passwordTextWatcher: TextWatcher
    lateinit var passwordCheckTextWatcher: TextWatcher
    lateinit var nickNameWatcher: TextWatcher
    lateinit var heightWatcher: TextWatcher

    enum class IntroText(label : String) {

        Login("로그인 ID를입력해주세요."),
        Password("비밀번호를 입력해주세요."),
        PasswordCheck("비밀번호를 다시 입력해주세요."),
        LastBtn("다음 버튼을 눌러주세요.");

        fun next() = when(this){
            Login -> Password
            Password -> PasswordCheck
            PasswordCheck -> LastBtn
            LastBtn -> LastBtn
        }

    }

    private var intro : IntroText = IntroText.Login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_sign_up1_v2)

        windowHeight = getWindowHeightSize()

        pickerListener()
        // 뒤로가기
        toolbar2.setNavigationOnClickListener {
            finish()
        }

        imesetting()
        initWatcher()

        next.setOnClickListener {

            model.signUP(

                    loginId.text.toString(),password.text.toString()
                            ,genderValue.toString(),nickNameId.text.toString(),birth.text.toString()
                            ,App.prefs.myauthenticated_address.toString(),height.text.toString(),
                            applicationContext
            )

        }


        birth.setOnClickListener {

            DatePickerDialog(
                this,
                dateSetListener,
                // set DatePickerDialog to point to today's date when it loads up
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()

        }



        genderPicker.setOnCheckedChangeListener { group, checkedId ->

            Log.e("SignupActivit2", windowHeight.toFloat().dp.toString())
            Log.e("SignupActivit2", 100f.dp.toString())
            belowAnimator(
                R.id.genderPicker,
                windowHeight / (resources.displayMetrics.density - 0.5f) -(55f*3).dp)

            if(manRadio.isChecked){
                genderValue = 0
            }else{
                genderValue =1
            }

            nickNameInputLayout.visibility = View.VISIBLE
            introTv.setText("닉네임을 선택해 주세요.")
            nickNameId.requestFocus()
        }

        birth.setOnClickListener {

            DatePickerDialog(
                this,
                dateSetListener,
                // set DatePickerDialog to point to today's date when it loads up
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()

        }


    }

    fun initWatcher(){

        birth.setOnFocusChangeListener(this)

        //loginTextWatcher
        loginTextWatcher = object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                     val check = checkLoginIdField(s.toString())

                    if(check){
                        loginInputLayout.helperText ="체크 아이콘 또는 완료 버튼을 눌러주세요"
                        idcheckValue = true
                    }else {
                        loginInputLayout.helperText = "아이디는 문자, 숫자 3자리이상 15자이하여야 합니다."
                        idcheckValue = false
                    }
            }
        }

        passwordTextWatcher = object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val check = checkPassword(s.toString())

                if(check){
                    passwordInputLayout.helperText ="사용가능합니다."
                    passwordInputLayout.isEndIconVisible = true
                    passwordCheckValue = true

                }else {
                    passwordInputLayout.helperText = "6자 이상 숫자와 문자를 조합해야합니다."
                    passwordInputLayout.isEndIconVisible = false
                    passwordCheckValue = false
                }

                changeButton()

            }
        }

        passwordCheckTextWatcher = object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(password.text.toString().isEmpty()){
                    return
                }

                if(password.text.toString().equals(s.toString())){
                    passwordDuplicateCheckValue= true
                    passwordCheckInputLayout.helperText ="비밀번호가 일치합니다."
                }else{
                    passwordDuplicateCheckValue = false
                    passwordCheckInputLayout.helperText ="비밀번호가 일치하지 않습니다."
                }

                changeButton()
            }
        }

        passwordCheck.addTextChangedListener(passwordCheckTextWatcher)
        password.addTextChangedListener(passwordTextWatcher)
        loginId.addTextChangedListener(loginTextWatcher)

        loginInputLayout.setEndIconOnClickListener {
            login()
        }


        nickNameWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                val check = checkNick(s.toString())

                if(check){
                    nickNameInputLayout.helperText ="체크 아이콘 또는 완료 버튼을 눌러주세요"
                    nickNameval = true
                    changeButton()
                }else{
                    nickNameInputLayout.helperText = "닉네임은 8자를 넘을 수 없습니다."
                    nickNameval = false
                    changeButton()
                }


            }
        }

        nickNameInputLayout.setEndIconOnClickListener {
            checkNickName()
        }


        heightWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                heightInput = true
                changeButton()

            }
        }

        nickNameId.addTextChangedListener(nickNameWatcher)
        height.addTextChangedListener(heightWatcher)




    }

    fun imesetting(){
        loginId.imeOptions = EditorInfo.IME_ACTION_DONE
        passwordCheck.imeOptions = EditorInfo.IME_ACTION_DONE
        password.imeOptions = EditorInfo.IME_ACTION_DONE

        nickNameId.imeOptions = EditorInfo.IME_ACTION_DONE
        birth.imeOptions = EditorInfo.IME_ACTION_DONE
        height.imeOptions = EditorInfo.IME_ACTION_DONE


        loginId.setOnEditorActionListener(this)
        passwordCheck.setOnEditorActionListener(this)
        password.setOnEditorActionListener(this)
        nickNameId.setOnEditorActionListener(this)
        birth.setOnEditorActionListener(this)
        height.setOnEditorActionListener(this)


    }


    fun checkLoginIdField(input : String) : Boolean{

        val regex = Regex("[a-z0-9_-]{3,15}")

        return input.matches(regex)

    }

    fun checkPassword(input: String) : Boolean{

        val regex = Regex("^.*(?=^.{6,15}$)(?=.*\\d)(?=.*[a-z]).*\$")

        return input.matches(regex)

    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
            when(v!!.id){
                R.id.loginId->{

                    changeButton()
                    login()

                    return true
                }

                R.id.password ->{

                    if(passwordCheckValue){
                        belowAnimator(R.id.passwordInputLayout, windowHeight / (resources.displayMetrics.density - 0.5f) -(55f*1).dp)
                        passwordInputLayout.helperText=""
                        passwordCheck.requestFocus()
                        passwordInputLayout.isEndIconVisible = true

                        passwordCheckInputLayout.alpha = 1f
                        passwordCheckInputLayout.isEndIconVisible = false
                        passwordCheckInputLayout.visibility = View.VISIBLE
                        changeButton()
                        introTv.setText("비밀번호를 다시 입력해주세요.")
                        return true
                    }

                }


                R.id.passwordCheck ->{

                    if(passwordDuplicateCheckValue){
                        passwordInputLayout.alpha = 1f
                        passwordCheckInputLayout.isEndIconVisible = true
                        belowAnimator(R.id.passwordCheckInputLayout, windowHeight / (resources.displayMetrics.density - 0.5f) -(55f*2).dp)
                        passwordCheckInputLayout.helperText=""
                        changeButton()
                        introTv.setText("성별을 선택해 주세요.")
                        genderPicker.visibility=View.VISIBLE

                        return true
                    }

                }


                R.id.nickNameId -> {
                    checkNickName()
                }

                R.id.height -> {
                    belowAnimator(R.id.heightInputLayout,windowHeight / (resources.displayMetrics.density - 0.5f) -(55f*6).dp)
                    heightInputLayout.setEndIconDrawable(R.drawable.ic_baseline_check_24)
                    introTv.setText("다음을 눌러주세요.")

                }



            }

        return false
    }


    fun changeButton() {
        next.isEnabled = idcheckValue && passwordDuplicateCheckValue && passwordCheckValue
    }

    fun belowAnimator(layout : Int, height : Float){

        val layout = findViewById<View>(layout)
        val ani = ObjectAnimator.ofFloat(layout, "translationY",height)
        ani.duration = 200
        ani.start()
    }


    fun ObjectAnimator.addapter(){
        this.addListener(object : AnimatorListenerAdapter(){

            override fun onAnimationEnd(animation: Animator?, isReverse: Boolean) {
                super.onAnimationEnd(animation, isReverse)
            }

            override fun onAnimationStart(animation: Animator?, isReverse: Boolean) {
                super.onAnimationStart(animation, isReverse)

            }
        })
    }


    fun getWindowHeightSize() : Int{
        val display = windowManager.defaultDisplay // in case of Activity
        val size = Point()
        display.getRealSize(size) // or getSize(size)
        val width = size.x
        val height = size.y

        return height
    }

    fun login(){

        model.CheckDuplicateId(loginId.text.toString(), object : CodeCallBack {

            override fun onSuccess(code: String, value: String) {

                try {

                    if (code.equals("200")) {
                        //성공했을때
                        loginInputLayout.helperText ="사용가능한 아이디 입니다."
                        loginInputLayout.isEndIconVisible = true
                        loginInputLayout.setEndIconDrawable(R.drawable.ic_baseline_check_24)
                        belowAnimator(R.id.loginInputLayout, windowHeight / (resources.displayMetrics.density - 0.5f))

                        passwordInputLayout.alpha = 1f
                        passwordInputLayout.visibility = View.VISIBLE
                        passwordInputLayout.isEndIconVisible = false
                        loginInputLayout.helperText=""

                        introTv.setText("비밀번호를 입력해주세요.")

                        idcheckValue = true
                        password.requestFocus()

                    } else if (code.equals("400")) {
                        loginInputLayout.error ="중복 된 아이디입니다."
                        loginInputLayout.boxBackgroundColor = resources.getColor(R.color.white)
                        idcheckValue = false
                    } else {
                        loginInputLayout.error ="일시적인 서버 에러입니다."
                    }
                } catch (e: Exception) {

                }

            }

        })

    }

    fun checkNickName() {

        model.CheckDuplicateName(nickNameId.text.toString(), object : CodeCallBack {
            override fun onSuccess(code: String, value: String) {
                try {
                    if (code.equals("200")) {
                        nickNameval = true
                        belowAnimator(R.id.nickNameInputLayout,windowHeight / (resources.displayMetrics.density - 0.5f) -(55f*4).dp)
                        birthInputLayout.visibility = View.VISIBLE
                        nickNameInputLayout.helperText=""
                        nickNameInputLayout.setEndIconDrawable(R.drawable.ic_baseline_check_24)
                        birth.requestFocus()
                        introTv.setText(introBirth)

                    } else if (code.equals("400")) {
                        nickNameval = false
                        nickNameInputLayout.helperText = "중복된 닉네임 입니다."
                    } else {
                        nickNameInputLayout.helperText = "일시적인 서버 오류입니다."
                        nickNameval = false

                    }
                } catch (e: Exception) {

                }
                changeButton()
            }
        })

    }


    fun checkNick(cw: String): Boolean {
        val regExpId = Regex("^[0-9a-z가-힣]{0,8}")


        return regExpId.matches(cw)
    }

    fun pickerListener() {

        dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(
                view: DatePicker, year: Int, monthOfYear: Int,
                dayOfMonth: Int
            ) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
                belowAnimator(R.id.birthInputLayout,windowHeight / (resources.displayMetrics.density - 0.5f) -(55f*5).dp)
                birthInputLayout.setEndIconDrawable(R.drawable.ic_baseline_check_24)
                heightInputLayout.visibility = View.VISIBLE
                introTv.setText(introHeight)
                dateInput = true
                height.requestFocus()

                changeButton()
            }
        }

    }

    private fun updateDateInView() {
        val myFormat = "yyyy-MM-dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.KOREA)

        val currentTime = Calendar.getInstance()


        cal.get(Calendar.YEAR)


        //가입하는 사람의 생년월일이 100살 이하 14세 이하는 서버에서 로직 점검

        if (currentTime.get(Calendar.YEAR) - 100 < cal.get(Calendar.YEAR)) {
            birth.setText(sdf.format(cal.getTime()))
            dateInput = true

            // hideKeyboard()

        } else {
            Toast.makeText(applicationContext, "생년월일을 다시 선택 해주세요", Toast.LENGTH_SHORT).show()
        }


    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {

        when (v!!.id) {


            R.id.birth -> {

                if (hasFocus) {

                    DatePickerDialog(
                        this,
                        dateSetListener,
                        // set DatePickerDialog to point to today's date when it loads up
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)
                    ).show()

                }
            }


        }
    }

}

val Float.dp: Float
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toFloat()

val Int.dp: Float
    get() = (this / Resources.getSystem().displayMetrics.density - 0.5f).toFloat()
