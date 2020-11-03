package com.tingting.ver01.sharedPreference

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Base64
import com.google.firebase.analytics.FirebaseAnalytics
import com.tingting.ver01.view.GlideImage
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class App :Application(){
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    companion object{
        lateinit var instance: App
        lateinit var prefs : SharedPreference
    }

    override fun onCreate() {
        super.onCreate()
        prefs = SharedPreference(applicationContext)

        instance = this

        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()

        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, App.prefs.mylocal_id.toString())
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "AppActivitry")
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)

        GlideImage.sign++

    }



}

