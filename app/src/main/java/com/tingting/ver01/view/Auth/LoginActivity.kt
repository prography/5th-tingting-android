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
import com.kakao.auth.AuthType
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.StringSet.name
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.util.exception.KakaoException
import com.tingting.ver01.R
import com.tingting.ver01.databinding.ActivityLoginBinding
import com.tingting.ver01.model.ModelSignUp
import com.tingting.ver01.sharedPreference.App
import com.tingting.ver01.socket.SocketListener
import com.tingting.ver01.view.Auth.FindIdAndPw.FindAccount
import com.tingting.ver01.view.SignUp.SignUpConfirmActivity
import com.tingting.ver01.viewModel.LoginActivityViewModel
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.android.synthetic.main.activity_login.*
import java.net.URISyntaxException


class LoginActivity : AppCompatActivity() {


    var callback: SessionCallback = SessionCallback()
    var model: ModelSignUp = ModelSignUp(this)
    var check = false
    lateinit var dataBinding: ActivityLoginBinding
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    companion object{
        var msocket = IO.socket("http://13.209.77.221");
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        socketConnect()


        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        dataBinding.viewmodel = ViewModelProviders.of(this).get(LoginActivityViewModel::class.java)

        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, App.prefs.mylocal_id.toString())
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name)
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
        signUpKakao.setOnClickListener {
            App.prefs.myautoLogin = "true"
            Session.getCurrentSession().addCallback(callback)
            Session.getCurrentSession().open(AuthType.KAKAO_LOGIN_ALL, this)
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
        Session.getCurrentSession().removeCallback(callback)
        super.onDestroy()
    }


    inner class SessionCallback : ISessionCallback {
        // 세션이 열림.
        // 개인 정보를 서버에 보내주는 코드 작성 필요.
        open override fun onSessionOpened() {

            UserManagement.getInstance().me(object : MeV2ResponseCallback() {

                override fun onFailure(errorResult: ErrorResult?) {}

                override fun onSessionClosed(errorResult: ErrorResult?) {}

                override fun onSuccess(result: MeV2Response?) {

                    try {
                        dataBinding.viewmodel?.loginKakao(this@LoginActivity)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            })
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

    fun socketConnect(){

        try{
            //socket 생성 및 연결 // 주소 들어가야 함.!

            var so = SocketListener()

            msocket.connect()
            msocket.on(Socket.EVENT_CONNECT,so.onConnect)
            Log.d("socketConnect","connect1")


//            var data   = JsonObject()
//            data.addProperty("userId" ,"1")
//            msocket.emit("enroll", data)

        }catch (e:URISyntaxException){

        }


    }

}
