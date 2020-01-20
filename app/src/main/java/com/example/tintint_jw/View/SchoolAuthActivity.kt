package com.example.tintint_jw.View

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tintint_jw.Model.IdCallBack
import com.example.tintint_jw.Model.ModelSchoolAuth
import com.example.tintint_jw.R
import com.example.tintint_jw.SharedPreference.App
import com.example.tintint_jw.View.SignUp.SignUpActivity2
import com.example.tintint_jw.View.SignUp.SignupActivity1
import kotlinx.android.synthetic.main.activity_school_authentication.*
import kotlinx.coroutines.*
import java.util.*

class SchoolAuthActivity : AppCompatActivity() {

    var isAuthorized:Boolean = false
    var TimeInMillis:Long = 1800000
    val model : ModelSchoolAuth = ModelSchoolAuth(this)
    val scope: CoroutineScope ?= CoroutineScope(Dispatchers.Main)

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_school_authentication)
        val view:ViewGroup = findViewById(R.id.rootView)

        schoolAuthText.visibility = View.INVISIBLE
        schoolAuthComplete.visibility = View.INVISIBLE

        /*view.setTag(view.visibility)
        view.viewTreeObserver.addOnGlobalLayoutListener(object :ViewTreeObserver.OnGlobalLayoutListener{
            override fun onGlobalLayout() {
                schoolAuthText.visibility = View.INVISIBLE
                schoolAuthComplete.visibility = View.INVISIBLE
                view.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }

        })*/
        back.setOnClickListener{
            finish()
        }

        next.setOnClickListener(){

            model.schoolAuthComplete(schEmail.text.toString(), object :IdCallBack{
                override fun onSuccess(value: String) {
                    super.onSuccess(value)
                    try{
                    Log.d("value complete",value)
                    if(value.equals("인증이 완료된 이메일입니다.")) {
                        view.invalidate()
                        schoolAuthText.visibility = View.INVISIBLE
                        schoolAuthComplete.visibility = View.VISIBLE
                    }
                    }catch (e:Exception){
                        Toast.makeText(applicationContext, "인증이 필요한 이메일입니다.", Toast.LENGTH_LONG).show()
                        e.printStackTrace()
                    }
                }
            })

            if(intent.hasExtra("kakao")&&checkEmptyField(schEmail.toString())){

                scope!!.cancel()
                val intent= Intent(this, SignUpActivity2::class.java)
                startActivity(intent)

            }

            else{
                if(checkEmptyField(schEmail.toString())&&isAuthorized){
                    scope!!.cancel()
                    val intent= Intent(this, SignupActivity1::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(applicationContext, "인증되지 않은 이메일입니다.", Toast.LENGTH_LONG).show()
                }
            }
        }

        // 이메일 인증 절차
        emailSendBtn.setOnClickListener (object :View.OnClickListener{
            override fun onClick(v: View?) {
                if(checkEmptyField(schEmail.text.toString())){
                    model.schoolAuth(App.prefs.name,schEmail.text.toString(), object : IdCallBack{
                        override fun onSuccess(value: String) {
                            super.onSuccess(value)
                            Log.d("value ", value)
                            if(value.equals("인증메일을 전송했습니다.")){
                                runBlocking {
                                    scope!!.launch {
                                        schoolAuthText.visibility = View.VISIBLE
                                        schoolAuthComplete.visibility = View.INVISIBLE
                                        startCountDown()

                                    }
                                }
                                // 타이머 설정하기
                            }else{
                                Toast.makeText(applicationContext, "일시적인 서버 오류입니다.", Toast.LENGTH_LONG).show()
                            }
                        }
                    })
                }
                else{
                    Toast.makeText(applicationContext, "이메일을 입력해주세요.", Toast.LENGTH_LONG).show()
                }
            }

        })
    }

    // 제한 시간 재기 시작
    private fun startCountDown() {
        var coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
        val cntDownTimer:CountDownTimer = object : CountDownTimer(TimeInMillis, 1000){
            override fun onFinish() {
                Toast.makeText(applicationContext, "요청한 시간이 초과되었습니다.", Toast.LENGTH_LONG).show()
            }

            override fun onTick(p0: Long) {
                TimeInMillis = p0
                model.schoolAuthComplete(schEmail.text.toString(), object :IdCallBack{
                    override fun onSuccess(value: String) {
                        super.onSuccess(value)
                        Log.d("value complete",value)
                        if(value.equals("인증이 완료된 이메일입니다.")){
                            isAuthorized = true
                            runBlocking {
                                coroutineScope.launch {
                                    launch(Dispatchers.Main){
                                        schoolAuthText.visibility = View.INVISIBLE
                                        schoolAuthComplete.visibility = View.VISIBLE
                                    }

                                }
                                /*scope!!.launch {
                                    schoolAuthText.visibility = View.INVISIBLE
                                    schoolAuthComplete.visibility = View.VISIBLE
                                }*/
                            }
                        }else{
                            isAuthorized = false
                        }
                    }
                })
                updateCountDown()
                //time.text = p0.toString()
            }

        }
            cntDownTimer.start()
    }

    // UI 업데이트
    private fun updateCountDown() {
        var min:Int = (TimeInMillis/1000).toInt()/60
        var seconds:Int = (TimeInMillis/1000).toInt() % 60

        var timeLeftFormat : String = String.format(Locale.getDefault(),"%02d:%02d", min, seconds)
        time.text = timeLeftFormat
    }

    fun checkEmptyField(
        schEmail:String

    ): Boolean {
        if (schEmail.isEmpty()) {
            Toast.makeText(applicationContext, "이메일을 올바르게 입력해주세요.", Toast.LENGTH_LONG).show();
            return false
        }
        return true

    }
}