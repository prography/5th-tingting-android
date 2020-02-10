package com.tingting.ver01.View.SignUp

import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.tingting.ver01.R
import com.tingting.ver01.View.Policy.CheckPolicy01
import com.tingting.ver01.View.Policy.CheckPolicy02
import com.tingting.ver01.View.SchoolAuthActivity
import com.varunest.sparkbutton.SparkEventListener
import kotlinx.android.synthetic.main.dialog_univ_list.view.*
import kotlinx.android.synthetic.main.signup_confirm_k_activity.*
import kotlinx.android.synthetic.main.signup_confirm_k_activity.checkUnivList
import kotlinx.android.synthetic.main.signup_confirm_k_activity.next

class SignUpConfirmKActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_confirm_k_activity)

        changeButton()

        next.setOnClickListener {
            try{

                val intent = Intent(applicationContext, SchoolAuthActivity::class.java)
                intent.putExtra("kakao","kakao")
                startActivity(intent)

            }catch (e :Exception){
                e.printStackTrace()
            }

        }


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

        agreeAllk.setEventListener(object : SparkEventListener {
            override fun onEventAnimationEnd(button: ImageView?, buttonState: Boolean) {
            }

            override fun onEventAnimationStart(button: ImageView?, buttonState: Boolean) {
            }

            override fun onEvent(button: ImageView?, buttonState: Boolean) {
                allButton(agreeAllk.isChecked)

            }

        })

        agree1k.setEventListener(object :SparkEventListener{
            override fun onEventAnimationEnd(button: ImageView?, buttonState: Boolean) {
            }

            override fun onEvent(button: ImageView?, buttonState: Boolean) {
                subButton(agree1k.isChecked, agree2k.isChecked, agree3k.isChecked)
            }

            override fun onEventAnimationStart(button: ImageView?, buttonState: Boolean) {
            }

        })

        agree2k.setEventListener(object :SparkEventListener{
            override fun onEventAnimationEnd(button: ImageView?, buttonState: Boolean) {
            }

            override fun onEvent(button: ImageView?, buttonState: Boolean) {
                subButton(agree1k.isChecked, agree2k.isChecked, agree3k.isChecked)
            }

            override fun onEventAnimationStart(button: ImageView?, buttonState: Boolean) {
            }

        })

        agree3k.setEventListener(object :SparkEventListener{
            override fun onEventAnimationEnd(button: ImageView?, buttonState: Boolean) {
            }

            override fun onEvent(button: ImageView?, buttonState: Boolean) {
                subButton(agree1k.isChecked, agree2k.isChecked, agree3k.isChecked)
            }

            override fun onEventAnimationStart(button: ImageView?, buttonState: Boolean) {
            }

        })

        agreement1.setOnClickListener {
            val intent:Intent = Intent(applicationContext, CheckPolicy01::class.java)
            startActivity(intent)
        }

        agreement2.setOnClickListener {
            val intent:Intent = Intent(applicationContext, CheckPolicy02::class.java)
            startActivity(intent)
        }
    }

    fun allButton(agreeAllk:Boolean){
        if(agreeAllk){
            agree1k.isChecked = true
            agree2k.isChecked = true
            agree3k.isChecked = true
        }else{
            agree1k.isChecked = false
            agree2k.isChecked = false
            agree3k.isChecked = false
        }
        changeButton()
    }

    fun subButton(agree1:Boolean, agree2:Boolean, agree3:Boolean){
        if(agree1&&agree2&&agree3)
            agreeAllk.isChecked = true
        else
            agreeAllk.isChecked = false

        changeButton()
    }

    fun changeButton(){
        next.isEnabled = agreeAllk.isChecked
    }
}