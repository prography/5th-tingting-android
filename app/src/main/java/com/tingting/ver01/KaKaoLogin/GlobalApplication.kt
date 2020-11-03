package com.tingting.ver01.KaKaoLogin

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.tingting.ver01.sharedPreference.App
import com.tingting.ver01.sharedPreference.SharedPreference


class GlobalApplication : Application() {

    lateinit var prefs : SharedPreference


    override fun onCreate() {
        super.onCreate()

        instance = this
        KakaoSdk.init(this, "5fd375a184a5929ffb6ee99280a33288")

        App.prefs = SharedPreference(applicationContext)
        super.onCreate()
    }

    override fun onTerminate() {
        super.onTerminate()
        instance = null
    }

    fun getGlobalApplicationContext(): GlobalApplication {
        checkNotNull(instance) { "this application does not inherit com.kakao.GlobalApplication" }
        return instance!!
    }

    companion object {
        var instance: GlobalApplication? = null
    }

}