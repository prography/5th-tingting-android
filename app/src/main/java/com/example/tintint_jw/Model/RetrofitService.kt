package com.example.tintint_jw.Model

import com.example.tintint_jw.Model.Profile.GetProfile
import com.example.tintint_jw.Model.Profile.GetProfileResponse
import com.example.tintint_jw.Model.Profile.PutProfile
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


}