package com.example.tintint_jw.View

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.BoringLayout
import android.widget.DatePicker
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import com.example.tintint_jw.R
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


        val dpd = DatePickerDialog(this@SignUpActivity,android.R.style.Theme_Holo_Dialog, DatePickerDialog.OnDateSetListener {
                view, year, monthOfYear, dayOfMonth -> pickBirth.setText(year.toString()+"."+(monthOfYear+1).toString()+"."+dayOfMonth.toString());
        }, year, month,  day)


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
        text.setTextColor(Color.BLACK)
    }
    //배경화면을 핑크색으로 바꿔주는 코드
    @SuppressLint("ResourceAsColor")
    fun bgToPink(li : LinearLayout,li2:LinearLayout,text: TextView){
        li.setBackgroundResource(R.drawable.whole_pink)
        li2.setBackgroundResource(R.drawable.edge_gray_whole)
        text.setTextColor(Color.WHITE)
    }
}
