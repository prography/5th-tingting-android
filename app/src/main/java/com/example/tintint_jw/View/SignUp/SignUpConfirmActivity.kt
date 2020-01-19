package com.example.tintint_jw.View.SignUp

import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.tintint_jw.R
import com.example.tintint_jw.View.Policy.CheckPolicy01
import com.example.tintint_jw.View.Policy.CheckPolicy02
import com.example.tintint_jw.View.SchoolAuthActivity
import com.varunest.sparkbutton.SparkEventListener
import kotlinx.android.synthetic.main.activity_signup_confirm.*
import kotlinx.android.synthetic.main.dialog_univ_list.view.*

class SignUpConfirmActivity: AppCompatActivity() {

    var agreeAllState:Boolean=false
    var agree1State:Boolean=false
    var agree2State:Boolean=false
    var agree3State:Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_confirm)

        // next
        next.setOnClickListener{

            if(agree1State&&agree2State&&agree3State||agreeAllState){
                val intent = Intent(applicationContext, SchoolAuthActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(applicationContext, "필수 약관에 동의해주세요.", Toast.LENGTH_LONG).show()
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

        // 약관 자세히 보기
        agreement1.setOnClickListener {
            val intent = Intent(applicationContext, CheckPolicy01::class.java)
            startActivity(intent)
        }

        agreement2.setOnClickListener {
            val intent = Intent(applicationContext, CheckPolicy02::class.java)
            startActivity(intent)
        }

        // 약관 동의 버튼
        agree1.setEventListener(object : SparkEventListener {
            override fun onEventAnimationEnd(button: ImageView?, buttonState: Boolean) {
            }

            override fun onEventAnimationStart(button: ImageView?, buttonState: Boolean) {
            }

            override fun onEvent(button: ImageView?, buttonState: Boolean) {
                if(buttonState){
                    agree1State = true
                }
                agree1State = false

            }

        })
        agree2.setEventListener(object : SparkEventListener {
            override fun onEventAnimationEnd(button: ImageView?, buttonState: Boolean) {
            }

            override fun onEventAnimationStart(button: ImageView?, buttonState: Boolean) {
            }

            override fun onEvent(button: ImageView?, buttonState: Boolean) {
                if(buttonState){
                    agree2State = true
                }
                agree2State = false
            }

        })
        agree3.setEventListener(object : SparkEventListener {
            override fun onEventAnimationEnd(button: ImageView?, buttonState: Boolean) {
            }

            override fun onEventAnimationStart(button: ImageView?, buttonState: Boolean) {
            }

            override fun onEvent(button: ImageView?, buttonState: Boolean) {
                if(buttonState){
                    agree3State = true}
                agree3State = false
            }

        })

        if(agree1.isChecked&&agree2.isChecked&&agree3.isChecked){
            agreeAllState = true
            agreeAll.isChecked = true
        }else{
            agreeAllState = false
            agreeAll.isChecked = false
        }
        agreeAll.setEventListener(object : SparkEventListener {
            override fun onEventAnimationEnd(button: ImageView?, buttonState: Boolean) {
            }

            override fun onEventAnimationStart(button: ImageView?, buttonState: Boolean) {
            }

            override fun onEvent(button: ImageView?, buttonState: Boolean) {

                if(buttonState){
                    agreeAllState = true
                    if(agreeAllState){
                        agree1State = true
                        agree1.isChecked =true
                        agree2State = true
                        agree2.isChecked =true
                        agree2State = true
                        agree3.isChecked =true
                        }
                    }else{
                        agreeAllState = false
                        agree1State = false
                        agree1.isChecked =false
                        agree2State = false
                        agree2.isChecked =false
                        agree2State = false
                        agree3.isChecked =false
                    }
            }

        })


    }
}