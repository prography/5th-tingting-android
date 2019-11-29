package com.example.tintint_jw.View

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tintint_jw.Model.ModelMain
import com.example.tintint_jw.SharedPreference.SharedPreference
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    //private var callback :SessionCallback = SessionCallback()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.tintint_jw.R.layout.activity_login)
        val prefs : SharedPreference = SharedPreference(this)


        if((prefs!!.myId=="서버로 부터 불러온 아이디") && (prefs!!.myPw=="서버로 부터 불러온 PW")){
            val intent = Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)
        }

        signIn.setOnClickListener(){

            ModelMain(this).Login(loginId.text.toString(),loginPw.text.toString())

            prefs.myId = loginId.text.toString()
            prefs.myPw = loginPw.text.toString()


        //    if(loginId.text.equals("서버로 부터 불러 온 id") && loginPw.text.equals("서버로 부터 불러 온 pw")){

       //     }
        }




        signUp.setOnClickListener(){
            val intent = Intent(applicationContext,SignUpActivity::class.java)
            startActivity(intent)
        }

        /*signUpKakao.setOnClickListener(){
            Session.getCurrentSession().addCallback(callback)
            Session.getCurrentSession().open(AuthType.KAKAO_LOGIN_ALL, this);
        }*/
    }


    /*override fun onDestroy() {
        Session.getCurrentSession().removeCallback(callback);
        super.onDestroy()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    open fun redirectSignUpActivity() {
        val intent = Intent(applicationContext ,SignUpActivity::class.java)
        startActivity(intent)
        finish()

    }

    inner class SessionCallback : ISessionCallback {
         // 세션이 열림.
         open override fun onSessionOpened() {
             UserManagement.getInstance().me(object : MeV2ResponseCallback() {
                 override fun onFailure(errorResult: ErrorResult?) {
                     Log.d("Session Call on failed", errorResult?.errorMessage.toString())
                 }

                 override fun onSessionClosed(errorResult: ErrorResult?) {
                     Log.e("Session onSessionClosed", errorResult?.errorMessage.toString())

                 }

                 override fun onSuccess(result: MeV2Response?) {
                     redirectSignUpActivity()
                 }
             })
         }
        //세션이 실패했을 때
        override fun onSessionOpenFailed(exception: KakaoException?) {
        }
     }*/
}
