package com.example.tintint_jw.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tintint_jw.R
import com.example.tintint_jw.View.Policy.CheckPolicy01
import com.example.tintint_jw.View.Policy.CheckPolicy02
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class SettingsActivity: AppCompatActivity() {
    val scope: CoroutineScope?= CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // 뒤로가기
        back.setOnClickListener {
            finish()
        }

        // 이용약관
        policy1.setOnClickListener{
            val intent:Intent = Intent(applicationContext, CheckPolicy01::class.java)
            startActivity(intent)
        }

        policy2.setOnClickListener{
            val intent:Intent = Intent(applicationContext, CheckPolicy02::class.java)
            startActivity(intent)
        }
    }
}