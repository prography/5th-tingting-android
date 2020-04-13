package com.tingting.ver01.model.team

import com.tingting.ver01.model.profile.LookMyTeamInfoProfileResponse
import com.tingting.ver01.model.team.JoinTeam.JoinTeamRequest
import com.tingting.ver01.model.team.JoinTeam.JoinTeamResponse
import com.tingting.ver01.model.team.MakeTeam.MakeTeamRequest
import com.tingting.ver01.model.team.MakeTeam.MakeTeamResponse
import com.tingting.ver01.model.team.MakeTeam.TeamNameResponse
import com.tingting.ver01.model.team.UpdateTeam.UpdateMyTeaminfo
import com.tingting.ver01.model.team.lookIndivisualTeam.IndivisualTeamResponse
import com.tingting.ver01.model.team.lookMyTeamInfoDetail.LookMyTeamInfoDetailResponse
import com.tingting.ver01.model.team.lookMyTeamInfoDetail.LookTeamTagResponse
import com.tingting.ver01.model.team.lookTeamList.TeamResponse
import retrofit2.Call
import retrofit2.http.*

interface RetrofitTeam {

    @GET("/api/v1/teams")
    fun lookTeamList(@Header("Authorization") autho:String, @Query("limit") limit : Int = 20, @Query("page") page : Int = 0) : Call<TeamResponse>

    @POST("/api/v1/teams")
    fun makeTeam(@Header("Authorization") autho:String, @Body user: MakeTeamRequest) : Call<MakeTeamResponse>

    @GET("api/v1/teams/{id}")
    fun oneTeamInfo(@Header("Authorization") autho:String, @Path("id") id:Int) : Call<IndivisualTeamResponse>

    @GET("api/v1/teams/{id}")
    fun oneMyTeamInfo(@Header("Authorization") autho:String, @Path("id") id:Int) : Call<MakeTeamResponse>

    @PATCH("api/v1/me/teams/{id}")
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

    @GET("/api/v1/teams/tags")
    fun lookTeamTag(@Header("Authorization") autho:String) : Call<LookTeamTagResponse>

}
