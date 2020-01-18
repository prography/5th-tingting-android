package com.example.tintint_jw.View

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Base64.NO_WRAP
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tintint_jw.FindIdAndPw.FindId
import com.example.tintint_jw.FindIdAndPw.FindPw
import com.example.tintint_jw.Model.IdCallBack
import com.example.tintint_jw.Model.ModelSignUp
import com.example.tintint_jw.SharedPreference.App
import com.example.tintint_jw.SharedPreference.SharedPreference
import com.example.tintint_jw.View.SignUp.SignUpActivity2
import com.example.tintint_jw.View.SignUp.SignupActivity1
import com.kakao.auth.AuthType
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.util.exception.KakaoException
import com.kakao.util.helper.Utility.getPackageInfo
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.loginId

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.tintint_jw.View.view_model.LoginViewModel
import com.kakao.usermgmt.response.model.Profile
import java.lang.NullPointerException
import com.example.tintint_jw.View.SignUp.SignUpConfirmActivity


class LoginActivity : AppCompatActivity() {


    var callback: SessionCallback = SessionCallback()
    var model: ModelSignUp = ModelSignUp(this)
    private val loginVM by lazy {
        ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.tintint_jw.R.layout.activity_login)
        val prefs: SharedPreference = SharedPreference(this)

        App.prefs.myauthenticated_address = "147@naver.com"

        Log.d("hash", getHashKey(this).toString())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //permission이 허용되어 있지 않은 상태라면
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_DENIED || checkSelfPermission(android.Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_DENIED
            ) {
                //permission denied

                //permission already granted
                //permission 상태창을 보여줌

                val permissions = arrayOf(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_CONTACTS
                );

                //show popup to request runtime permission
                requestPermissions(permissions, 1000);

            }

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //permission이 허용되어 있지 않은 상태라면
            if (checkSelfPermission(android.Manifest.permission.INTERNET) ==
                PackageManager.PERMISSION_DENIED
            ) {
                //permission denied

                //permission already granted
                //permission 상태창을 보여줌
                ;
                val permissions = arrayOf(android.Manifest.permission.INTERNET);
                //show popup to request runtime permission
                requestPermissions(permissions, 1000);

            }

        }

        //자동로그인 파트
        /*     ModelSignUp(this).Login(App.prefs.mypassword.toString(),App.prefs.myId.toString(), object :IdCallBack{
                     override fun onSuccess(value: String) {
                         super.onSuccess(value)
                         val s = App.prefs.myautoLogin
                         if(value.equals("true") && s.equals("true")){
                         val intent = Intent(applicationContext,MainActivity::class.java)
                         startActivity(intent)
                         }
                     }
                 })
     */

        loginId.setText(App.prefs.myId)

        signIn.setOnClickListener() {

            ModelSignUp(this).Login(
                loginPw.text.toString(),
                loginId.text.toString(),
                object : IdCallBack {
                    override fun onSuccess(value: String) {
                        super.onSuccess(value)
                        if (value.equals("true")) {
                            App.prefs.myId = loginId.text.toString()
                            App.prefs.mypassword = loginPw.text.toString()
                            App.prefs.myautoLogin = "true"
                            var intent: Intent =
                                Intent(applicationContext, MainActivity::class.java)
                            startActivity(intent)

                        } else {
                            Toast.makeText(applicationContext, "일치하는 아이디가 없습니다.", Toast.LENGTH_LONG)
                                .show()
                        }
                    }

                })


            App.prefs.myId = loginId.text.toString()
            App.prefs.myPw = loginPw.text.toString()

            //    if(loginId.text.equals("서버로 부터 불러 온 id") && loginPw.text.equals("서버로 부터 불러 온 pw")){

            //     }
        }


        Findid.setOnClickListener() {
            val intent = Intent(applicationContext, FindId::class.java)
            startActivity(intent)

        }

        Findpw.setOnClickListener() {
            val intent = Intent(applicationContext, FindPw::class.java)
            startActivity(intent)
        }

        signUp.setOnClickListener(){
            val intent = Intent(applicationContext,
                SignUpConfirmActivity::class.java)
            startActivity(intent)
        }

        // 카카오톡 로그인 코드
        //
        signUpKakao.setOnClickListener() {
            App.prefs.myId = ""
            App.prefs.mypassword = ""
            Session.getCurrentSession().addCallback(callback)
            Session.getCurrentSession().open(AuthType.KAKAO_TALK_ONLY, this);

            val intent = Intent(applicationContext, KakaoConfirmActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    //세션 연결을 끊는 코드
    override fun onDestroy() {
        Session.getCurrentSession().removeCallback(callback);
        super.onDestroy()
    }


    open fun redirectSignUpActivity() {
        val intent = Intent(
            applicationContext,
            SignUpActivity2::class.java
        )
        startActivity(intent)
        finish()

    }

    inner class SessionCallback : ISessionCallback {
        // 세션이 열림.
        // 개인 정보를 서버에 보내주는 코드 작성 필요.
        open override fun onSessionOpened() {

            UserManagement.getInstance().me(object : MeV2ResponseCallback() {
                override fun onFailure(errorResult: ErrorResult?) {
                    Log.d("Session Call on failed", errorResult?.errorMessage.toString())
                }

                override fun onSessionClosed(errorResult: ErrorResult?) {
                    Log.e("Session onSessionClosed", errorResult?.errorMessage.toString())

                }

                override fun onSuccess(result: MeV2Response?) {
                    Log.d("Session is success", result.toString())
                         try{
                             App.prefs.mythumnail= result!!.kakaoAccount.profile.profileImageUrl.toString()
                         }catch (e:NullPointerException){
                             App.prefs.mythumnail = ""
                         }

                         App.prefs.myId = result!!.id.toString()
                         App.prefs.mylocal_id = result!!.id.toString()
                         redirectSignUpActivity()

                    App.prefs.mythumnail = result!!.kakaoAccount.profile.profileImageUrl.toString()
                    App.prefs.myId = result!!.id.toString()
                    App.prefs.mylocal_id = result!!.id.toString()
                    redirectSignUpActivity()

                }
            })
            //함수 실행해서 토큰 값 sharedprference에 저장.

        }

        //세션이 실패했을 때
        override fun onSessionOpenFailed(exception: KakaoException?) {

        }

    }


    //permission에 대한 응답코드.
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            1000 -> {

                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission from popup granted
                } else {
                    //permission from popup denied
                    Toast.makeText(this, "권한을 승인 하셔야 앱을 이용 할 수 있습니다.", Toast.LENGTH_LONG).show()
                    finishAffinity()
                }
            }
        }
    }


    fun getHashKey(context: Context): String? {
        try {
            if (Build.VERSION.SDK_INT >= 28) {
                val packageInfo = getPackageInfo(context, PackageManager.GET_SIGNING_CERTIFICATES)
                val signatures = packageInfo.signingInfo.apkContentsSigners
                val md = MessageDigest.getInstance("SHA")
                for (signature in signatures) {
                    md.update(signature.toByteArray())
                    return String(Base64.encode(md.digest(), NO_WRAP))
                }
            } else {
                val packageInfo =
                    getPackageInfo(context, PackageManager.GET_SIGNATURES) ?: return null

                for (signature in packageInfo!!.signatures) {
                    try {
                        val md = MessageDigest.getInstance("SHA")
                        md.update(signature.toByteArray())
                        return Base64.encodeToString(md.digest(), Base64.NO_WRAP)
                    } catch (e: NoSuchAlgorithmException) {


                    }
                }
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return null
    }
}
