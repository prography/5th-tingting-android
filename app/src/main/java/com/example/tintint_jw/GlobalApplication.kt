package com.example.tintint_jw

import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager

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

    override fun onCreate() {
        super.onCreate()

        instance = this
        DEBUG = isDebuggable(this)
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
    // endregion
}