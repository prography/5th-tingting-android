package com.example.tintint_jw.View

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.tintint_jw.FindIdAndPw.FindId
import com.example.tintint_jw.FindIdAndPw.FindPw
import com.example.tintint_jw.Model.ModelMain
import com.example.tintint_jw.SharedPreference.SharedPreference
import kotlinx.android.synthetic.main.activity_login.*
import java.util.jar.Manifest


class LoginActivity : AppCompatActivity() {

    //private var callback :SessionCallback = SessionCallback()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.tintint_jw.R.layout.activity_login)
        val prefs : SharedPreference = SharedPreference(this)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            //permission이 허용되어 있지 않은 상태라면
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_DENIED || checkSelfPermission(android.Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_DENIED){
                //permission denied

                //permission already granted
                //permission 상태창을 보여줌

                val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.READ_CONTACTS);

                //show popup to request runtime permission
                requestPermissions(permissions, 1000);

            }

        }

      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            //permission이 허용되어 있지 않은 상태라면
            if (checkSelfPermission(android.Manifest.permission.INTERNET) ==
                PackageManager.PERMISSION_DENIED){
                //permission denied

                //permission already granted
                //permission 상태창을 보여줌
      ;
                val permissions = arrayOf(android.Manifest.permission.INTERNET);
                //show popup to request runtime permission
                requestPermissions(permissions, 1000);

            }

        }*/



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


        Findid.setOnClickListener(){
            val intent = Intent(applicationContext, FindId::class.java)
            startActivity(intent)

        }

        Findpw.setOnClickListener(){
            val intent = Intent(applicationContext, FindPw::class.java)
            startActivity(intent)
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

    //permission에 대한 응답코드.
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            1000 -> {


                if (grantResults.size >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                }
                else{
                    //permission from popup denied
                    Toast.makeText(this,"권한을 승인 하셔야 앱을 이용 할 수 있습니다.",Toast.LENGTH_LONG).show()
                    finishAffinity()
                }
            }
        }
    }

}
