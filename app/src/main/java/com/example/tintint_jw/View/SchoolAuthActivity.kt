package com.example.tintint_jw.View

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tintint_jw.R
import kotlinx.android.synthetic.main.activity_school_authentication.*

class SchoolAuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_school_authentication)

        next.setOnClickListener(){
            val intent= Intent(this, PictureRegisterActivity::class.java)
            startActivity(intent)
        }

    }
}