package com.example.tintint_jw.SharedPreference

import android.content.Context
import android.content.SharedPreferences
import java.lang.reflect.Array.get

class SharedPreference(context:Context) {
//chagnetest
    val PREFS_FILENAME = "prefs"
    val id = "myId"
    val pw = "myPw"
    val prefs: SharedPreferences? = context.getSharedPreferences(PREFS_FILENAME,0)

    var myId : String?
        get()  = prefs?.getString(id,"")
        set(value) = prefs?.edit()!!.putString(id,value).apply()

    var myPw : String?
        get()  = prefs?.getString(pw,"")
        set(value) = prefs?.edit()!!.putString(pw,value).apply()

}