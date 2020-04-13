package com.tingting.ver01.view.SignUp

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tingting.ver01.R
import com.tingting.ver01.model.CodeCallBack
import com.tingting.ver01.model.ModelSignUp
import com.tingting.ver01.sharedPreference.App
import kotlinx.android.synthetic.main.activity_school_authentication.*
import kotlinx.android.synthetic.main.activity_sign_up1.*
import kotlinx.android.synthetic.main.activity_sign_up1.back
import kotlinx.android.synthetic.main.activity_sign_up1.next
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SignupActivity1 : AppCompatActivity() {

    var model: ModelSignUp = ModelSignUp(this)

    var check = false
    var checkidvalidate = false
    var check2 = false
    var scope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up1)

        changeButton()
        var input = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        input.showSoftInput(loginId,0)
        loginId.requestFocus()

        // 뒤로가기
        back.setOnClickListener {
            finish()
        }

        passwordCheck.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                val text: String = s.toString()

                if (text.equals(passwordCheck.text.toString())) {
                    checkPwCheckMessage.layoutParams.height =
                        (15 * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
                    checkPwCheckMessage.text = "비밀번호가 일치합니다. "
                    checkPwCheckMessage.setTextColor(getColor(R.color.green))
                    check2 = true
                } else {
                    checkPwCheckMessage.layoutParams.height =
                        (15 * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
                    checkPwCheckMessage.text = "비밀번호가 다릅니다. "
                    checkPwCheckMessage.setTextColor(getColor(android.R.color.holo_red_dark))
                    check2 = false
                }
                if (password.text.toString().equals(passwordCheck.text.toString())) {
                    checkPwCheckMessage.layoutParams.height =
                        (15 * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
                    checkPwCheckMessage.text = "비밀번호가 일치합니다. "
                    checkPwCheckMessage.setTextColor(getColor(R.color.green))
                    check2 = true
                } else {
                    checkPwCheckMessage.layoutParams.height =
                        (15 * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
                    checkPwCheckMessage.text = "비밀번호가 다릅니다. "
                    checkPwCheckMessage.setTextColor(getColor(android.R.color.holo_red_dark))
                    check2 = false
                }

                passwordCheck.setOnEditorActionListener(object: TextView.OnEditorActionListener{
                    override fun onEditorAction(
                        v: TextView?,
                        actionId: Int,
                        event: KeyEvent?
                    ): Boolean {
                        if(actionId==EditorInfo.IME_ACTION_NEXT){
                            next.requestFocus()
                            input.hideSoftInputFromWindow(passwordCheck.windowToken,0)
                        }
                        return false
                    }
                })



                changeButton()

            }

        })

        checkId.setOnClickListener {
            password.requestFocus()

            if (loginId.text.toString().trim().length != 0) {
                model.CheckDuplicateId(loginId.text.toString(), object : CodeCallBack {

                    override fun onSuccess(code: String, value: String) {

                        try {
                            if (code.equals("200")) {
                                checkidmessage.layoutParams.height =
                                    (20 * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
                                checkidvalidate = true
                                checkidmessage.text = "사용가능한 아이디 입니다. "
                                checkidmessage.setTextColor(getColor(R.color.green))
                                input.hideSoftInputFromWindow(password.windowToken,0)


                            } else if (code.equals("400")) {
                                checkidmessage.layoutParams.height =
                                    (20 * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
                                checkidvalidate = false
                                checkidmessage.text = "중복된 아이디 입니다. "
                                checkidmessage.setTextColor(getColor(android.R.color.holo_red_dark))

                            } else {
                                checkidvalidate = false
                                Toast.makeText(
                                    applicationContext,
                                    "일시적인 서버 오류입니다",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        } catch (e: Exception) {

                        }
                        changeButton()

                    }

                })
            } else {
                Toast.makeText(applicationContext, "아이디 값을 입력해주세요", Toast.LENGTH_LONG).show()
            }

        }


        loginId.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkEmail(loginId, checkidmessage)
                checkidvalidate = false
            }
        })

        password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                password.setOnEditorActionListener(object: TextView.OnEditorActionListener{
                    override fun onEditorAction(
                        v: TextView?,
                        actionId: Int,
                        event: KeyEvent?
                    ): Boolean {
                        if(actionId==EditorInfo.IME_ACTION_NEXT){
                            input.hideSoftInputFromWindow(password.windowToken,0)


                        }
                        return false
                    }
                })
                checkPw(password, checkpwmessage)
                changeButton()
            }
        })


        //다음 화면으로 넘어가는 버튼
        next.setOnClickListener {


            App.prefs.mylocal_id = loginId.text.toString()
            App.prefs.mypassword = password.text.toString()
            if (checkEmptyField(
                    loginId.toString(),
                    password.text.toString()
                ) && check2 && checkidvalidate
            ) {

                var intent: Intent = Intent(this, SignupActivity2::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(applicationContext, "아이디 중복을 확인해주세요", Toast.LENGTH_LONG).show()
            }
        }
    }


    fun checkPw(pw: EditText, cw: TextView) {
        val reg = Regex("^.*(?=^.{8,15}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#\$%^&+=]).*\$")
                if (reg.matches(pw.text.toString())) {
                    cw.text = "사용 가능합니다."
                    cw.setTextColor(getColor(R.color.green))
                    check = true

                } else {

                    cw.layoutParams.height =
                        (20 * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
                    cw.text = "비밀번호는 8자리 이상,문자, 특수문자,영문을 포함해야합니다."
                    cw.setTextColor(getColor(android.R.color.holo_red_dark))
                    check = false
                }




    }

    fun checkEmail(email: EditText, idmessage: TextView) {
        var regExpId = Regex("^[0-9a-z]+")

        if (regExpId.matches(email.text.toString()) && email.text.length < 20) {
            idmessage.layoutParams.height =
                (15 * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
            idmessage.text = "아이디 중복 확인을 해주세요."
            idmessage.setTextColor(getColor(android.R.color.holo_red_dark))

        } else if (email.text.length > 20) {
            idmessage.layoutParams.height =
                (15 * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
            idmessage.text = "아이디는 20자 이하만 가능합니다."
            idmessage.setTextColor(getColor(android.R.color.holo_red_dark))
        } else {
            idmessage.layoutParams.height =
                (15 * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
            idmessage.text = "아이디는 영문 또는 숫자만 입력 가능합니다."
            idmessage.setTextColor(getColor(android.R.color.holo_red_dark))
        }
    }


    fun checkEmptyField(
        id: String,
        password: String

    ): Boolean {
        if (id.isEmpty()) {
            Toast.makeText(applicationContext, "아이디 필드를 확인해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        if (password.isEmpty()) {
            Toast.makeText(applicationContext, "패스워드 필드를 확인해주세요", Toast.LENGTH_LONG).show()
            return false
        }

        return true

    }

    fun changeButton() {
        next.isEnabled = check2 && checkidvalidate && check
    }


}

