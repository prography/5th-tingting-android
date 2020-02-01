package com.example.tintint_jw.view

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.annotation.LayoutRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.tintint_jw.FindIdAndPw.FindId
import com.example.tintint_jw.FindIdAndPw.FindPw
import com.example.tintint_jw.Model.ModelSignUp
import com.example.tintint_jw.R
import com.example.tintint_jw.SharedPreference.App
import com.example.tintint_jw.view.SignUp.SignUpActivity2
import com.example.tintint_jw.view.SignUp.SignUpConfirmActivity
import com.example.tintint_jw.databinding.ActivityLogin2Binding
import com.example.tintint_jw.new_package.base.BaseActivity
import com.example.tintint_jw.new_package.etc.KakaoSessionCallback
import com.example.tintint_jw.new_package.util.extension.isLoading
import com.example.tintint_jw.new_package.util.extension.kakaoLogin
import com.example.tintint_jw.new_package.util.extension.showToast
import com.example.tintint_jw.new_package.view_model.LoginViewModel
import com.kakao.auth.AuthType
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.util.exception.KakaoException
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * 앱 들어와서 로그인 화면에서 바로 권한 관련 띄워주는게 아니라 얼럿 띄워주는데, 얼럿에서 이런이런 권한을 사용하니 동의해달라
 * 라는 내용이 먼저 띄워지고 나서 권한 동의가 떠야해!
 *
 * 권한을 거부했을때 앱이 종료되지 않고 사라는 있어야해!
 *
 * 권한 거부 및 안보이기 를 했을 경우도 대비!
 */

class LoginActivity : BaseActivity<ActivityLogin2Binding>() {
    @LayoutRes
    override fun layoutRes(): Int = R.layout.activity_login_2

    companion object {
        private const val PERMISSION_STORAGE = 1000
    }

    private val loginVM by lazy { ViewModelProviders.of(this).get(LoginViewModel::class.java) }

    override fun initView() {
        binding.activity = this
        binding.loginVM = loginVM
        isLoadingView()
        setViewModel()

        App.prefs.myauthenticated_address = "147@naver.com"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED ||
                checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                val permissions = arrayOf(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                requestPermissions(permissions, PERMISSION_STORAGE)
            }
        }

        binding.loginId.setText(App.prefs.myId)
    }

    private fun isLoadingView() {
        disposables.add(isLoading
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if(it) showProgress()
                else hideProgress()
            }
        )
    }

    private fun setViewModel() {
        loginVM.items.observe(this, Observer {
            startActivity(Intent(this, MainActivity::class.java))
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    // permission 에 대한 응답코드.
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    showToast("권한을 승인 하셔야 앱을 이용 할 수 있습니다.")
                    finishAffinity()
                }
            }
        }
    }

    // region onClickEvent
    fun onClickFindId() { startActivity(Intent(applicationContext, FindId::class.java)) }

    fun onClickFIndPw() { startActivity(Intent(applicationContext, FindPw::class.java)) }

    fun onClickSignUp() { startActivity(Intent(applicationContext, SignUpConfirmActivity::class.java)) }

    fun onClickSIgnUpKakao() {
        App.prefs.myId = ""
        App.prefs.mypassword = ""

        kakaoLogin(this)

        startActivity(Intent(applicationContext, KakaoConfirmActivity::class.java))
    }
    // endregion
}
