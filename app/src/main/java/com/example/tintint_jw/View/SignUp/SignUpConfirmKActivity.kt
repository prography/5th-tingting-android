package com.example.tintint_jw.View.SignUp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tintint_jw.R
import com.example.tintint_jw.View.SchoolAuthActivity
import kotlinx.android.synthetic.main.signup_confirm_k_activity.*

class SignUpConfirmKActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_confirm_k_activity)

        next.setOnClickListener {
            val intent = Intent(applicationContext, SchoolAuthActivity::class.java)
            intent.putExtra("kakao","kakao")
            startActivity(intent)

        }
    }
}