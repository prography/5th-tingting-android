package com.tingting.ver01.Model

import GetProfileResponse
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.example.tintint_jw.Model.Profile.SignUpKakaoRequest
import com.tingting.ver01.Model.Auth.CheckDuplicate.ID.DuplicateIdResponse
import com.tingting.ver01.Model.Auth.CheckDuplicate.Nickname.DuplicateNameResponse
import com.tingting.ver01.Model.Auth.Findidpw.*
import com.tingting.ver01.Model.Auth.Login.Kakao.LoginKakaoResponse
import com.tingting.ver01.Model.Auth.Login.Local.LoginLocalRequest
import com.tingting.ver01.Model.Auth.Login.Local.LoginLocalResponse
import com.tingting.ver01.Model.Auth.SignUp.SignUpRequest
import com.tingting.ver01.Model.Auth.SignUp.SignUpResponse
import com.tingting.ver01.Model.Auth.UploadThumnailResponse
import com.tingting.ver01.Model.Profile.PatchProfileResponse
import com.tingting.ver01.Model.Profile.PutProfile
import com.tingting.ver01.SharedPreference.App
import com.tingting.ver01.View.MainActivity
import com.tingting.ver01.View.PictureRegisterActivity
import com.tingting.ver01.View.SignUp.SignupActivity2
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class ModelSignUp(val context: Activity) {


    fun signUP( local_id:String,  password :String,  gender:String,
                name:String,  birth:String,  authenticated_address  : String,
                height:String, ac: Context) {



        val userRequest = SignUpRequest(local_id, password, gender, name, birth, authenticated_address, height)
        val call = RetrofitGenerator.create().SignUp(userRequest)

        call.enqueue(object :Callback<SignUpResponse>{
            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                t.printStackTrace()
                call.cancel()
            }

            override fun onResponse(
                call: Call<SignUpResponse>,
                response: Response<SignUpResponse>)
            {

                App.prefs.myToken = response.body()?.data?.token

                response.isSuccessful
                Log.d("TestValue",response.body()?.data?.token.toString())
                Log.d("TestValue",App.prefs.myToken.toString())
                Thread.sleep(1000)
                val intent = Intent(ac, SignupActivity2::class.java)

                val bundle = Bundle(1)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(ac,intent,bundle)
            }
        })
    }


    fun Login(pw: String, email: String , callback: IdCallBack) {

        val loginRequest = LoginLocalRequest(pw,email)
        val call = RetrofitGenerator.create().LoginLocal(loginRequest)

        call.enqueue(object: Callback<LoginLocalResponse> {
            override fun onFailure(call: Call<LoginLocalResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<LoginLocalResponse>,
                response: Response<LoginLocalResponse>) {

                if(response.isSuccessful){
                    callback.onSuccess("true")

                    App.prefs.myToken = response.body()!!.data.token

                }else{
                    callback.onSuccess("false")
                }

            }
        })

    }

    //Get My Profile
    fun getProfile(token: String, profile: ProfileCallBack) {

        val call = RetrofitGenerator.create().getProfile(token)

        call.enqueue(object : Callback<GetProfileResponse> {

            override fun onFailure(call: Call<GetProfileResponse>, t: Throwable) {
                t.printStackTrace()
                call.cancel()
            }

            override fun onResponse(
                call: Call<GetProfileResponse>,
                response: Response<GetProfileResponse>
            ) {

                var body: GetProfileResponse? = response.body()

                profile.onSuccess2(body!!.data.myInfo.name
                ,body!!.data.myInfo.birth
                ,body!!.data.myInfo.height.toString()
                ,body!!.data.myInfo.thumbnail
                ,body!!.data.myInfo.gender.toString()
                ,body!!.data.myInfo.schoolName
                ,body!!.data.myTeamList)

                //파싱한 데이터 Intent에 실어서 보내줘야 될듯.
            }
        })
    }

    //Modify My Profile
    fun putProfile(userName: String, birth: String, height: String, thumnail: String) {
        val PutProfile = PutProfile(userName, birth, height, thumnail)
        val call = RetrofitGenerator.create().putProfile(App.prefs.myToken.toString(), PutProfile)

        call.enqueue(object : Callback<PatchProfileResponse> {
            override fun onFailure(call: Call<PatchProfileResponse>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<PatchProfileResponse>,
                response: Response<PatchProfileResponse>
            ) {
                Log.d("ProfileDetailUdpate","프로필수정")
            }
        })
    }

    fun LoginKakao(id: String , callback: ProfileCallBack) {

        val call = RetrofitGenerator.create().LoginKakao(id)

        call.enqueue(object : Callback<LoginKakaoResponse> {

            override fun onFailure(call: Call<LoginKakaoResponse>, t: Throwable) {
                Toast.makeText(context, "카카오톡 로그인에 실패 하셨습니다.", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<LoginKakaoResponse>,
                response: Response<LoginKakaoResponse>
            ) {
                var a: LoginKakaoResponse? = response.body()

                if(a?.data?.message!=null){
                    callback.kakaoLogin("success")
                    //토큰 저장.
                    App.prefs.myToken = a!!.data.token
                }else{
                    callback.kakaoLogin("fail")
                }
            }
        })

    }

    fun CheckDuplicateId(local_id: String , callback:CodeCallBack): Boolean {

        val call = RetrofitGenerator.create().CheckDuplicateId(local_id)

        call.enqueue(object : Callback<DuplicateIdResponse> {
            override fun onFailure(call: Call<DuplicateIdResponse>, t: Throwable) {
                t.printStackTrace()
                call.cancel()

            }
            override fun onResponse(
                call: Call<DuplicateIdResponse>,
                response: Response<DuplicateIdResponse>
            ) {
                var code:Int = response.code()
                var value:String = response.body().toString()
                callback.onSuccess(code.toString(), value)

            }
        })
      return false
    }

    fun CheckDuplicateName(name: String, callback: CodeCallBack ) :Boolean {

        val call = RetrofitGenerator.create().CheckDuplicateName(name)

        var check = true

        call.enqueue(object : Callback<DuplicateNameResponse> {
            override fun onFailure(call: Call<DuplicateNameResponse>, t: Throwable) {
                t.printStackTrace()
                 check = false
            }

            override fun onResponse(
                call: Call<DuplicateNameResponse>,
                response: Response<DuplicateNameResponse>
            ) {
                var code:Int = response.code()
                var value:String = response.message()

                callback.onSuccess(code.toString(), value)
            }
        })
        return check
    }

    fun KakaoSignUp(name : String ,birth:String, height: String,
                    authenticated_address: String, gender: String  ,callback:ProfileCallBack,ac: Context) {
        val user = SignUpKakaoRequest(name, birth, height, authenticated_address, gender)
        val call = RetrofitGenerator.create().SignUpKakao(App.prefs.myKakaoToken.toString(), user)

        call.enqueue(object : Callback<LoginKakaoResponse> {
            override fun onFailure(call: Call<LoginKakaoResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<LoginKakaoResponse>,
                response: Response<LoginKakaoResponse>
            ) {
                App.prefs.myToken = response.body()?.data?.token

                Thread.sleep(1000)

                val intent = Intent(ac, PictureRegisterActivity::class.java)

                val bundle = Bundle(1)

                if (response.body()?.data?.message != null) {
                    callback.kakaoLogin("success")
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(ac,intent,bundle)
                } else {
                    callback.kakaoLogin("false")
                }
            }
        })
    }

    fun findId(email:String, back: CodeCallBack){
        var findIdRequest = FindIdRequest(email)
        val call = RetrofitGenerator.create().findId(findIdRequest)

        call.enqueue(object :Callback<FindIdResponse>{
            override fun onFailure(call: Call<FindIdResponse>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<FindIdResponse>,
                response: Response<FindIdResponse>
            ) {
                var code = response.code()
                var value = response.message().toString()
                back.onSuccess(code.toString(), value)
            }

        })
    }

    fun findPw(email:String, local_id:String, back:CodeCallBack){
        var findPwRequest = FindPwRequest(email, local_id)
        val call = RetrofitGenerator.create().findPw(findPwRequest)

        call.enqueue(object:Callback<FindPwResponse>{
            override fun onFailure(call: Call<FindPwResponse>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<FindPwResponse>,
                response: Response<FindPwResponse>
            ) {
                var code :Int = response.code()
                var value:String = response.message().toString()

                back.onSuccess(code.toString(), value)
            }

        })
    }

    fun resetPw(token:String, password:String, back:CodeCallBack){
        var resetPwRequest = ResetPwRequest(password)
        val call = RetrofitGenerator.create().resetPw(token, resetPwRequest)

        call.enqueue(object :Callback<ResetPwResponse>{
            override fun onFailure(call: Call<ResetPwResponse>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<ResetPwResponse>,
                response: Response<ResetPwResponse>
            ) {
                var code:Int = response.code()
                var value:String = response.message().toString()

                back.onSuccess(code.toString(), value)
            }

        })
    }

    fun uploadThumbnail(img : Uri, code :CodeCallBack){
        var file = File(img.toString())

        val fileReqBody = RequestBody.create(MediaType.parse("image/*"), file)
        val part = MultipartBody.Part.createFormData(
            "/api/v1/auth/thumbnail-img",
            file.name,
            fileReqBody
        )

//        val description = RequestBody.create(MediaType.parse("text/plain"), "")

        val description = RequestBody.create(MediaType.parse("text/plain"),
            "image-type");

        val call = RetrofitGenerator.create().uploadThumbnail(App.prefs.myToken.toString(),part,description  )

//        val call = RetrofitGenerator.create().uploadThumbnail(App.prefs.myToken.toString() )


        call.enqueue(object :Callback<UploadThumnailResponse>{
            override fun onFailure(call: Call<UploadThumnailResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<UploadThumnailResponse>, response: Response<UploadThumnailResponse>) {

            }
        })

    }
}