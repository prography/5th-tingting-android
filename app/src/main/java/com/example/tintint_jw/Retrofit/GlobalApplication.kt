/*
package com.example.tintint_jw.Retrofit

import android.app.Application
import com.example.tintint_jw.Retrofit.Login.KakaoSDKAdapter
import com.kakao.auth.KakaoSDK

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        KakaoSDK.init(KakaoSDKAdapter())
    }

    override fun onTerminate() {
        super.onTerminate()
        instance = null
    }

    fun getGlobalApplicationContext(): GlobalApplication {
        //checkNotNull(instance) { "this application does not inherit com.kakao.GlobalApplication" }
        return instance!!
    }

    companion object {
        var instance: GlobalApplication? = null
    }

}*/
