package com.example.tintint_jw.Model.Profile

import GetProfileResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path

interface RetrofitProfile {

    //header부분에 Auth 코드를 넣어줘야함.
    @Headers("Accept: application/json")
    @GET("/api/v1/me/profile")
    fun getProfile(@Header("Authorization")token : String) : Call<GetProfileResponse>

    @Headers("Accept: application/json")
    @GET("/api/v1/users/:id/profile")
    fun getTeammemberProfile(@Header("Authorization")token : String, @Path("id") id:Int) : Call<GetTeammberProfileResponse>

}