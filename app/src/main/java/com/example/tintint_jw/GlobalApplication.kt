package com.example.tintint_jw

import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import android.util.Base64
import com.example.tintint_jw.KaKaoLogin.KaKaoSDKAdapter
import com.example.tintint_jw.SharedPreference.App
import com.example.tintint_jw.SharedPreference.SharedPreference
import com.example.tintint_jw.new_package.common.Dlog
import com.example.tintint_jw.new_package.util.network.NetworkStateHolder.registerConnectivityBroadcaster
import com.kakao.auth.KakaoSDK
import com.kakao.util.helper.Utility
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

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

        Dlog.d("hash = "+getHashKey(this).toString())
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

    private fun getHashKey(context: Context): String? {
        try {
            if (Build.VERSION.SDK_INT >= 28) {
                val packageInfo = Utility.getPackageInfo(context, PackageManager.GET_SIGNING_CERTIFICATES)
                val signatures = packageInfo.signingInfo.apkContentsSigners
                val md = MessageDigest.getInstance("SHA")
                for (signature in signatures) {
                    md.update(signature.toByteArray())
                    return String(Base64.encode(md.digest(), Base64.NO_WRAP))
                }
            } else {
                val packageInfo = Utility.getPackageInfo(context, PackageManager.GET_SIGNATURES) ?: return null

                for (signature in packageInfo.signatures) {
                    try {
                        val md = MessageDigest.getInstance("SHA")
                        md.update(signature.toByteArray())
                        return Base64.encodeToString(md.digest(), Base64.NO_WRAP)
                    } catch (e: NoSuchAlgorithmException) {
                    }
                }
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return null
    }

    fun getGlobalApplicationContext(): GlobalApplication {
        checkNotNull(instance) { "this application does not inherit com.kakao.GlobalApplication" }
        return instance!!
    }
}