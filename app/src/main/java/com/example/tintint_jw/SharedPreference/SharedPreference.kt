package com.example.tintint_jw.SharedPreference

import android.content.Context
import android.content.SharedPreferences

class SharedPreference(context:Context) {

    val PREFS_FILENAME = "prefs"
    val id = "myId"
    val pw = "myPw"
    val token="myToken"
    val prefs: SharedPreferences? = context.getSharedPreferences(PREFS_FILENAME,0)

    var myId : String?
        get()  = prefs?.getString(id,"")
        set(value) = prefs?.edit()!!.putString(id,value).apply()

    var myPw : String?
        get()  = prefs?.getString(pw,"")
        set(value) = prefs?.edit()!!.putString(pw,value).apply()

    var myToken: String?
    get() = prefs?.getString(token,"")
    set(value) = prefs?.edit()!!.putString(token,value).apply()

}