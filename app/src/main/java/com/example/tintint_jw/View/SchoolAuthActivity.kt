package com.example.tintint_jw.View

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tintint_jw.Model.IdCallBack
import com.example.tintint_jw.Model.ModelSchoolAuth
import com.example.tintint_jw.R
import com.example.tintint_jw.SharedPreference.App
import com.example.tintint_jw.View.SignUp.SignUpActivity2
import com.example.tintint_jw.View.SignUp.SignupActivity1
import kotlinx.android.synthetic.main.activity_school_authentication.*

class SchoolAuthActivity : AppCompatActivity() {
    val model : ModelSchoolAuth = ModelSchoolAuth(this)


    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_school_authentication)
        val view:ViewGroup = findViewById(R.id.rootView)

        view.setTag(view.visibility)
        view.viewTreeObserver.addOnGlobalLayoutListener(object :ViewTreeObserver.OnGlobalLayoutListener{
            override fun onGlobalLayout() {
                schoolAuthText.visibility = View.INVISIBLE
                schoolAuthComplete.visibility = View.INVISIBLE
                view.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }

        })
        back.setOnClickListener{
            finish()
        }


        next.setOnClickListener(){

            model.schoolAuthComplete(schEmail.text.toString(), object :IdCallBack{
                override fun onSuccess(value: String) {
                    super.onSuccess(value)
                    Log.d("value complete",value)
                    if(value.equals("인증이 완료된 이메일입니다.")){
                        view.invalidate()
                        schoolAuthText.visibility = View.INVISIBLE
                        schoolAuthComplete.visibility = View.VISIBLE
                    }
                }
            })

            if(intent.hasExtra("kakao")&&checkEmptyField(schEmail.toString())){

                val intent= Intent(this, SignUpActivity2::class.java)
                startActivity(intent)

            }

            else{
                if(checkEmptyField(schEmail.toString())){
                    val intent= Intent(this, SignupActivity1::class.java)
                    startActivity(intent)
                }
            }
        }

        // 이메일 인증 절차
        emailSendBtn.setOnClickListener (object :View.OnClickListener{
            override fun onClick(v: View?) {
                model.schoolAuth(App.prefs.name,schEmail.text.toString(), object : IdCallBack{
                    override fun onSuccess(value: String) {
                        super.onSuccess(value)
                        Log.d("value ", value)
                        if(value.equals("인증메일을 전송했습니다.")){
                            view.invalidate()
                            schoolAuthText.visibility = View.VISIBLE
                            schoolAuthComplete.visibility = View.INVISIBLE
                            // 타이머 설정하기
                            time.setText("30:00")
                        }
                    }
                })
            }

        })
    }

    fun checkEmptyField(
        schEmail:String

    ): Boolean {
        if (schEmail.isEmpty()) {
            Toast.makeText(applicationContext, "이메일을 올바르게 입력해주세요.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;

    }
}