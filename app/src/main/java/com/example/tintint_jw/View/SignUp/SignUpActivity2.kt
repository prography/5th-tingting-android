package com.example.tintint_jw.View.SignUp

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.tintint_jw.Model.IdCallBack
import com.example.tintint_jw.Model.ModelSignUp
import com.example.tintint_jw.R
import com.example.tintint_jw.SharedPreference.App
import com.example.tintint_jw.View.PictureRegisterActivity
import com.example.tintint_jw.View.SchoolAuthActivity
import kotlinx.android.synthetic.main.activity_sign_up2.*
import kotlinx.coroutines.*
import java.lang.Exception
import java.lang.Runnable
import java.util.*

class SignUpActivity2 : AppCompatActivity() {

    @SuppressLint("ResourceAsColor")
    var model: ModelSignUp = ModelSignUp(this)
    var nickNameval = false
    lateinit var mHandler: Handler
    lateinit var mRunnable: Runnable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up2)

        mHandler = Handler()


        //변수 초기화
        var male: Boolean = true;
        var female: Boolean = false;

        val cal = Calendar.getInstance()

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        //set Toolbar


        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        //initialize date picker dialog
        val dpd = DatePickerDialog(
            this@SignUpActivity2,
            android.R.style.Theme_Holo_Dialog,

            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                var month=""
                var day = ""
                if(monthOfYear<10){
                     month = "0"+(monthOfYear + 1).toString()
                }else{
                     month = (monthOfYear + 1).toString()
                }

                if(dayOfMonth<10){
                     day = "0"+dayOfMonth.toString()
                }else{
                     day = dayOfMonth.toString()
                }

                pickBirth.setText(year.toString()+"-"+month+"-"+ day)

            },
            year,
            month,
            day
        )
        // pickBirth. click listener
        pickBirth.setOnClickListener {
            dpd.show()
        }

        //back button
       back.setOnClickListener() {
            finish()
        }


        //다음 으로 넘어가는 버튼
        next.setOnClickListener() {

/*            val intent = Intent(applicationContext, PictureRegisterActivity::class.java);
            //
            startActivity(intent)
*/
            // check empty value function.
            if (checkEmptyField(
                    NickName.text.toString(),
                    pickBirth.text.toString(),
                    height.text.toString(),
                    school.text.toString(),
                    hobby.text.toString(),
                    character.text.toString()
                )
            ) {
                App.prefs.myname = NickName.text.toString()
                App.prefs.mybirth = pickBirth.text.toString()
                App.prefs.myheight = height.text.toString()

                if(female){
                    App.prefs.mygender = "1"
                }else{
                    App.prefs.mygender = "0"
                }
                if(nickNameval){
                    val intent = Intent(applicationContext, PictureRegisterActivity::class.java);
                    //
                    startActivity(intent)
                }else{
                    Toast.makeText(applicationContext,"닉네임 중복 확인을 해주세요",Toast.LENGTH_LONG).show()
                }

            }
        }



        //닉네임 체크 버튼
        checkNickname.setOnClickListener() {
            if (model.CheckDuplicateName(NickName.text.toString(), object: IdCallBack {
                    override fun onSuccess(value: String) {
                                if(value.equals("true")){
                                    nickNameval = true
                                    checknickmessage.layoutParams.height =
                                        (20 * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
                                    checknickmessage.setText("사용가능한 닉네임 입니다. ")
                                    checknickmessage.visibility = View.VISIBLE
                                    Log.d("SignUpActivity2","chekc 실행")
                                }
                                else{
                                    checknickmessage.layoutParams.height =
                                        (20 * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
                                    checknickmessage.visibility = View.VISIBLE
                                    checknickmessage.setText("중복된 닉네임 입니다.")
                                }

                    }
                })) {

            }

        }

        //성별 여자를 클릭하면 색이 바뀜
        genderFemale.setOnClickListener() {
            female = true;
            male = false;
            bgToWhite(genderMale, genderMain, genderMaleTv)
            bgToPink(genderFemale, genderMain, genderFemaleTv)
        }

        //성별 남자를 클릭하면 색이 바뀜
        genderMale.setOnClickListener() {
            male = true;
            female = false;
            bgToPink(genderMale, genderMain, genderMaleTv)
            bgToWhite(genderFemale, genderMain, genderFemaleTv)
        }

    }

    //배경화면을 흰색으로 바꿔주는 코드
    @SuppressLint("ResourceAsColor")
    fun bgToWhite(li: LinearLayout, li2: LinearLayout, text: TextView) {
        li.setBackgroundResource(R.drawable.whole_white)
        li2.setBackgroundResource(R.drawable.edge_gray_whole)
        text.setTextColor(R.color.gray)
    }

    //배경화면을 핑크색으로 바꿔주는 코드
    @SuppressLint("ResourceAsColor")
    fun bgToPink(li: LinearLayout, li2: LinearLayout, text: TextView) {
        li.setBackgroundResource(R.drawable.whole_pink)
        li2.setBackgroundResource(R.drawable.edge_gray_whole)
        text.setTextColor(Color.WHITE)
    }


    // password legnth is more than 8, At least one number and one character should be include .


    fun checkEmptyField(
        nickName: String,
        pickBirth: String,
        height: String,
        school: String,
        hobby: String,
        character: String
    ): Boolean {

        if (nickName.isEmpty()) {
            Toast.makeText(applicationContext, "닉네임값을 확인해주세요", Toast.LENGTH_LONG).show();
            return false;
        }
        if (pickBirth.isEmpty()) {
            Toast.makeText(applicationContext, "생년월일을 확인 해주세요.", Toast.LENGTH_LONG).show();
            return false;
        }

        if (height.isEmpty()) {
            Toast.makeText(applicationContext, "키 값을 확인해주세요", Toast.LENGTH_LONG).show();
            return false;
        }
        if (school.isEmpty()) {
            Toast.makeText(applicationContext, "학교 값을 확인해주세요", Toast.LENGTH_LONG).show();
            return false;
        }
        if (hobby.isEmpty()) {
            Toast.makeText(applicationContext, "취미 값을 확인해주세요", Toast.LENGTH_LONG).show();
            return false;
        }
        if (character.isEmpty()) {
            Toast.makeText(applicationContext, "성격 값을 확인해주세요", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;

    }




}
