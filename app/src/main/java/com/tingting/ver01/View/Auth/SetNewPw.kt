package com.tingting.ver01.View.Auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tingting.ver01.R
import kotlinx.android.synthetic.main.activity_set_new_pw.*

class SetNewPw: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_new_pw)

        back.setOnClickListener {
            finish()
        }
    }
}