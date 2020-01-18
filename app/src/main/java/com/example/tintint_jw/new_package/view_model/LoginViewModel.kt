package com.example.tintint_jw.new_package.view_model

import android.annotation.SuppressLint
import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.example.tintint_jw.Model.Auth.Login.Local.LoginLocalRequest
import com.example.tintint_jw.Model.Auth.Login.Local.LoginLocalResponse
import com.example.tintint_jw.Model.RetrofitService
import com.example.tintint_jw.new_package.base.BaseViewModel
import com.example.tintint_jw.new_package.data.Api
import com.example.tintint_jw.new_package.util.extension.commonRx
import com.example.tintint_jw.new_package.util.extension.showToast

class LoginViewModel(application: Application) : BaseViewModel(application) {
    private val context = application.applicationContext

    val id: ObservableField<String> = ObservableField()
    val pw: ObservableField<String> = ObservableField()

    private val _refreshing: MutableLiveData<Boolean> = MutableLiveData()
    val refreshing: MutableLiveData<Boolean>
        get() = _refreshing

    private val _items: MutableLiveData<LoginLocalResponse> = MutableLiveData()
    val items: MutableLiveData<LoginLocalResponse>
        get() = _items

    @SuppressLint("CheckResult")
    fun callApiToLogin() {
        if (pw.get() == null || id.get() == null) {
            context.showToast("이메일 또는 비밀번호를 입력해주세요.")
            return
        } else {
            val loginRequest = LoginLocalRequest(pw.get()!!, id.get()!!)
            Api.createService(RetrofitService::class.java).postLogin(loginRequest)
                .commonRx()
                .subscribe({
                    _items.value = it
                }, {
                    it.printStackTrace()
                })

//            addToDisposable(call.with()
//                .doOnSubscribe { _refreshing.value = true }
//                .doOnSuccess { _refreshing.value = false }
//                .doOnError { _refreshing.value = false }
//                .subscribe({
//                    _items.value = it
//                }, {
//                    // handle errors
//                    it.printStackTrace()
//                })
//            )
        }
    }
}