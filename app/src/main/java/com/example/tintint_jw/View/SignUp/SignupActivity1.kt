package com.example.tintint_jw.View.SignUp

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tintint_jw.Model.IdCallBack
import com.example.tintint_jw.Model.ModelSignUp
import com.example.tintint_jw.R
import com.example.tintint_jw.SharedPreference.App
import kotlinx.android.synthetic.main.activity_sign_up1.*


class SignupActivity1 : AppCompatActivity() {

    var model:ModelSignUp = ModelSignUp(this)

    var check = false
    var checkidvalidate = false
    var check2 = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up1)


        passwordCheck.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(password.text.toString().equals(passwordCheck.text.toString())){
                    checkPwCheckMessage.layoutParams.height =
                        (20 * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
                    checkPwCheckMessage.setText("비밀번호가 일치합니다. ")
                    check2 = true
                }
                else{
                    checkPwCheckMessage.layoutParams.height =
                        (20 * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
                    checkPwCheckMessage.setText("비밀번호가 다릅니다. ")
                    check2=false
                }
            }
        })

        checkId.setOnClickListener(){
            if(checkidvalidate){
                model.CheckDuplicateId(loginId.text.toString(), object : IdCallBack {
                    override fun onSuccess(value: String) {

                            if(value.equals("사용 가능한 아이디입니다.")){
                                checkidmessage.layoutParams.height =
                                    (20 * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
                                checkidmessage.setText("사용가능한 아이디 입니다. ")
                            }else{
                                checkidmessage.layoutParams.height =
                                    (20 * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
                                checkidmessage.setText("중복 된 아이디 입니다. ")
                            }

                    }
                });
            }else{
                checkidmessage.layoutParams.height =
                    (20 * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
                checkidmessage.setText("유효한 이메일 형식인지 확인해 주세요")
            }
        }



        loginId.addTextChangedListener(object :TextWatcher{

            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkEmail(loginId, checkidmessage)
            }
        })

        password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkPw(password,checkpwmessage)
            }
        })


        //다음 화면으로 넘어가는 버튼
        next.setOnClickListener(){
            App.prefs.mylocal_id = loginId.text.toString()
            App.prefs.mypassword = password.text.toString()
            if(checkEmptyField(loginId.toString(),password.text.toString()) && check2 && checkidvalidate){
            var intent: Intent = Intent(this, SignUpActivity2::class.java)

            startActivity(intent)
            }
        }
    }





    fun checkPw(pw: EditText, cw:TextView): Boolean {
        if (pw.text.toString().length < 6) {

                cw.layoutParams.height =
                    (20 * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
                cw.setText("비밀 번호는 6자리 이상이어야 합니다.")

            return false;
        } else {
            checkpwmessage.setText("사용 가능합니다.")
        }
        val reg = Regex("(?=.*\\d{1,50})(?=.*[~`!@#\$%\\^&*()-+=]{1,50})(?=.*[a-zA-Z]{2,50})\$\n")
        return true;
    }


    fun checkEmail(email: EditText, idmessage:TextView): Boolean {
        val reg = Regex("^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}")

        if (!email.text.toString().matches(reg)) {
                idmessage.layoutParams.height =
                    (20 * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
                idmessage.setText("유효한 이메일 형식인지 확인해 주세요")

            return false;

        } else {
            checkidvalidate= true
            idmessage.setText("유효한 이메일 입니다.")
        }
        return true;
    }


    fun checkEmptyField(
        id: String,
        password: String

    ): Boolean {
        if (id.isEmpty()) {
            Toast.makeText(applicationContext, "아이디 필드를 확인해주세요", Toast.LENGTH_LONG).show();
            return false;
        }
        if (password.isEmpty()) {
            Toast.makeText(applicationContext, "패스워드 필드를 확인해주세요", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;

    }
}
