package com.example.tintint_jw.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tintint_jw.R
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        next.setOnClickListener(){
            val intent = Intent(applicationContext,PictureRegisterActivity::class.java);
            startActivity(intent)
        }
    }
}
