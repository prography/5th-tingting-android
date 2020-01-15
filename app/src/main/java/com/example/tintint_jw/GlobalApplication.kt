package com.example.tintint_jw

import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.example.tintint_jw.KaKaoLogin.KaKaoSDKAdapter
import com.example.tintint_jw.SharedPreference.App
import com.example.tintint_jw.SharedPreference.SharedPreference
import com.example.tintint_jw.new_package.util.network.NetworkStateHolder.registerConnectivityBroadcaster
import com.kakao.auth.KakaoSDK

class GlobalApplication : Application() {
    companion object {
        private var instance: GlobalApplication? = null
        fun getInstance() : GlobalApplication {
            if(instance == null)
                instance = GlobalApplication()

            return instance!!
        }

        var DEBUG: Boolean = false
    }

    lateinit var prefs : SharedPreference

    override fun onCreate() {
        super.onCreate()

        instance = this

        KakaoSDK.init(KaKaoSDKAdapter())
        App.prefs = SharedPreference(applicationContext)

        DEBUG = isDebuggable(this)
        registerConnectivityBroadcaster()
    }

    // region 현재 디버그모드여부를 리턴
    private fun isDebuggable(context: Context): Boolean {
        var debuggable = false

        val packageManager = context.packageManager
        try {
            val applicationInfo = packageManager.getApplicationInfo(context.packageName, 0)
            debuggable = 0 != applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE
        } catch (e: PackageManager.NameNotFoundException) {
            /* debuggable variable will remain false */
            e.printStackTrace()
        }

        return debuggable
    }
    // endregion\

    override fun onTerminate() {
        super.onTerminate()
        instance = null
    }

    fun getGlobalApplicationContext(): GlobalApplication {
        checkNotNull(instance) { "this application does not inherit com.kakao.GlobalApplication" }
        return instance!!
    }
}