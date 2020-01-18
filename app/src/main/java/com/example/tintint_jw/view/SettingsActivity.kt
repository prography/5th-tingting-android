package com.example.tintint_jw.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tintint_jw.R
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // 뒤로가기
        back.setOnClickListener {
            finish()
        }
    }
}