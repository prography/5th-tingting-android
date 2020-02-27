package com.tingting.ver01.View.SignUp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.tingting.ver01.R
import com.tingting.ver01.View.Policy.CheckPolicy01
import com.tingting.ver01.View.Policy.CheckPolicy02
import com.tingting.ver01.View.SchoolAuthActivity
import com.varunest.sparkbutton.SparkEventListener
import kotlinx.android.synthetic.main.activity_signup_confirm.*
import kotlinx.android.synthetic.main.dialog_univ_list.view.*

class SignUpConfirmActivity: AppCompatActivity() {

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_confirm)

        changeButton()
        // next
        next.setOnClickListener{
        try{

            if(agreeAll.isChecked){
                //val intent = Intent(applicationContext, SignupActivity1::class.java)
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

        view1.setOnClickListener {
            val intent:Intent = Intent(applicationContext, CheckPolicy01::class.java)
            startActivity(intent)
        }

        view2.setOnClickListener {
            val intent:Intent = Intent(applicationContext, CheckPolicy02::class.java)
            startActivity(intent)
        }

        agreeAll.setEventListener(object :SparkEventListener{
            override fun onEventAnimationEnd(button: ImageView?, buttonState: Boolean) {
            }

            override fun onEvent(button: ImageView?, buttonState: Boolean) {
                allButton(agreeAll.isChecked)
            }

            override fun onEventAnimationStart(button: ImageView?, buttonState: Boolean) {
            }

        })

        // 약관 동의 버튼
        agree1.setEventListener(object : SparkEventListener {
            override fun onEventAnimationEnd(button: ImageView?, buttonState: Boolean) {
            }

            override fun onEventAnimationStart(button: ImageView?, buttonState: Boolean) {
            }

            override fun onEvent(button: ImageView?, buttonState: Boolean) {
                subButton(agree1.isChecked, agree2.isChecked, agree3.isChecked)
            }

        })
        agree2.setEventListener(object : SparkEventListener {
            override fun onEventAnimationEnd(button: ImageView?, buttonState: Boolean) {
            }

            override fun onEventAnimationStart(button: ImageView?, buttonState: Boolean) {
            }

            override fun onEvent(button: ImageView?, buttonState: Boolean) {
                subButton(agree1.isChecked, agree2.isChecked, agree3.isChecked)

            }

        })
        agree3.setEventListener(object : SparkEventListener {
            override fun onEventAnimationEnd(button: ImageView?, buttonState: Boolean) {
            }

            override fun onEventAnimationStart(button: ImageView?, buttonState: Boolean) {
            }

            override fun onEvent(button: ImageView?, buttonState: Boolean) {
                subButton(agree1.isChecked, agree2.isChecked, agree3.isChecked)

            }

        })
    }

    fun allButton(agreeAll:Boolean){
        if(agreeAll){
            agree1.isChecked = true
            agree2.isChecked = true
            agree3.isChecked = true
        }else{
            agree1.isChecked = false
            agree2.isChecked = false
            agree3.isChecked = false
        }
        changeButton()
    }

    fun subButton(agree1:Boolean, agree2:Boolean, agree3:Boolean){
        if(agree1&&agree2&&agree3)
            agreeAll.isChecked = true
        else
            agreeAll.isChecked = false

        changeButton()
    }

    fun changeButton(){
        next.isEnabled = agreeAll.isChecked
    }
}