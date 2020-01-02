package com.example.tintint_jw.Model

import com.example.tintint_jw.Model.Auth.CheckDuplicate.ID.DuplicateIdRequest
import com.example.tintint_jw.Model.Auth.CheckDuplicate.ID.DuplicateIdResponse
import com.example.tintint_jw.Model.Auth.CheckDuplicate.Nickname.DuplicateNameRequest
import com.example.tintint_jw.Model.Auth.CheckDuplicate.Nickname.DuplicateNameResponse
import com.example.tintint_jw.Model.Auth.Login.Kakao.LoginKakaoRequest
import com.example.tintint_jw.Model.Auth.Login.Kakao.LoginKakaoResponse
import com.example.tintint_jw.Model.Auth.Login.Local.LoginLocalRequest
import com.example.tintint_jw.Model.Auth.Login.Local.LoginLocalResponse
import com.example.tintint_jw.Model.Auth.School.*
import com.example.tintint_jw.Model.Auth.SignUp.SignUpRequest
import com.example.tintint_jw.Model.Auth.SignUp.SignUpResponse
import com.kakao.usermgmt.request.LogoutRequest
import retrofit2.Call
import retrofit2.http.*


interface RetrofitService{

    @Headers("Accept: application/json")
    @POST("/users/")
    fun registerUser(@Body user :RegisterUserRequest ) : Call<RegisterUserRequest>

    @Headers("Accept: application/json")
    @POST("/api-token-auth/")
    fun login(@Body user : LoginRequest) : Call<LoginResponse>

    @Headers("Accept: application/json")
    @POST("/api/v1/auth/school/")
    fun SchoolAuth(@Body user: SchoolAuthRequest) : Call<SchoolAuthResponse>

    @Headers("Accept: application/json")
    @POST("/api/v1/auth/school/confirm/")
    fun SchoolAuthConfirm(@Body user: SchoolAuthConfirmRequest) : Call<SchoolAuthConfirmResponse>

    @Headers("Accept: application/json")
    @GET("/api/v1/auth/school/complete/")
    fun SchoolAuthComplete(@Body user: SchoolCompleteRequest) : Call<SchoolCompleteResponse>

    @Headers("Accept: application/json")
    @GET("/api/v1/auth/kakao/")
    fun LoginKakao(@Body user: LoginKakaoRequest) : Call<LoginKakaoResponse>

    @Headers("Accept: application/json")
    @POST("/api/v1/auth/login/")
    fun LoginLocal(@Body user: LoginLocalRequest) : Call<LoginLocalResponse>

    @Headers("Accept: application/json")
    @POST("/api/v1/auth/local/signup/")
    fun SignUp(@Header("Authorization") auth:String, @Body (user: SignUpRequest) : Call<SignUpResponse>)

    @Headers("Accept: application/json")
    @GET("/api/v1/auth/duplicate-id/")
    fun CheckDuplicateId(@Body user: DuplicateIdRequest) : Call<DuplicateIdResponse>

    @Headers("Accept: application/json")
    @POST("/api/v1/auth/duplicate-name/")
    fun CheckDuplicateName(@Body user: DuplicateNameRequest) : Call<DuplicateNameResponse>

    @Headers("Accept: application/json")
    @GET("/api/v1/auth/logout/")
    fun Logout(@Body user: RequestLogout) : Call<LogoutResponse>

}