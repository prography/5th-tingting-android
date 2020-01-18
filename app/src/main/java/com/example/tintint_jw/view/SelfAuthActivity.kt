package com.example.tintint_jw.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tintint_jw.R
import com.example.tintint_jw.view.SignUp.SignupActivity1
import kotlinx.android.synthetic.main.activity_self_authentication.*

class SelfAuthActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_self_authentication)

        next.setOnClickListener{
            val intent = Intent(applicationContext, SignupActivity1::class.java)
            startActivity(intent)
        }

        back.setOnClickListener {
            finish()
        }
    }
}