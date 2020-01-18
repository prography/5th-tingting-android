package com.example.tintint_jw.Model.Matching

import com.example.tintint_jw.Model.Matching.ShowAllCandidateList
import com.example.tintint_jw.Model.Team.LookTeamList.TeamResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header

interface RetrofitMatching {

    @GET("api/v1/matching/teams")
    fun lookTeamList(@Header("Authorization") autho:String) : Call<ShowAllCandidateListResponse>

}