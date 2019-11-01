package com.example.tintint_jw.View

import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tintint_jw.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //여기 자동로그인이랑 비교 구문 구현.

        signUp.setOnClickListener(){
            val intent = Intent(applicationContext,SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}
