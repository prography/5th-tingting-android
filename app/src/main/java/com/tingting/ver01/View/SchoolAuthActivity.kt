package com.tingting.ver01.View

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tingting.ver01.Model.Auth.ModelSchoolAuth
import com.tingting.ver01.Model.IdCallBack
import com.tingting.ver01.R
import com.tingting.ver01.SharedPreference.App
import com.tingting.ver01.View.SignUp.SignupActivity1
import com.tingting.ver01.View.SignUp.SignupActivity2
import kotlinx.android.synthetic.main.activity_school_authentication.*
import kotlinx.coroutines.*
import java.util.*

class SchoolAuthActivity : AppCompatActivity() {

    var isAuthorized:Boolean = false
    var TimeInMillis:Long = 1800000
    val model : ModelSchoolAuth = ModelSchoolAuth(this)
    val scope: CoroutineScope ?= CoroutineScope(Dispatchers.Main)
    var coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    lateinit var cntDownTimer : CountDownTimer
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

        try{
        next.setOnClickListener(){

                if(checkEmptyField(schEmail.toString())&&isAuthorized){
                    cntDownTimer.cancel()
                    scope!!.cancel()
                    coroutineScope!!.cancel()

                    if(App.prefs.myLoginType.equals("local")){
                    val intent= Intent(this, SignupActivity1::class.java)
                    startActivity(intent)
                    }else if (App.prefs.myLoginType.equals("kakao")){
                        val intent= Intent(this, SignupActivity2::class.java)
                        startActivity(intent)
                    }
                }else{
                    Toast.makeText(applicationContext, "인증되지 않은 이메일입니다.", Toast.LENGTH_LONG).show()
                }

        }
        }catch (e : Exception){
            e.printStackTrace()
        }
        // 이메일 인증 절차
        emailSendBtn.setOnClickListener (object :View.OnClickListener{
            override fun onClick(v: View?) {
                if(checkEmptyField(schEmail.text.toString())){
                    model.schoolAuth(App.prefs.name,schEmail.text.toString(), object : IdCallBack{
                        override fun onSuccess(value: String) {
                            super.onSuccess(value)
                            Log.d("value ", value)
                            when(value){
                                "400" -> Toast.makeText(applicationContext, "이미 가입된 메일입니다.", Toast.LENGTH_LONG).show()
                                "401" -> Toast.makeText(applicationContext, "가입이 불가능한 이메일입니다.", Toast.LENGTH_LONG).show()
                                "201" ->  runBlocking {
                                    scope!!.launch {
                                        schoolAuthText.visibility = View.VISIBLE
                                        schoolAuthComplete.visibility = View.INVISIBLE
                                    }
                                    startCountDown()
                                }
                            }
                        }
                    })
                } else{
                    Toast.makeText(applicationContext, "이메일을 입력해주세요.", Toast.LENGTH_LONG).show()
                }
            }

        })
    }

    // 제한 시간 재기 시작
    private fun startCountDown() {

        cntDownTimer = object : CountDownTimer(TimeInMillis, 1000){
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
                            App.prefs.myauthenticated_address = schEmail.text.toString()

                            runBlocking {
                                coroutineScope.launch {
                                    launch(Dispatchers.Main){
                                        schoolAuthText.visibility = View.INVISIBLE
                                        schoolAuthComplete.visibility = View.VISIBLE
                                    }
                                }
                            }

                        }else{
                            isAuthorized = false
                        }
                    }
                })
                updateCountDown()
            }

        }

        if(isAuthorized){
            cntDownTimer.cancel()
        }else{
            cntDownTimer.start()
        }
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