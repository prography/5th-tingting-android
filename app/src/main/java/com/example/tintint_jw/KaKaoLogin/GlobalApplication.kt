package com.example.tintint_jw.KaKaoLogin

import android.app.Application
import com.example.tintint_jw.SharedPreference.App
import com.example.tintint_jw.SharedPreference.SharedPreference
import com.kakao.auth.KakaoSDK


//class GlobalApplication : Application() {
//
//    lateinit var prefs : SharedPreference
//
//
//    override fun onCreate() {
//        super.onCreate()
//
//        instance = this
//        KakaoSDK.init(KaKaoSDKAdapter())
//
//        App.prefs = SharedPreference(applicationContext)
//        super.onCreate()
//    }
//
//    override fun onTerminate() {
//        super.onTerminate()
//        instance = null
//    }
//
//    fun getGlobalApplicationContext(): GlobalApplication {
//        checkNotNull(instance) { "this application does not inherit com.kakao.GlobalApplication" }
//        return instance!!
//    }
//
//    companion object {
//        var instance: GlobalApplication? = null
//    }
//}