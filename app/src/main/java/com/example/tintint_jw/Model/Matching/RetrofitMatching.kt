package com.example.tintint_jw.Model.Matching

import com.example.tintint_jw.Model.Matching.ShowAllCandidateList
import com.example.tintint_jw.Model.Team.LookTeamList.TeamResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface RetrofitMatching {

    @GET("api/v1/matching/teams")
    fun lookTeamList(@Header("Authorization") autho:String) : Call<ShowAllCandidateListResponse>

    @GET("api/v1/matching/teams/{id}")
    fun lookOneMatchingTeam(@Header("Authorization") auth : String, @Path("id") matchingTeamId:Int,@Body myTeamId: Int ) : Call<ShowMatchingTeamInfoResponse>

}