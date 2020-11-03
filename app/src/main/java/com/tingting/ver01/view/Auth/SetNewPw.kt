package com.tingting.ver01.view.Auth

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tingting.ver01.R
import com.tingting.ver01.model.CodeCallBack
import com.tingting.ver01.model.ModelSignUp
import com.tingting.ver01.sharedPreference.App
import kotlinx.android.synthetic.main.activity_set_new_pw.*

class SetNewPw: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_new_pw)

        var model : ModelSignUp = ModelSignUp(this)
        var email:String? = intent.getStringExtra("email")
        Log.i("code", App.prefs.mycode.toString())

        next.setOnClickListener{
            model.resetPw(App.prefs.mycode.toString(), email, newPw.text.toString(), object : CodeCallBack{
                override fun onSuccess(code: String, value: String) {
                    try{
                        if(code.equals("200")){
                            Toast.makeText(applicationContext, "비밀번호를 재설정하였습니다.", Toast.LENGTH_LONG).show()
                        }else if(code.equals("401")){
                            Toast.makeText(applicationContext, "인증이 필요한 이메일입니다.", Toast.LENGTH_LONG).show()
                        }else{
                            Toast.makeText(applicationContext, "서버 오류", Toast.LENGTH_LONG).show()
                        }
                    }
                    catch (e:Exception){
                        e.printStackTrace()
                    }
                }
            })
        }
        back.setOnClickListener {
            finish()
        }
    }
}