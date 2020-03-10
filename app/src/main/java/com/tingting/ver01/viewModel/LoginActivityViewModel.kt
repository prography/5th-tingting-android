package com.tingting.ver01.viewModel

import android.app.Activity
import android.content.Intent
import com.kakao.auth.Session
import com.tingting.ver01.sharedPreference.App
import com.tingting.ver01.View.Auth.SchoolAuthActivity
import com.tingting.ver01.View.Main.MainActivity
import com.tingting.ver01.model.Auth.Login.Kakao.LoginKakaoResponse
import com.tingting.ver01.model.Auth.Login.Local.LoginLocalResponse
import com.tingting.ver01.model.ModelSignUp

class LoginActivityViewModel :BaseViewModel(){


    fun login(context: Activity, pw:String, email:String) : Int{
        dataLoading.value=true
        var value :Int = 0

        ModelSignUp.getProfileInstance().Login(pw , email){isSuccess: Int, data: LoginLocalResponse ->

            when(isSuccess){
                200-> {
                    value = 200
                    App.prefs.myId = email
                    App.prefs.mypassword = pw
                    App.prefs.myautoLogin = "true"
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                }
                400-> value = 400;
                500-> value = 500;

            }

        }
        return value;
    }

    fun loginKakao(context: Activity){
        dataLoading.value = true
        var kakaoToken  = Session.getCurrentSession().accessToken.toString()
        ModelSignUp.getProfileInstance().LoginKakao(kakaoToken){isSuccess: Int, data: LoginKakaoResponse? ->

            when(isSuccess){
                200->{
                    if(App.prefs.myautoLogin.equals("false")){

                    }else{
                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)
                    }

                }
                500->{
                    App.prefs.myLoginType = "kakao"
                    val intent = Intent(context, SchoolAuthActivity::class.java)
                    context.startActivity(intent)
                }
                else->{
                    if(App.prefs.isMaking.equals("true")){
                        val intent = Intent(context, SchoolAuthActivity::class.java)
                        context.startActivity(intent)
                    }
                }
            }

        }
    }

}