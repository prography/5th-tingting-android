package com.example.tintint_jw.new_package.view_model

import android.annotation.SuppressLint
import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.tintint_jw.Model.Auth.Login.Local.LoginLocalRequest
import com.example.tintint_jw.Model.Auth.Login.Local.LoginLocalResponse
import com.example.tintint_jw.SharedPreference.App
import com.example.tintint_jw.new_package.data.repository.LoginRepository
import com.example.tintint_jw.new_package.util.DataListener
import com.example.tintint_jw.new_package.util.extension.showToast

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val context = application.applicationContext
    private val loginRep by lazy { LoginRepository() }

    val id: ObservableField<String> = ObservableField()
    val pw: ObservableField<String> = ObservableField()

    private val _items: MutableLiveData<LoginLocalResponse> = MutableLiveData()
    val items: MutableLiveData<LoginLocalResponse>
        get() = _items

    @SuppressLint("CheckResult")
    fun callApiToLogin() {
        if (pw.get() == null || id.get() == null) {
            context.showToast("이메일 또는 비밀번호를 입력해주세요.")
            return
        } else {
            loginRep.get(LoginLocalRequest(pw.get()!!, id.get()!!), object : DataListener<LoginLocalResponse> {
                override fun onSuccess(data: LoginLocalResponse) {
                    _items.value = data

                    App.prefs.myToken = data.data.token
                    App.prefs.myId = id.get()
                    App.prefs.mypassword = pw.get()
                    App.prefs.myPw = pw.get()
                    App.prefs.myautoLogin = "true"
                }

                override fun onFail() {
                    context.showToast("일치하는 아이디가 없습니다.")
                }
            })
        }
    }
}