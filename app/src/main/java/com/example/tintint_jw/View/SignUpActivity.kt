package com.example.tintint_jw.View

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.BoringLayout
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.*
import com.example.tintint_jw.R
import com.example.tintint_jw.Toolbar.BackToolbar
import kotlinx.android.synthetic.main.activity_create_team2.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS
import kotlinx.android.synthetic.main.activity_sign_up.back as back1

class SignUpActivity : AppCompatActivity() {

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //변수 초기화
        var male: Boolean = true;
        var female: Boolean = false;

        val cal = Calendar.getInstance()

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        //set Toolbar

        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        //initialize date picker dialog
        val dpd = DatePickerDialog(
            this@SignUpActivity,
            android.R.style.Theme_Holo_Dialog,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                pickBirth.setText(year.toString() + "." + (monthOfYear + 1).toString() + "." + dayOfMonth.toString());
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
            val intent = Intent(applicationContext, PictureRegisterActivity::class.java);
            startActivity(intent)

            // check empty value function.
            /*if(checkEmptyField(loginId.text.toString(),password.text.toString(),NickName.text.toString(),pickBirth.text.toString(),height.text.toString(),school.text.toString(),hobby.text.toString(),character.text.toString())){

            }*/
        }

        loginId.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                //this is test
                checkEmail(loginId)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })

        password.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                checkPw(password)
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })




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

    fun checkPw(pw: EditText): Boolean {
        if (pw.text.toString().length < 8) {
            checkpwmessage.setText("비밀 번호는 8자리 이상이어야 합니다.")
            return false;
        }else{
            checkpwmessage.setText("사용 가능합니다.")
        }
        val reg = Regex("(?=.*\\d{1,50})(?=.*[~`!@#\$%\\^&*()-+=]{1,50})(?=.*[a-zA-Z]{2,50}).{8,50}\$\n")
        return true;
    }

    fun checkEmail(email: EditText): Boolean {
        val reg =
            Regex("^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}")
        if (!email.text.toString().matches(reg)) {
            Toast.makeText(applicationContext, "유효한 이메일 형식을 입력해주세요", Toast.LENGTH_LONG).show()
            return false;
        }
        return true;
    }

    fun checkEmptyField(
        id: String,
        password: String,
        nickName: String,
        pickBirth: String,
        height: String,
        school: String,
        hobby: String,
        character: String): Boolean {
        if(id.isEmpty()){
            Toast.makeText(applicationContext,"아이디 필드를 확인해주세요",Toast.LENGTH_LONG).show();
            return  false;
        }
        if(password.isEmpty()){
            Toast.makeText(applicationContext,"패스워드 필드를 확인해주세요",Toast.LENGTH_LONG).show();
            return  false;
        }
        if(nickName.isEmpty()){
            Toast.makeText(applicationContext,"닉네임값을 확인해주세요",Toast.LENGTH_LONG).show();
            return  false;
        }
        if(pickBirth.isEmpty()){
            Toast.makeText(applicationContext,"생년월일을 확인 해주세요.",Toast.LENGTH_LONG).show();
            return  false;
        }

        if(height.isEmpty()){
            Toast.makeText(applicationContext,"키 값을 확인해주세요",Toast.LENGTH_LONG).show();
            return  false;
        }
        if(school.isEmpty()){
            Toast.makeText(applicationContext,"학교 값을 확인해주세요",Toast.LENGTH_LONG).show();
            return  false;
        }
        if(hobby.isEmpty()){
            Toast.makeText(applicationContext,"취미 값을 확인해주세요",Toast.LENGTH_LONG).show();
            return  false;
        }
        if(character.isEmpty()){
            Toast.makeText(applicationContext,"성격 값을 확인해주세요",Toast.LENGTH_LONG).show();
            return  false;
        }

    return true;

    }


}
