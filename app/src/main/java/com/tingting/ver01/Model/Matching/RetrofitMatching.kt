package com.tingting.ver01.Model.Matching

import retrofit2.Call
import retrofit2.http.*

interface RetrofitMatching {

    @GET("/api/v1/matching/teams")
    fun lookTeamList(@Header("Authorization") autho:String) : Call<ShowAllCandidateListResponse>

    @GET("/api/v1/matching/teams/{id}")
    fun lookOneMatchingTeam(@Header("Authorization") autho : String, @Path("id") matchingTeamId:Int
                            ,@Query("myTeamId") myTeamId: Int ) : Call<ShowMatchingTeamInfoResponse>

    @GET("/api/v1/matching/applied-teams/{id}")
    fun lookAppliedMatchingTeam(@Header("Authorization") autho:String,
                                @Path("id") id:Int,
                                @Query("myTeamId") myTeamId:Int) : Call<ShowAppliedTeamInfoResponse>

    @POST("/api/v1/matching/send-heart/first")
    fun firstSendHeart(@Header("Authorization") autho:String,
                  @Body user: SendMessage) : Call<FirstSendHeartResponse>


    @POST("/api/v1/matching/send-heart")
    fun sendHeart(@Header("Authorization") autho:String,
                  @Body user: SendHeartRequest) : Call<SendHeartResponse>

    @POST("/api/v1/matching/receive-heart")
    fun receiveHeart(@Header("Authorization") autho:String,
                     @Body user:ReceiveHeartRequest) : Call<ReceiveHeartResponse>
}