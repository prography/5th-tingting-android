package com.example.tintint_jw.Model.Team

import com.example.tintint_jw.Model.Profile.LookMyTeamInfoProfileResponse
import com.example.tintint_jw.Model.Team.JoinTeam.JoinTeamRequest
import com.example.tintint_jw.Model.Team.JoinTeam.JoinTeamResponse
import com.example.tintint_jw.Model.Team.LookIndivisualTeam.IndivisualTeamResponse
import com.example.tintint_jw.Model.Team.LookMyTeamInfoDetail.LookMyTeamInfoDetailResponse
import com.example.tintint_jw.Model.Team.LookTeamList.TeamResponse
import com.example.tintint_jw.Model.Team.MakeTeam.MakeTeamRequest
import com.example.tintint_jw.Model.Team.MakeTeam.MakeTeamResponse
import com.example.tintint_jw.Model.Team.MakeTeam.TeamNameResponse
import com.example.tintint_jw.Model.Team.UpdateTeam.UpdateMyTeaminfo
import retrofit2.Call
import retrofit2.http.*

interface RetrofitTeam {

    @GET("/api/v1/teams")
    fun lookTeamList(@Header("Authorization") autho:String) : Call<TeamResponse>

    @POST("/api/v1/teams")
    fun makeTeam(@Header("Authorization") autho:String, @Body user: MakeTeamRequest) : Call<MakeTeamResponse>

    @GET("api/v1/teams/{id}")
    fun oneTeamInfo(@Header("Authorization") autho:String, @Path("id") id:Int) : Call<IndivisualTeamResponse>

    @GET("api/v1/teams/{id}")
    fun oneMyTeamInfo(@Header("Authorization") autho:String, @Path("id") id:Int) : Call<MakeTeamResponse>

    @PUT("api/v1/me/teams/{id}")
    fun updateMyTeamInfo(@Header("Authorization") autho:String, @Path("id") id:Int, @Body user : UpdateMyTeaminfo) : Call<MakeTeamResponse>

    @POST("api/v1/me/teams/{id}/leave")
    fun leaveTeam(@Header("Authorization") autho:String, @Path("id") id:Int) : Call<LeaveTeamResponse>

    @POST("api/v1/teams/{id}/join")
    fun joinTeam(@Header("Authorization") autho:String, @Path("id") id:Int,@Body user : JoinTeamRequest) : Call<JoinTeamResponse>

    @GET("/api/v1/teams/duplicate-name")
    fun McheckTeamName(@Header("Authorization") autho:String, @Query("name") teamName:String ) :  Call<TeamNameResponse>

    @GET("/api/v1/me/teams/{id}")
    fun LookMyTeamInfoDetail(@Header("Authorization") autho:String, @Path("id") id: Int) : Call<LookMyTeamInfoDetailResponse>

    @GET("/api/v1/me/teams/{id}")
    fun LookMyTeamInfoDetailProfile(@Header("Authorization") autho:String, @Path("id") id: Int) : Call<LookMyTeamInfoProfileResponse>


}
