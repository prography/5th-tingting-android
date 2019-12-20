package com.example.tintint_jw.FindIdAndPw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tintint_jw.R
import kotlinx.android.synthetic.main.activity_find_id.*

class FindPw : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_pw)

        back.setOnClickListener(){
            finish()
        }

    }
}
