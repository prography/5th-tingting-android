package com.tingting.ver01.View

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Base64.NO_WRAP
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.kakao.auth.AuthType
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.StringSet.name
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.util.exception.KakaoException
import com.kakao.util.helper.Utility.getPackageInfo
import com.tingting.ver01.FindIdAndPw.FindAccount
import com.tingting.ver01.Model.IdCallBack
import com.tingting.ver01.Model.ModelSignUp
import com.tingting.ver01.Model.ProfileCallBack
import com.tingting.ver01.SharedPreference.App
import com.tingting.ver01.SharedPreference.SharedPreference
import com.tingting.ver01.View.SignUp.SignUpConfirmActivity
import kotlinx.android.synthetic.main.activity_login.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class LoginActivity : AppCompatActivity() {


    var callback: SessionCallback = SessionCallback()
    var model: ModelSignUp = ModelSignUp(this)
    var check = false
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.tingting.ver01.R.layout.activity_login)
        val prefs : SharedPreference = SharedPreference(this)
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, App.prefs.mylocal_id.toString())
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name)
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //permission이 허용되어 있지 않은 상태라면
            if (checkSelfPermission(android.Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_DENIED || checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
            ) {
                //permission denied

                //permission already granted
                //permission 상태창을 보여줌

                val permissions = arrayOf(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_CONTACTS,
                    android.Manifest.permission.CAMERA
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
        Log.d("HashValue",getHashKey(this).toString())
        //자동로그인 파트
        if(App.prefs.loginType.equals("카카오")){
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }else{
            ModelSignUp(this).Login(
                App.prefs.mypassword.toString(),
                App.prefs.myId.toString(),
                object : IdCallBack {
                    override fun onSuccess(value: String) {
                        super.onSuccess(value)
                        val s = App.prefs.myautoLogin

                        if (value.equals("true") && s.equals("true")) {
                            val intent = Intent(applicationContext, MainActivity::class.java)
                            startActivity(intent)
                        }
                    }
                })
        }


        loginId.setText(App.prefs.mylocal_id)

        signIn.setOnClickListener() {

            model.Login(loginPw.text.toString(), loginId.text.toString(), object : IdCallBack {
                override fun onSuccess(value: String) {
                    super.onSuccess(value)
                    if (value.equals("true") && checkEmptyField(loginId)) {
                        App.prefs.myId = loginId.text.toString()
                        App.prefs.mypassword = loginPw.text.toString()
                        App.prefs.myautoLogin = "true"
                        var intent: Intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)

                    } else {
                        Toast.makeText(applicationContext, "잘못된 계정입니다.", Toast.LENGTH_LONG)
                            .show()
                    }

                }

            })

            //    if(loginId.text.equals("서버로 부터 불러 온 id") && loginPw.text.equals("서버로 부터 불러 온 pw")){

            //     }
        }


        findAccount.setOnClickListener {
            val intent = Intent(applicationContext, FindAccount::class.java)
            startActivity(intent)
        }
        /*Findid.setOnClickListener() {
            val intent = Intent(applicationContext, FindAccount::class.java)
            startActivity(intent)

        }

        Findpw.setOnClickListener() {
            val intent = Intent(applicationContext, FindPw::class.java)
            startActivity(intent)
        }*/

        signUp.setOnClickListener() {
            val intent = Intent(applicationContext, SignUpConfirmActivity::class.java)
            App.prefs.myLoginType="local"
            startActivity(intent)
        }

        // 카카오톡 로그인 코드
        //
        signUpKakao.setOnClickListener() {

            Session.getCurrentSession().addCallback(callback)
            Session.getCurrentSession().open(AuthType.KAKAO_TALK_ONLY, this)

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            App.prefs.myisMaking = "true"
            App.prefs.myLoginType = "kakao"

            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    //세션 연결을 끊는 코드
    override fun onDestroy() {
        Session.getCurrentSession().removeCallback(callback);
        super.onDestroy()
    }


    fun redirectSignUpActivity() {
        val intent = Intent(applicationContext, SchoolAuthActivity::class.java)
        startActivity(intent)
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

                    var a  = Session.getCurrentSession().accessToken.toString()
                    App.prefs.myKakaoToken = a

                    App.prefs.myId = result!!.id.toString()
                    App.prefs.mythumnail = result!!.kakaoAccount.profile.thumbnailImageUrl.toString()

                    Log.d("kakaoAccountProfiel",result!!.kakaoAccount.profile.toString())

                    try {
                        model.LoginKakao(a, object : ProfileCallBack {
                            override fun kakaoLogin(success: String) {
                                super.kakaoLogin(success)

                                if (success.equals("success")) {
                                    val intent = Intent(applicationContext, MainActivity::class.java)
                                    startActivity(intent)
                                } else {
                                    if (App.prefs.myisMaking.equals("true")) {
                                        redirectSignUpActivity()
                                    }

                                }
                            }
                        })
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    //가입을 하다가 중단된 경우 다시 진입 할 수 있게 해주어야함.

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

    //카카오 키 값을 얻어와야 하기 때문에 삭제하면 안됩니다.
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

    fun checkEmptyField(
        loginId: EditText

    ): Boolean {
        if (loginId.text.toString().length == 0) {
            Toast.makeText(applicationContext, "아이디를 입력해주세요.", Toast.LENGTH_LONG).show();
            return false
        }
        return true

    }

}
