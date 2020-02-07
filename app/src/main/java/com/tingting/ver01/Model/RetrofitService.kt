package com.tingting.ver01.Model

import GetProfileResponse
import com.example.tintint_jw.Model.Profile.SignUpKakaoRequest
import com.tingting.ver01.Model.Auth.CheckDuplicate.ID.DuplicateIdResponse
import com.tingting.ver01.Model.Auth.CheckDuplicate.Nickname.DuplicateNameResponse
import com.tingting.ver01.Model.Auth.Findidpw.FindIdRequest
import com.tingting.ver01.Model.Auth.Findidpw.FindIdResponse
import com.tingting.ver01.Model.Auth.Findidpw.FindPwRequest
import com.tingting.ver01.Model.Auth.Findidpw.FindPwResponse
import com.tingting.ver01.Model.Auth.Login.Kakao.LoginKakaoRequest
import com.tingting.ver01.Model.Auth.Login.Kakao.LoginKakaoResponse
import com.tingting.ver01.Model.Auth.Login.Local.LoginLocalRequest
import com.tingting.ver01.Model.Auth.Login.Local.LoginLocalResponse
import com.tingting.ver01.Model.Auth.School.*
import com.tingting.ver01.Model.Auth.SignUp.SignUpRequest
import com.tingting.ver01.Model.Auth.SignUp.SignUpResponse
import com.tingting.ver01.Model.Profile.PatchProfileResponse
import com.tingting.ver01.Model.Profile.PutProfile
import retrofit2.Call
import retrofit2.http.*


interface RetrofitService{

    @Headers("Accept: application/json")
    @POST("/api-token-auth/")
    fun login(@Body user : LoginKakaoRequest) : Call<LoginLocalResponse>

    //header부분에 Auth 코드를 넣어줘야함.
    @Headers("Accept: application/json")
    @GET("/api/v1/me/profile")
    fun getProfile(@Header("Authorization") user : String) : Call<GetProfileResponse>

    @PATCH("/api/v1/me/profile")
    fun putProfile(@Header("Authorization") auth : String , @Body user : PutProfile) : Call<PatchProfileResponse>

    @GET("/api/v1/users/{id}/profile")
    fun getOtherProfile(@Header("Authentication") authentication:String, @Path("id") id : String) : Call<PutProfile>

    @Headers("Accept: application/json")

    @POST("/api/v1/auth/school")
    fun SchoolAuth(@Body user: SchoolAuthRequest) : Call<SchoolAuthResponse>

    @Headers("Accept: application/json")
    @POST("/api/v1/auth/school/confirm")
    fun SchoolAuthConfirm(@Body user: SchoolAuthConfirmRequest) : Call<SchoolAuthConfirmResponse>

    @Headers("Accept: application/json")
    @GET("/api/v1/auth/school/complete")
    fun SchoolAuthComplete(@Query("email") email:String) : Call<SchoolCompleteResponse>

    @Headers("Accept: application/json")
    @POST("/api/v1/auth/kakao/login")
    fun LoginKakao(@Header("Authorization") kakaoId : String) : Call<LoginKakaoResponse>

    @Headers("Accept: application/json")
    @POST("/api/v1/auth/kakao/login")
    fun SignUpKakao(@Header("Authorization") kakaoId : String, @Body user : SignUpKakaoRequest) : Call<LoginKakaoResponse>

    @Headers("Accept: application/json")
    @POST("/api/v1/auth/local/login")
    fun LoginLocal(@Body user: LoginLocalRequest) : Call<LoginLocalResponse>

    @Headers("Accept: application/json")
    @POST("/api/v1/auth/local/signup")
    fun SignUp(@Body user : SignUpRequest) : Call<SignUpResponse>

    @Headers("Accept: application/json")
    @GET("/api/v1/auth/duplicate-id")
    fun CheckDuplicateId(@Query("local_id") name:String) : Call<DuplicateIdResponse>

    @Headers("Accept: application/json")
    @GET("/api/v1/auth/duplicate-name")
    fun CheckDuplicateName(@Query("name") name: String) : Call<DuplicateNameResponse>


    @Headers("Accept: application/json")
    @GET("/api/v1/policy/rule/")
    fun GetPolicyRule() : Call<String>

    @Headers("Accept: application/json")
    @GET("/api/v1/policy/privacy/")
    fun GetPolicyPrivate() : Call<String>

  /*  @Headers("Accept: application/json")
    @GET("/api/v1/auth/logout/")
    fun Logout(@Body user: RequestLogout) : Call<LogoutResponse>
*/
    @Headers("Accept: Application/json")
    @GET("/api/v1/auth/find/id")
    fun findId(@Body user: FindIdRequest) : Call<FindIdResponse>

    @Headers("Accept: Application/json")
    @GET("/api/v1/auth/find/password")
    fun findPw(@Body user: FindPwRequest) : Call<FindPwResponse>

}