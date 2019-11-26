package com.example.tintint_jw.Model

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST


interface RetrofitService{

    @Headers("Accept: application/json")
    @POST("/users/")
    fun registerUser(@Body user :RegisterUserRequest ) : Call<RegisterUserRequest>

    @Headers("Accept: application/json")
    @POST("/api-token-auth/")
    fun login(@Body user : LoginRequest) : Call<LoginResponse>

}