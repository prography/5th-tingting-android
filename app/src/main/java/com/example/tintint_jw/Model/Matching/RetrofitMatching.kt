package com.example.tintint_jw.Model.Matching

import retrofit2.Call
import retrofit2.http.*

interface RetrofitMatching {

    @GET("api/v1/matching/teams")
    fun lookTeamList(@Header("Authorization") autho:String) : Call<ShowAllCandidateListResponse>

    @GET("api/v1/matching/teams/{id}")
    fun lookOneMatchingTeam(@Header("Authorization") auth : String, @Path("id") matchingTeamId:Int,@Body myTeamId: Int ) : Call<ShowMatchingTeamInfoResponse>

    @POST("/api/v1/matching/send-heart/first/")
    fun sendHeart(@Header("Authorization") autho:String,
                  @Body receiveTeamId:Int, sendTeamId:Int, message:String) : Call<String>
}