package com.example.tintint_jw.View

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tintint_jw.R
import com.example.tintint_jw.View.SignUp.SignUpActivity2
import com.example.tintint_jw.View.SignUp.SignupActivity1
import kotlinx.android.synthetic.main.activity_school_authentication.*

class SchoolAuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_school_authentication)

        back.setOnClickListener{
            finish()
        }

        next.setOnClickListener(){
            if(intent.hasExtra("kakao")){
                val intent= Intent(this, SignUpActivity2::class.java)
                startActivity(intent)
            }
            else{
                val intent= Intent(this, SignupActivity1::class.java)
                startActivity(intent)
            }
        }

    }
}