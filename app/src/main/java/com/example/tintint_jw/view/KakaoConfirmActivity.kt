package com.example.tintint_jw.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.tintint_jw.view.SignUp.SignUpConfirmKActivity
import kotlinx.android.synthetic.main.activity_kakao_confirm.*


class KakaoConfirmActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.tintint_jw.R.layout.activity_kakao_confirm)

        Glide.with(applicationContext)
            .load(com.example.tintint_jw.R.drawable.tingting)
            .apply(RequestOptions.circleCropTransform())
            .into(appIcon)

        confirm.setOnClickListener{
            val intent = Intent(applicationContext, SignUpConfirmKActivity::class.java)
            startActivity(intent)
        }
    }
}