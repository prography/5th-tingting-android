package com.tingting.ver01.FindIdAndPw

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.tingting.ver01.Model.ModelSignUp
import com.tingting.ver01.R
import kotlinx.android.synthetic.main.activity_find_id.*

class FindId : AppCompatActivity() {

    var model:ModelSignUp = ModelSignUp(this)
    var isValid:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_id)

        changeButton()

        loginId.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })
        back.setOnClickListener(){
            finish()
        }
    }

    fun changeButton(){
        next.isEnabled = isValid
    }

}
