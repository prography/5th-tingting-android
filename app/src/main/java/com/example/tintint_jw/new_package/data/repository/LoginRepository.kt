package com.example.tintint_jw.new_package.data.repository

import android.annotation.SuppressLint
import android.util.Log
import com.example.tintint_jw.Model.Auth.Login.Local.LoginLocalRequest
import com.example.tintint_jw.Model.Auth.Login.Local.LoginLocalResponse
import com.example.tintint_jw.Model.RetrofitService
import com.example.tintint_jw.new_package.data.Api
import com.example.tintint_jw.new_package.util.DataListener
import com.example.tintint_jw.new_package.util.extension.commonRx

class LoginRepository() {

    @SuppressLint("CheckResult")
    fun get(loginReq : LoginLocalRequest, listener: DataListener<LoginLocalResponse>) {
        Api.createService(RetrofitService::class.java).postLogin(loginReq)
            .commonRx()
            .subscribe({
                Log.i("LoginRepository", "postLogin onSuccess")
                listener.onSuccess(it)
            }, {
                it.printStackTrace()
                listener.onFail()
            })
    }
}