package com.example.tintint_jw.View

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.BoringLayout
import android.widget.*
import com.example.tintint_jw.R
import com.example.tintint_jw.Toolbar.BackToolbar
import kotlinx.android.synthetic.main.activity_create_team2.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.text.SimpleDateFormat
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS

class SignUpActivity : AppCompatActivity() {

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //변수 초기화
        var male : Boolean = true;
        var female : Boolean = false;

        val cal  = Calendar.getInstance()

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        //set Toolbar

        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        //initialize date picker dialog
        val dpd = DatePickerDialog(this@SignUpActivity,android.R.style.Theme_Holo_Dialog, DatePickerDialog.OnDateSetListener {
                view, year, monthOfYear, dayOfMonth -> pickBirth.setText(year.toString()+"."+(monthOfYear+1).toString()+"."+dayOfMonth.toString());
        }, year, month,  day)
        // pickBirth. click listener
        pickBirth.setOnClickListener {
            dpd.show()
        }




        //다음 으로 넘어가는 버튼
        next.setOnClickListener(){
            val intent = Intent(applicationContext,PictureRegisterActivity::class.java);
            startActivity(intent)
        }

       //성별 여자를 클릭하면 색이 바뀜
        genderFemale.setOnClickListener(){
            female=true;
            male=false;
            bgToWhite(genderMale,genderMain,genderMaleTv)
            bgToPink(genderFemale,genderMain,genderFemaleTv)
        }

        //성별 남자를 클릭하면 색이 바뀜
        genderMale.setOnClickListener(){
            male=true;
            female=false;
            bgToPink(genderMale,genderMain,genderMaleTv)
            bgToWhite(genderFemale,genderMain,genderFemaleTv)
        }
    }

    //배경화면을 흰색으로 바꿔주는 코드
    @SuppressLint("ResourceAsColor")
    fun bgToWhite(li : LinearLayout, li2:LinearLayout ,text: TextView){
        li.setBackgroundResource(R.drawable.whole_white)
        li2.setBackgroundResource(R.drawable.edge_gray_whole)
        text.setTextColor(R.color.gray)
    }
    //배경화면을 핑크색으로 바꿔주는 코드
    @SuppressLint("ResourceAsColor")
    fun bgToPink(li : LinearLayout,li2:LinearLayout,text: TextView){
        li.setBackgroundResource(R.drawable.whole_pink)
        li2.setBackgroundResource(R.drawable.edge_gray_whole)
        text.setTextColor(Color.WHITE)
    }

    // password legnth is more than 8, At least one number and one character should be include .
    fun checkPw(pw : EditText) : Boolean{
      if(pw.text.toString().length<8){
          Toast.makeText(applicationContext,"비밀번호의 길이는 최소 8자리이상 이어야 합니다.",Toast.LENGTH_LONG).show()
          return false;
      }
        val reg = Regex("(?=.*\\d{1,50})(?=.*[~`!@#\$%\\^&*()-+=]{1,50})(?=.*[a-zA-Z]{2,50}).{8,50}\n")
        if(!pw.text.toString().matches(reg)){
            Toast.makeText(applicationContext,"숫자, 특문 각 1회 이상, 영문은 2개 이상 사용하여 8자리 이상 입력",Toast.LENGTH_LONG).show()
            return false;
        }
        return true;
    }

    fun checkEmail(email : EditText): Boolean{

        val reg = Regex("^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}")
        if(!email.text.toString().matches(reg)){
            Toast.makeText(applicationContext,"유효한 이메일 형식을 입력해주세요",Toast.LENGTH_LONG).show()
            return false;
        }
        return true;
    }


}
