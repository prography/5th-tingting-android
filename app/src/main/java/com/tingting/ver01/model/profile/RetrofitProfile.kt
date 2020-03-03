package com.tingting.ver01.model.profile

import retrofit2.Call
import retrofit2.http.*

interface RetrofitProfile {

    //header부분에 Auth 코드를 넣어줘야함.
    @Headers("Accept: application/json")
    @GET("/api/v1/me/profile")
    fun getProfile(@Header("Authorization")token : String) : Call<GetProfileResponse>

    @Headers("Accept: application/json")
    @GET("/api/v1/users/{id}/profile")
    fun getTeammemberProfile(@Header("Authorization")token : String, @Path("id") id:Int) : Call<GetTeammberProfileResponse>

    @Headers("Accept: application/json")
    @GET("/api/v1/me/profile")
    fun getSentMatchings(@Header("Authorization")token : String) : Call<GetProfileResponse>

    /*@Headers("Accept: application/json")
    @POST("/api/v1/users/:id/report")
    fun reportUser(@Header(""))

    @Headers("Accept: application/json")
    @POST("/api/v1/users/:id/block")
    fun blockUser(@Header(""))*/
}