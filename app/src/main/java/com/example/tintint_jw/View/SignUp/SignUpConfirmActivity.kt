package com.example.tintint_jw.View.SignUp

import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.tintint_jw.R
import com.example.tintint_jw.View.SchoolAuthActivity
import com.varunest.sparkbutton.SparkEventListener
import kotlinx.android.synthetic.main.activity_signup_confirm.*
import kotlinx.android.synthetic.main.dialog_univ_list.view.*
import java.lang.Exception

class SignUpConfirmActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_confirm)

        // next
        next.setOnClickListener{
        try{

            if(agree1.isChecked&&agree2.isChecked&&agree3.isChecked&& agreeAll.isChecked){
                val intent = Intent(applicationContext, SchoolAuthActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(applicationContext, "필수 약관에 동의해주세요.", Toast.LENGTH_LONG).show()
            }

        }catch (e : Exception){
            e.printStackTrace()
        }
        }

        // back
        back.setOnClickListener {
            finish()
        }

        // 대학 목록 보기
        checkUnivList.setOnClickListener{
            val univDialog = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.dialog_univ_list,null)

            univDialog.setView(dialogView)
            val check = univDialog.show()

            dialogView.dialogDismiss.setOnClickListener(){
                check.dismiss()
            }

            dialogView.list.movementMethod = ScrollingMovementMethod.getInstance()


        }

        agreeAll.setEventListener(object : SparkEventListener {
            override fun onEventAnimationEnd(button: ImageView?, buttonState: Boolean) {
            }

            override fun onEventAnimationStart(button: ImageView?, buttonState: Boolean) {
            }

            override fun onEvent(button: ImageView?, buttonState: Boolean) {

                if(agreeAll.isChecked){
                    agreeAll.isChecked = true
                    agree1.isChecked = true
                    agree2.isChecked=true
                    agree3.isChecked=true

                }else{
                    agree1.isChecked=false
                    agree2.isChecked=false
                    agree3.isChecked=false
                    agreeAll.isChecked=false
                }

            }

        })


    }
}