package com.example.tintint_jw.SharedPreference

import android.app.Application
import com.example.tintint_jw.KaKaoLogin.KaKaoSDKAdapter
import com.kakao.auth.KakaoSDK

class App :Application(){

    companion object{

        lateinit var prefs : SharedPreference
    }

    override fun onCreate() {
        prefs = SharedPreference(applicationContext)
        super.onCreate()
    }


}

