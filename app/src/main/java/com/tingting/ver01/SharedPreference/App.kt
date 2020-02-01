package com.tingting.ver01.SharedPreference

import android.app.Application
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.kakao.usermgmt.StringSet
import com.tingting.ver01.SharedPreference.SharedPreference

class App :Application(){
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    companion object{

        lateinit var prefs : SharedPreference
    }

    override fun onCreate() {

        prefs = SharedPreference(applicationContext)
        super.onCreate()

        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()

        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, App.prefs.mylocal_id.toString())
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, StringSet.name)
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)

    }


}

