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
                if(agreeAllk.isChecked){
                    agree1k.isChecked=false
                    agree2k.isChecked=false
                    agree3k.isChecked=false
                    agreeAllk.isChecked=false
                }else{
                    agree1k.isChecked=true
                    agree2k.isChecked=true
                    agree3k.isChecked=true
                    agreeAllk.isChecked=true
                }

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
}