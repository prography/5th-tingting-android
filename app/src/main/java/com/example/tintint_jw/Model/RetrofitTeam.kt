package com.example.tintint_jw.Model

import com.example.tintint_jw.Model.Team.LookTeamList.TeamListResponse
import com.example.tintint_jw.Model.Team.MakeTeam.MakeTeamRequest
import com.example.tintint_jw.Model.Team.MakeTeam.MakeTeamResponse
import com.example.tintint_jw.Model.Team.UpdateTeam.UpdateMyTeaminfo
import retrofit2.Call
import retrofit2.http.*

interface RetrofitTeam {

    @GET("/api/v1/teams")
    fun lookTeamList(@Header("Authorization") autho:String ) : Call<TeamListResponse>

    @POST("/api/v1/teams")
    fun makeTeam(@Header("Authorization") autho:String, @Body user: MakeTeamRequest) : Call<MakeTeamResponse>

    @GET("api/v1/teams/{id}")
    fun oneTeamInfo(@Header("Autorization") autho:String, @Path("id") id:Int) : Call<MakeTeamResponse>

    @GET("api/v1/teams/{id}")
    fun oneMyTeamInfo(@Header("Autorization") autho:String, @Path("id") id:Int) : Call<MakeTeamResponse>

    @PUT("api/v1/me/teams/{id}")
    fun updateMyTeamInfo(@Header("Autorization") autho:String, @Path("id") id:Int, @Body user : UpdateMyTeaminfo) : Call<MakeTeamResponse>

    @POST("api/v1/me/teams/{id}/leave")
    fun leaveTeam(@Header("Autorization") autho:String, @Path("id") id:Int)

    @POST("api/v1/me/teams/{id}/join")
    fun joinTeam(@Header("Autorization") autho:String, @Path("id") id:Int)

}
