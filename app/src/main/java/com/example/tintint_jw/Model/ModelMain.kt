package com.example.tintint_jw.Model

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.tintint_jw.SharedPreference.SharedPreference
import com.example.tintint_jw.View.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class ModelMain(var context: Activity) :Application() {

    var activity = context
    var prefs: SharedPreference = SharedPreference(context)
    // applicaiton을 상속받아서 그 context 값을 넣어줘야함. object
    fun signUP(email: String, pw : String, pwCheck:String ){
        val userRequest = RegisterUserRequest(email,pw,pwCheck)
        val call = RetrofitGenerator.create().registerUser(userRequest)

        call.enqueue(object : Callback<RegisterUserRequest>{
            override fun onFailure(call: Call<RegisterUserRequest>, t: Throwable) {
                 Log.d("testModel",t.toString())
                t.printStackTrace()
            }
            override fun onResponse(
                call: Call<RegisterUserRequest>,
                response: Response<RegisterUserRequest>
            ) {
                   Log.d("testModel",response.toString())
            }
        });
    }

    fun Login( email :String, pw: String)  {

        val loginRequest = LoginRequest(email,pw)
        val call = RetrofitGenerator.create().login(loginRequest)

        call.enqueue(object : Callback<LoginResponse>{
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                t.printStackTrace()
                 Toast.makeText(activity,"로그인에 실패하셨습니다.",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<LoginResponse >, response: Response<LoginResponse>) {
                Log.d("response",response.toString())
                var a : LoginResponse? = response.body()
                Log.d("response2",response.message().toString())

            }
        });
    }






}