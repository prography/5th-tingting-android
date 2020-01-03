package com.example.tintint_jw.Model

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.example.tintint_jw.Model.Auth.CheckDuplicate.ID.DuplicateIdResponse
import com.example.tintint_jw.Model.Auth.CheckDuplicate.Nickname.DuplicateNameResponse
import com.example.tintint_jw.Model.Auth.Login.Kakao.LoginKakaoRequest
import com.example.tintint_jw.Model.Auth.Login.Kakao.LoginKakaoResponse
import com.example.tintint_jw.Model.Auth.Login.Local.LoginLocalRequest
import com.example.tintint_jw.Model.Auth.Login.Local.LoginLocalResponse
import com.example.tintint_jw.Model.Auth.School.*
import com.example.tintint_jw.Model.Auth.SignUp.SignUpRequest
import com.example.tintint_jw.Model.Auth.SignUp.SignUpResponse
import com.example.tintint_jw.Model.Profile.GetProfile
import com.example.tintint_jw.Model.Profile.GetProfileResponse
import com.example.tintint_jw.Model.Profile.PutProfile
import com.example.tintint_jw.SharedPreference.App
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModelSignUp(val context: Activity) {



    fun signUP( local_id:String,  password :String,  gender:String,
                name:String,  birth:String,  thumbnail:String,  authenticated_email : String,  height:String) {
        val userRequest = SignUpRequest(local_id, password, gender, name, birth, thumbnail, authenticated_email, height)
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
                response.isSuccessful

            }
        })
    }


    fun Login(email: String, pw: String , callback: IdCallBack) {

        val loginRequest = LoginLocalRequest(email, pw)
        val call = RetrofitGenerator.create().LoginLocal(loginRequest)

        call.enqueue(object: Callback<LoginLocalResponse> {
            override fun onFailure(call: Call<LoginLocalResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<LoginLocalResponse>,
                response: Response<LoginLocalResponse>
            ) {

            }
        })

    }

    //Get My Profile
    fun getProfile(token: String) {
        val GetProfile = GetProfile(token)
        val call = RetrofitGenerator.create().getProfile(GetProfile)

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
                //파싱한 데이터 Intent에 실어서 보내줘야 될듯.
            }
        })
    }

    //Modify My Profile
    fun putProfile(userName: String, birth: String, height: String, thumnail: String) {
        val PutProfile = PutProfile(userName, birth, height, thumnail)
        val call = RetrofitGenerator.create().putProfile(PutProfile)

        call.enqueue(object : Callback<GetProfileResponse> {

            override fun onFailure(call: Call<GetProfileResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(context, "프로필을 업데이트하는데 실패했습니다.", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<GetProfileResponse>,
                response: Response<GetProfileResponse>
            ) {
                Toast.makeText(context, "업데이트 하는데 성공했습니다.", Toast.LENGTH_LONG).show()

                var body: GetProfileResponse? = response.body()
                //파싱한 데이터 Intent에 실어서 보내줘야 될듯.
            }
        })
    }

    fun SchoolAuth(name: String, email: String) {

        val schoolAuthRequest = SchoolAuthRequest(name, email)
        val call = RetrofitGenerator.create().SchoolAuth(schoolAuthRequest)

        call.enqueue(object : Callback<SchoolAuthResponse> {
            override fun onFailure(call: Call<SchoolAuthResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(context, "이미 가입된 이메일이거나 가입이 불가능한 이메일입니다.", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<SchoolAuthResponse>,
                response: Response<SchoolAuthResponse>
            ) {
                Log.d("response", response.toString())
                var a: SchoolAuthResponse? = response.body()

                Log.d("response2", response.message().toString())


            }
        })

    }

    fun SchoolAuthConfirm(token: String) {
        val schoolAuthConfirmRequest = SchoolAuthConfirmRequest(token)
        val call = RetrofitGenerator.create().SchoolAuthConfirm(schoolAuthConfirmRequest)

        call.enqueue(object : Callback<SchoolAuthConfirmResponse> {
            override fun onFailure(call: Call<SchoolAuthConfirmResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(context, "이메일 인증에 실패하였습니다.", Toast.LENGTH_LONG).show()

            }

            override fun onResponse(
                call: Call<SchoolAuthConfirmResponse>,
                response: Response<SchoolAuthConfirmResponse>
            ) {
                Log.d("response", response.toString())
                var a: SchoolAuthConfirmResponse? = response.body()
                Log.d("response2", response.message().toString())
            }
        })
    }

    fun SchoolAuthComplete(email: String) {
        val schoolAuthCompleteRequest = SchoolCompleteRequest(email)
        val call = RetrofitGenerator.create().SchoolAuthComplete(schoolAuthCompleteRequest)

        call.enqueue(object : Callback<SchoolCompleteResponse> {
            override fun onFailure(call: Call<SchoolCompleteResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(context, "인증이 필요한 이메일입니다.", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<SchoolCompleteResponse>,
                response: Response<SchoolCompleteResponse>
            ) {
                Log.d("response", response.toString())
                var a: SchoolCompleteResponse? = response.body()
                Log.d("response2", response.message().toString())
            }
        })
    }

    fun LoginKakao(id: String) {
        val kakaoRequest = LoginKakaoRequest(id)
        val call = RetrofitGenerator.create().LoginKakao()

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

                //토큰 저장.
                App.prefs.myToken = a.toString()

            }
        })

    }

    fun CheckDuplicateId(local_id: String , callback:IdCallBack): Boolean {

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

                var a: DuplicateIdResponse? = response.body()
                callback.onSuccess(response.body().toString())
                Log.d("ModelMain111",a.toString())

            }
        })
      return false
    }

    fun CheckDuplicateName(name: String, callback: IdCallBack ) :Boolean {

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

                var a: DuplicateNameResponse? = response.body()

                callback.onSuccess(response.isSuccessful.toString())
            }
        })
        return check
    }

}