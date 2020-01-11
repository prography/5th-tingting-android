package com.example.tintint_jw.View

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.tintint_jw.R
import com.example.tintint_jw.View.SignUp.SignUpActivity2
import kotlinx.android.synthetic.main.activity_kakao_confirm.*

class KakaoConfirmActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kakao_confirm)

        Glide.with(applicationContext)
            .load(R.drawable.tingting)
            .apply(RequestOptions.circleCropTransform())
            .into(appIcon)

        confirm.setOnClickListener{
            val intent = Intent(applicationContext, SignUpActivity2::class.java)
            startActivity(intent)
        }

    }
}