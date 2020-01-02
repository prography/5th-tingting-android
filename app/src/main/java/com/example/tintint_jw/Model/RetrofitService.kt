package com.example.tintint_jw.Model

import com.example.tintint_jw.Model.Profile.GetProfile
import com.example.tintint_jw.Model.Profile.GetProfileResponse
import com.example.tintint_jw.Model.Profile.PutProfile
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

    //header부분에 Auth 코드를 넣어줘야함.
    @Headers("Accept: application/json")
    @GET("/api/v1/me/profile")
    fun getProfile(@Body user : GetProfile) : Call<GetProfileResponse>

    @PUT("/api/v1/me/profile")
    fun putProfile(@Body user : PutProfile) : Call<GetProfileResponse>

    @GET("/api/v1/users/{id}/profile")
    fun getOtherProfile(@Header("Authentication") authentication:String, @Path("id") id : String) : Call<PutProfile>


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