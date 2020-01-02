package com.example.tintint_jw.Model

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.tintint_jw.Model.Profile.GetProfile
import com.example.tintint_jw.Model.Profile.GetProfileResponse
import com.example.tintint_jw.Model.Profile.PutProfile
import com.example.tintint_jw.Model.Auth.CheckDuplicate.ID.DuplicateIdRequest
import com.example.tintint_jw.Model.Auth.CheckDuplicate.ID.DuplicateIdResponse
import com.example.tintint_jw.Model.Auth.CheckDuplicate.Nickname.DuplicateNameRequest
import com.example.tintint_jw.Model.Auth.CheckDuplicate.Nickname.DuplicateNameResponse
import com.example.tintint_jw.Model.Auth.School.*
import com.example.tintint_jw.SharedPreference.App
import com.example.tintint_jw.SharedPreference.SharedPreference
import com.example.tintint_jw.View.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ModelMain(var context: Activity) :Application() {

    val prefs : SharedPreference = SharedPreference(context)
    // applicaiton을 상속받아서 그 context 값을 넣어줘야함. object

    fun signUP(email: String, pw : String, pwCheck:String ){
        val userRequest = RegisterUserRequest(email,pw,pwCheck)
        val call = RetrofitGenerator.create().registerUser(userRequest)

        call.enqueue(object : Callback<RegisterUserRequest>{
            override fun onFailure(call: Call<RegisterUserRequest>, t: Throwable) {
                 Log.d("testModel",t.toString())
                t.printStackTrace()
            }
            override fun onResponse(
                call: Call<RegisterUserRequest>,
                response: Response<RegisterUserRequest>
            ) {

                   Log.d("testModel",response.toString())
            }
        });
    }


    fun Login(email :String, pw: String)  {

        val loginRequest = LoginRequest(email,pw)
        val call = RetrofitGenerator.create().login(loginRequest)

        call.enqueue(object : Callback<LoginResponse>{
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                t.printStackTrace()
                 Toast.makeText(context,"가입하지 않은 아이디이거나, 잘못된 비밀번호입니다.",Toast.LENGTH_LONG).show()
            }
            override fun onResponse(call: Call<LoginResponse >, response: Response<LoginResponse>) {
                Log.d("response",response.toString())
                var a : LoginResponse? = response.body()
                Log.d("response2",response.message().toString())

            }
        });
    }

    //Get My Profile
    fun getProfile(token:String){
        val GetProfile = GetProfile(token)
        val call = RetrofitGenerator.create().getProfile(GetProfile)

        call.enqueue(object  : Callback<GetProfileResponse>{

            override fun onFailure(call: Call<GetProfileResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(context,"프로필을 불러오는데 실패했습니다.",Toast.LENGTH_LONG).show()
            }
            override fun onResponse(
                call: Call<GetProfileResponse>,
                response: Response<GetProfileResponse>
            ) {
               var body :GetProfileResponse? = response.body()
                //파싱한 데이터 Intent에 실어서 보내줘야 될듯.
            }
        })
    }

    //Modify My Profile
    fun putProfile(userName: String ,birth:String, height:String, thumnail:String){
        val PutProfile = PutProfile(userName,birth,height,thumnail)
        val call = RetrofitGenerator.create().putProfile(PutProfile)

        call.enqueue(object  : Callback<GetProfileResponse>{

            override fun onFailure(call: Call<GetProfileResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(context,"프로필을 업데이트하는데 실패했습니다.",Toast.LENGTH_LONG).show()
            }
            override fun onResponse(
                call: Call<GetProfileResponse>,
                response: Response<GetProfileResponse>
            ) {
                Toast.makeText(context,"업데이트 하는데 성공했습니다.",Toast.LENGTH_LONG).show()

                var body :GetProfileResponse? = response.body()
                //파싱한 데이터 Intent에 실어서 보내줘야 될듯.
            }
        })
    }
    fun SchoolAuth(name:String, email:String){

        val schoolAuthRequest = SchoolAuthRequest(name,email)
        val call = RetrofitGenerator.create().SchoolAuth(schoolAuthRequest)

        call.enqueue(object : Callback<SchoolAuthResponse>{
            override fun onFailure(call: Call<SchoolAuthResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(context, "이미 가입된 이메일이거나 가입이 불가능한 이메일입니다.", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<SchoolAuthResponse>,
                response: Response<SchoolAuthResponse>
            ) {
                Log.d("response",response.toString())
                var a : SchoolAuthResponse? = response.body()
                Log.d("response2",response.message().toString())


            }
        })

    }


    fun SchoolAuthConfirm(token:String){
        val schoolAuthConfirmRequest = SchoolAuthConfirmRequest(token)
        val call = RetrofitGenerator.create().SchoolAuthConfirm(schoolAuthConfirmRequest)

        call.enqueue(object : Callback<SchoolAuthConfirmResponse>{
            override fun onFailure(call: Call<SchoolAuthConfirmResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(context, "이메일 인증에 실패하였습니다.", Toast.LENGTH_LONG).show()

            }

            override fun onResponse(
                call: Call<SchoolAuthConfirmResponse>,
                response: Response<SchoolAuthConfirmResponse>
            ) {
                Log.d("response",response.toString())
                var a : SchoolAuthConfirmResponse? = response.body()
                Log.d("response2",response.message().toString())
            }
        })
    }

    fun SchoolAuthComplete(email:String){
        val schoolAuthCompleteRequest = SchoolCompleteRequest(email)
        val call = RetrofitGenerator.create().SchoolAuthComplete(schoolAuthCompleteRequest)

        call.enqueue(object : Callback<SchoolCompleteResponse>{
            override fun onFailure(call: Call<SchoolCompleteResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(context, "인증이 필요한 이메일입니다.", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<SchoolCompleteResponse>,
                response: Response<SchoolCompleteResponse>
            ) {
                Log.d("response",response.toString())
                var a : SchoolCompleteResponse? = response.body()
                Log.d("response2",response.message().toString())
            }
        })
    }

    fun LoginKakao(){}

    fun CheckDuplicateId(local_id:String){
        val duplicateIdRequest = DuplicateIdRequest(local_id)
        val call = RetrofitGenerator.create().CheckDuplicateId(duplicateIdRequest)

        call.enqueue(object : Callback<DuplicateIdResponse>{
            override fun onFailure(call: Call<DuplicateIdResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(context, "이미 존재하는 아이디입니다.", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<DuplicateIdResponse>,
                response: Response<DuplicateIdResponse>
            ) {
                Log.d("response",response.toString())
                var a : DuplicateIdResponse? = response.body()
                Log.d("response2",response.message().toString())
            }
        })
    }

    fun CheckDuplicateName(local_id:String){
        val duplicateNameRequest = DuplicateNameRequest(local_id)
        val call = RetrofitGenerator.create().CheckDuplicateName(duplicateNameRequest)

        call.enqueue(object : Callback<DuplicateNameResponse>{
            override fun onFailure(call: Call<DuplicateNameResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(context, "이미 존재하는 닉네임입니다.", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<DuplicateNameResponse>,
                response: Response<DuplicateNameResponse>
            ) {
                Log.d("response",response.toString())
                var a : DuplicateNameResponse? = response.body()
                Log.d("response2",response.message().toString())
            }
        })

    }

}