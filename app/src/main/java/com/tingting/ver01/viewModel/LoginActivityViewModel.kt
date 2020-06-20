package com.tingting.ver01.viewModel

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.kakao.auth.Session
import com.tingting.ver01.view.Auth.SchoolAuthActivity
import com.tingting.ver01.view.Main.MainActivity
import com.tingting.ver01.model.Auth.Login.Kakao.LoginKakaoResponse
import com.tingting.ver01.model.Auth.Login.Local.LoginLocalResponse
import com.tingting.ver01.model.ModelSignUp
import com.tingting.ver01.sharedPreference.App

class LoginActivityViewModel :BaseViewModel(){


    fun login(context: Activity, pw:String, email:String) {
        dataLoading.value=true
        var value :Int = 0

        ModelSignUp.getProfileInstance().Login(pw , email){ isSuccess: Int, data: LoginLocalResponse? ->
            Log.d("isSuccess",isSuccess.toString())
            when(isSuccess){
                200-> {
                    value = 200
                    App.prefs.myId = email
                    App.prefs.mypassword = pw
                    App.prefs.myautoLogin = "true"
                    App.prefs.myToken = data!!.data.token.toString()
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                }

                400 ->  Toast.makeText(context, "가입되지 않은 아이디이거나, 잘못된 비밀번호입니다.", Toast.LENGTH_LONG).show()
                500 ->  Toast.makeText(context, "서버 에러입니다. 잠시 후 시도해주세요.", Toast.LENGTH_LONG).show()

            }

        }
    }

    fun loginKakao(context: Activity){
        dataLoading.value = true
        var kakaoToken : String?  = Session.getCurrentSession().accessToken
        App.prefs.myKakaoToken = kakaoToken
        ModelSignUp.getProfileInstance().LoginKakao(kakaoToken){isSuccess: Int, data: LoginKakaoResponse? ->

            when(isSuccess){
                200->{
                    if(App.prefs.myautoLogin.equals("true")){
                        App.prefs.myLoginType = "kakao"
                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)
                    }
                }
                500->{
                    App.prefs.myLoginType = "kakao"
                    val intent = Intent(context, SchoolAuthActivity::class.java)
                    context.startActivity(intent)
                }

            }

        }
    }

}