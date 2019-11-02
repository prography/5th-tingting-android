package com.example.tintint_jw.View

import android.app.Application
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tintint_jw.R
import com.example.tintint_jw.SharedPreference.SharedPreference
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    companion object {
        lateinit var prefs : SharedPreference
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        prefs = SharedPreference(applicationContext)

        if((prefs.myId=="서버로 부터 불러온 아이디") && (prefs.myPw=="서버로 부터 불러온 PW")){
            val intent = Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)
        }

        signIn.setOnClickListener(){
        //    if(loginId.text.equals("서버로 부터 불러 온 id") && loginPw.text.equals("서버로 부터 불러 온 pw")){
                prefs.myId = loginId.text.toString()
                prefs.myPw = loginPw.text.toString()
       //     }
        }

        signUp.setOnClickListener(){
            val intent = Intent(applicationContext,SignUpActivity::class.java)
            startActivity(intent)
        }


    }
}
