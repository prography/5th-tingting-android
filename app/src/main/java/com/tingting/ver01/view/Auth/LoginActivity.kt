package com.tingting.ver01.view.Auth

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.analytics.FirebaseAnalytics
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.tingting.ver01.R
import com.tingting.ver01.databinding.ActivityLoginBinding
import com.tingting.ver01.model.ModelSignUp
import com.tingting.ver01.sharedPreference.App
import com.tingting.ver01.view.Auth.FindIdAndPw.FindAccount
import com.tingting.ver01.view.Main.MainActivity
import com.tingting.ver01.view.SignUp.SignUpConfirmActivity
import com.tingting.ver01.viewModel.LoginActivityViewModel
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {


    var model: ModelSignUp = ModelSignUp(this)
    var check = false
    lateinit var dataBinding: com.tingting.ver01.databinding.ActivityLoginBinding
    private lateinit var firebaseAnalytics: FirebaseAnalytics



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        dataBinding.viewmodel = ViewModelProviders.of(this).get(LoginActivityViewModel::class.java)

        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, App.prefs.mylocal_id.toString())
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "name")

        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)


        //auto login part
        autoLogin()


        signIn.setOnClickListener {

            Log.d("LoginId", dataBinding.loginId.text.toString())
            Log.d("LoginPw", dataBinding.loginPw.text.toString())
            App.prefs.myautoLogin = "true"
            App.prefs.myLoginType = "local"

         var number =    dataBinding.viewmodel?.login(
                this@LoginActivity,
                dataBinding.loginPw.text.toString(),
             dataBinding.loginId.text.toString()
            )

        }

        findAccount.setOnClickListener {
            val intent = Intent(applicationContext, FindAccount::class.java)
            startActivity(intent)
        }

        signUp.setOnClickListener {
            val intent = Intent(applicationContext, SignUpConfirmActivity::class.java)
            App.prefs.myLoginType = "local"
            startActivity(intent)
        }

        //이용약관
        systemAgree()

        loginId.setText(App.prefs.mylocal_id)

        // 카카오톡 로그인 코드

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e("testkakao", "로그인 실패", error)
            }
            else if (token != null) {

                Log.d("testKakaoToken!!!!", token.accessToken.toString())

                App.prefs.myKakaoToken = token.accessToken

                App.prefs.myisMaking = "true"
                App.prefs.myLoginType = "kakao"

                dataBinding.viewmodel?.loginKakao(this)
            }
        }

        signUpKakao.setOnClickListener {

            App.prefs.myautoLogin = "true"


            LoginClient.instance.loginWithKakaoAccount(this) { token, error ->

                if (LoginClient.instance.isKakaoTalkLoginAvailable(this)) {

                    LoginClient.instance.loginWithKakaoTalk(this, callback = callback)

                } else {
                    LoginClient.instance.loginWithKakaoAccount(this, callback = callback)
                }
            }

        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {


        super.onActivityResult(requestCode, resultCode, data)
    }

    //세션 연결을 끊는 코드
    override fun onDestroy() {

        super.onDestroy()
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

    fun checkEmptyField(
        loginId: EditText

    ): Boolean {
        if (loginId.text.toString().length == 0) {
            Toast.makeText(applicationContext, "아이디를 입력해주세요.", Toast.LENGTH_LONG).show()
            return false
        }
        return true

    }

    fun autoLogin(){

        if(App.prefs.myautoLogin.equals("true")){
            if(App.prefs.myLoginType.equals("kakao")){
                dataBinding.viewmodel?.loginKakao(this)
            }else{
                dataBinding.viewmodel?.login(this,App.prefs.mypassword.toString(),App.prefs.myId.toString())
            }
        }

    }
    fun systemAgree() {

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
                )

                //show popup to request runtime permission
                requestPermissions(permissions, 1000)

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
                val permissions = arrayOf(android.Manifest.permission.INTERNET)
                //show popup to request runtime permissions
                requestPermissions(permissions, 1000)

            }

        }
    }



}
