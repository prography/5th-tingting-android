package com.example.tintint_jw.new_package.util.extension

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.example.tintint_jw.new_package.common.Dlog
import com.example.tintint_jw.new_package.etc.KakaoSessionCallback
import com.kakao.auth.AuthType
import com.kakao.auth.Session
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.LogoutResponseCallback

fun AppCompatActivity.kakaoLogin(activity: Activity) {
    val session = Session.getCurrentSession()
    session.addCallback(KakaoSessionCallback(this))
    val checkKakao = session.checkAndImplicitOpen()
    Dlog.i("kakao Login checkKakao = $checkKakao")
    if(!checkKakao)
        session.open(AuthType.KAKAO_LOGIN_ALL, activity)
}

fun kakaoLogout() {
    UserManagement.getInstance().requestLogout(object : LogoutResponseCallback() {
        // 로그아웃을 성공한 경우 불립니다. 서버에 로그아웃 도달과 무관하게 항상 성공을 하며, 예제에서는 로그인 창으로 이동시킵니다.
        override fun onCompleteLogout() {
            Dlog.i("kakao_log_out")
        }
    })
}