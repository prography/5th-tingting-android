package com.tingting.ver01.sharedPreference

import android.content.Context
import android.content.SharedPreferences

class SharedPreference(context: Context) {

    val PREFS_FILENAME = "prefs"
    //카카오 id 저장
    val id = "myId"
    val pw = "myPw"
    val autoLogin = "myautoLogin"
    val token = "myToken"
    val kakoToken = "myKakaoToken"
    val local_id = "mylocal_id"
    val password = "mypassword"
    val gender = "mygender"
    val name = "myname"
    val birth = "mybirth"
    val thumbnail = "mythumnail"
    val authenticated_address = "myauthenticated_email"
    val height = "myheight"
    val personalId = "myPersonalId"
    val code = "mycode"


    //로그인 중에 끊긴 경우
    val isMaking = "myisMaking"
    //로그인 타입
    val loginType = "myLoginType"


    val prefs: SharedPreferences? = context.getSharedPreferences(PREFS_FILENAME, 0)
    var myId: String?
        get() = prefs?.getString(id, "")
        set(value) = prefs?.edit()!!.putString(id, value).apply()

    var myPw: String?
        get() = prefs?.getString(pw, "")
        set(value) = prefs?.edit()!!.putString(pw, value).apply()

    var myToken: String?
        get() = prefs?.getString(token, "")
        set(value) = prefs?.edit()!!.putString(token, value).apply()

    var mylocal_id: String?
        get() = prefs?.getString(local_id, "")
        set(value) = prefs?.edit()!!.putString(local_id, value).apply()

    var mypassword: String?
        get() = prefs?.getString(password, "")
        set(value) = prefs?.edit()!!.putString(password, value).apply()

    var mygender: String?
        get() = prefs?.getString(gender, "")
        set(value) = prefs?.edit()!!.putString(gender, value).apply()

    var myname: String?
        get() = prefs?.getString(name, "")
        set(value) = prefs?.edit()!!.putString(name, value).apply()

    var mybirth: String?
        get() = prefs?.getString(birth, "")
        set(value) = prefs?.edit()!!.putString(birth, value).apply()

    var mythumnail: String?
        get() = prefs?.getString(thumbnail, "")
        set(value) = prefs?.edit()!!.putString(thumbnail, value).apply()

    var myauthenticated_address: String?
        get() = prefs?.getString(authenticated_address, "")
        set(value) = prefs?.edit()!!.putString(authenticated_address, value).apply()

    var myheight: String?
        get() = prefs?.getString(height, "")
        set(value) = prefs?.edit()!!.putString(height, value).apply()

    var myautoLogin : String?
        get() = prefs?.getString(autoLogin,"false")
        set(value) = prefs?.edit()!!.putString(autoLogin,value).apply()

    var myPersonalId : String?
        get() = prefs?.getString(personalId,"false")
        set(value) = prefs?.edit()!!.putString(personalId,value).apply()

    var myisMaking : String?
        get() = prefs?.getString(isMaking,"false")
        set(value) = prefs?.edit()!!.putString(isMaking,value).apply()

    var myLoginType : String?
    get() = prefs?.getString(loginType,"")
    set(value) = prefs?.edit()!!.putString(loginType,value).apply()

    var mycode:String?
        get() = prefs?.getString(code, "")
        set(value) = prefs?.edit()!!.putString(code, value).apply()

    var myKakaoToken : String?
    get() = prefs?.getString(kakoToken,"")
    set(value) = prefs?.edit()!!.putString(kakoToken,value).apply()



}