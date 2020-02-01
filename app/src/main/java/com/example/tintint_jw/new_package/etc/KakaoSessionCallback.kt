package com.example.tintint_jw.new_package.etc

import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.tintint_jw.SharedPreference.App
import com.example.tintint_jw.new_package.common.Dlog
import com.example.tintint_jw.view.SignUp.SignUpActivity2
import com.kakao.auth.ISessionCallback
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.util.exception.KakaoException

class KakaoSessionCallback(val activity: AppCompatActivity) : ISessionCallback {
    // 세션이 열림.
    // 개인 정보를 서버에 보내주는 코드 작성 필요.
    override fun onSessionOpened() {
        requestMe()
    }

    //세션이 실패했을 때
    override fun onSessionOpenFailed(exception: KakaoException?) {
        Dlog.e("KakaoSessionCallback onSessionOpenFailed : ${exception?.message}")
    }

    // 사용자 정보 요청
    private fun requestMe() {
        Dlog.e("KakaoSessionCallback requestMe")

        val keys = ArrayList<String>()
        keys.add("properties.nickname")
        keys.add("properties.profile_image")
        keys.add("kakao_account.email")

        // 사용자정보 요청 결과에 대한 Callback
        UserManagement.getInstance().me(keys, object : MeV2ResponseCallback() {
            // 사용자 정보 요청이 성공한 경우로 사용자 정보 객체를 받습니다.
            override fun onSuccess(response: MeV2Response?) {
                /**
                 * name - type - description
                 * id - Long - 앱 유저 아이디
                 * hasSignedUp - OptionalBoolean - 유저의 앱 가입 여부      << 왜 없지?
                 * kakaoAccount - UserAccount - 유저의 카카오 게정에 관한 정보
                 * properties - Map - 유저의 앱별 정보 (nickname, thumbnail_image, profile_image 등등). *Properties 참고
                 * forPartners - JSONObject - 카카오 파트너사들에게 제공되는 정보 (uuid, serviceUserId 등)
                 */

                /**
                 * *Properties
                 * name - type - description
                 * nickname - String - 서비스별 닉네임. 기본값은 앱연결시의 카카오계정 닉네임
                 * profile_image - String - 서비스별 프로필 이미지 URL. 기본값은 앱연결시의 카카오계정 프로필 이미지 URL (640px * 640px)
                 * thumbnail_image - String - 서비스별 썸네일 이미지 URL. 기본값은 앱연결시의 카카오계정 썸네일 프로필 이미지 URL (110px * 110px)
                 */
                Dlog.i("KakaoSessionCallback onSuccess response = $response\n${response?.kakaoAccount?.emailNeedsAgreement()?.boolean}")

//                // 이메일을 얻기 위해 추가 동의가 필요한지에 대한 여부
//                if(response.kakaoAccount.emailNeedsAgreement().boolean) {
//                    handleScopeError(response.kakaoAccount)
//                    retrn
//                }

                response?.let {
                    try {
                        App.prefs.mythumnail = it.kakaoAccount.profile.profileImageUrl.toString()
                    } catch (e: NullPointerException) {
                        App.prefs.mythumnail = ""
                    }

                    App.prefs.myId = it.id.toString()
                    App.prefs.mylocal_id = it.id.toString()
                    redirectSignUpActivity()

                    App.prefs.mythumnail = it.kakaoAccount.profile.profileImageUrl.toString()
                    App.prefs.myId = it.id.toString()
                    App.prefs.mylocal_id = it.id.toString()
                    redirectSignUpActivity()
                }
            }

            // 세션이 닫혀 실패한 경우로 에러 결과를 받습니다. 재로그인 / 토큰발급이 필요합니다.
            override fun onSessionClosed(errorResult: ErrorResult) {
                Log.e("Session onSessionClosed", errorResult?.errorMessage.toString())
            }

//            // 미가입 상태에서도 호출 가능하기 때문에 더이상 불리지 않음
//            override fun onNotSignedUp() {
//                Dlog.e("KakaoSessionCallback onNotSignedUp")
//                reDirectLoginActivity()
//            }

            // 가입이 안된 경우와 세션이 닫힌 경우를 제외한 다른 이유로 요청이 실패한 경우의 콜백입니다
            override fun onFailure(errorResult: ErrorResult?) {
                Log.d("Session Call on failed", errorResult?.errorMessage.toString())
            }
        })
    }

    fun redirectSignUpActivity() {
        activity.startActivity(Intent(activity, SignUpActivity2::class.java))
        activity.finish()
    }
}