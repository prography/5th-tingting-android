package com.example.tintint_jw.Model.Matching

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface RetrofitMatching {

    @GET("api/v1/matching/teams")
    fun lookTeamList(@Header("Authorization") autho:String) : Call<ShowAllCandidateListResponse>

    @POST("/api/v1/matching/send-heart/first/")
    fun sendHeart(@Header("Authorization") autho:String,
                  @Body receiveTeamId:Int, sendTeamId:Int, message:String) : Call<String>
}