package com.tingting.ver01.view.SignUp

import android.animation.ObjectAnimator
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tingting.ver01.R
import com.tingting.ver01.model.CodeCallBack
import com.tingting.ver01.model.ModelSignUp
import com.tingting.ver01.model.ProfileCallBack
import com.tingting.ver01.sharedPreference.App
import kotlinx.android.synthetic.main.activity_sign_up1.*
import kotlinx.android.synthetic.main.activity_sign_up2_v2.*
import kotlinx.android.synthetic.main.activity_sign_up2_v2.next
import java.text.SimpleDateFormat
import java.util.*


class SignupActivity2 : AppCompatActivity() , TextView.OnEditorActionListener,  View.OnFocusChangeListener {

    var model: ModelSignUp = ModelSignUp(this)
    var nickNameval = false
    var heightInput = false
    var dateInput = false
    var windowHeight = 0
    var genderValue = 0
    var cal = Calendar.getInstance()

    val introBirth = "생일을 선택해 주세요."
    val introHeight = "키를 선택해 주세요."

    lateinit var dateSetListener: DatePickerDialog.OnDateSetListener

    lateinit var nickNameWatcher: TextWatcher
    lateinit var heightWatcher: TextWatcher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up2_v2)

        windowHeight = getWindowHeightSize()

        imesetting()
        initWatcher()
        pickerListener()

        toolbar2.setNavigationOnClickListener {
            finish()
        }

        genderPicker.setOnCheckedChangeListener { group, checkedId ->

            Log.e("SignupActivit2", windowHeight.toFloat().dp.toString())
            Log.e("SignupActivit2", 100f.dp.toString())
            belowAnimator(
                R.id.genderPicker,
                windowHeight / (resources.displayMetrics.density - 0.5f)
            )

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

        next.setOnClickListener {

            model.KakaoSignUp(
                nickNameId.text.toString(),birth.text.toString() ,height.text.toString()
                ,App.prefs.myauthenticated_address.toString(),genderValue.toString() , object :
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

    }


    fun checkNick(cw: String): Boolean {
        val regExpId = Regex("^[0-9a-z가-힣]{0,8}")


        return regExpId.matches(cw)
    }

    fun imesetting() {

        nickNameId.imeOptions = EditorInfo.IME_ACTION_DONE
        birth.imeOptions = EditorInfo.IME_ACTION_DONE
        height.imeOptions = EditorInfo.IME_ACTION_DONE

        nickNameId.setOnEditorActionListener(this)
        birth.setOnEditorActionListener(this)
        height.setOnEditorActionListener(this)
    }


    fun initWatcher() {

        birth.setOnFocusChangeListener(this)

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

    private fun changeButton() {
        next.isEnabled = nickNameval && dateInput && heightInput
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {

        when (v!!.id) {

            R.id.nickNameId -> {
                checkNickName()
            }

            R.id.height -> {
                belowAnimator(R.id.heightInputLayout,windowHeight / (resources.displayMetrics.density - 0.5f) -165f.dp)
                heightInputLayout.setEndIconDrawable(R.drawable.ic_baseline_check_24)
                introTv.setText("다음을 눌러주세요.")

            }
        }


        return false
    }

    fun belowAnimator(layout: Int, height: Float) {

        val layout = findViewById<View>(layout)
        val ani = ObjectAnimator.ofFloat(layout, "translationY", height)
        ani.duration = 200
        ani.start()
    }

    fun getWindowHeightSize(): Int {
        val display = windowManager.defaultDisplay // in case of Activity
        val size = Point()
        display.getRealSize(size) // or getSize(size)
        val width = size.x
        val height = size.y

        return height
    }

    fun checkNickName() {

        model.CheckDuplicateName(nickNameId.text.toString(), object : CodeCallBack {
                override fun onSuccess(code: String, value: String) {
                    try {
                        if (code.equals("200")) {
                            nickNameval = true
                            belowAnimator(R.id.nickNameInputLayout,windowHeight / (resources.displayMetrics.density - 0.5f) -55f.dp)
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
                belowAnimator(R.id.birthInputLayout,windowHeight / (resources.displayMetrics.density - 0.5f) -110f.dp )
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


    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(birth.windowToken, 0)
    }


}