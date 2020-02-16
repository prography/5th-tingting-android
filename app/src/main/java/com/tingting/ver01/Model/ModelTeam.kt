package com.tingting.ver01.Model

import LookMyTeamInfoDetailResponse
import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.tingting.ver01.Model.Profile.LookMyTeamInfoProfileResponse
import com.tingting.ver01.Model.Team.JoinTeam.JoinTeamRequest
import com.tingting.ver01.Model.Team.JoinTeam.JoinTeamResponse
import com.tingting.ver01.Model.Team.LeaveTeamResponse
import com.tingting.ver01.Model.Team.LookIndivisualTeam.IndivisualTeamResponse
import com.tingting.ver01.Model.Team.MakeTeam.MakeTeamRequest
import com.tingting.ver01.Model.Team.MakeTeam.MakeTeamResponse
import com.tingting.ver01.Model.Team.MakeTeam.TeamNameResponse
import com.tingting.ver01.Model.Team.UpdateTeam.UpdateMyTeaminfo
import com.tingting.ver01.SharedPreference.App
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModelTeam(val context: Activity) {

    fun makeTeam(
        token: String, gender: Int, name: String, place:String,
        max_member_number: Int, intro: String, chat_address: String, back: CodeCallBack
    ) {

        val request = MakeTeamRequest(gender, name, place, max_member_number, intro, chat_address)
        val call = RetrofitGenerator.createTeam().makeTeam(token, request)

        call.enqueue(object : retrofit2.Callback<MakeTeamResponse> {
            override fun onFailure(call: Call<MakeTeamResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<MakeTeamResponse>,
                response: Response<MakeTeamResponse>
            ) {
                var code:Int = response.code()
                var value:String = response.body().toString()
                back.onSuccess(code.toString(), value)
            }

        })
    }

    fun showIndivisualTeamList(token: String, bossid: Int, team: TeamDataCallback) {
        val call = RetrofitGenerator.createTeam().oneTeamInfo(token, bossid)

        call.enqueue(object : retrofit2.Callback<IndivisualTeamResponse> {

            override fun onFailure(call: Call<IndivisualTeamResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<IndivisualTeamResponse>,
                response: Response<IndivisualTeamResponse>
            ) {
                team.onIndivisualResult(response.body(), 0, 0)
            }

        })
    }

    fun TeamName(n: String, back: CodeCallBack) {

        val call = RetrofitGenerator.createTeam().McheckTeamName(App.prefs.myToken.toString(), n)

        call.enqueue(object : Callback<TeamNameResponse> {
            override fun onFailure(call: Call<TeamNameResponse>, t: Throwable) {

                return
            }

            override fun onResponse(
                call: Call<TeamNameResponse>,
                response: Response<TeamNameResponse>
            ) {
                var code:Int = response.code()
                var value:String = response.body().toString()
                back.onSuccess(code.toString(), value)
            }
        })
    }

    fun JoinTeam(token: String, teamid: Int) {
        val request = JoinTeamRequest("")
        val call = RetrofitGenerator.createTeam().joinTeam(token, teamid, request)

        call.enqueue(object : retrofit2.Callback<JoinTeamResponse> {
            override fun onFailure(call: Call<JoinTeamResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<JoinTeamResponse>,
                response: Response<JoinTeamResponse>
            ) {

            }
        })

    }

    fun ReviseTeamInfo(
        token: String,
        place:String,
        teamId: Int,
        Password: String,
        Gender: String,
        Name: String,
        Max_member_number: String
        ,
        Intro: String,
        Tag_list: String,
        Chat_address: String,
        back:CodeCallBack
    ) {
        val request = UpdateMyTeaminfo(
            place,
            Password,
            Gender.toInt(),
            Name,
            Max_member_number.toInt(),
            Intro,
            Tag_list,
            Chat_address
        )

        val call = RetrofitGenerator.createTeam().updateMyTeamInfo(token, teamId, request)

        call.enqueue(object : Callback<MakeTeamResponse> {
            override fun onFailure(call: Call<MakeTeamResponse>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<MakeTeamResponse>,
                response: Response<MakeTeamResponse>
            ) {
                var code:Int = response.code()
                var value:String = response.body().toString()
                Log.i("reviseTeam", code.toString())
                back.onSuccess(code.toString(), value)
            }
        })
    }

    fun LookMyTeamInfo(Id: Int, team: TeamDataCallback) {
        val call =
            RetrofitGenerator.createTeam().LookMyTeamInfoDetail(App.prefs.myToken.toString(), Id)

        call.enqueue(object : Callback<LookMyTeamInfoDetailResponse> {
            override fun onFailure(call: Call<LookMyTeamInfoDetailResponse>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<LookMyTeamInfoDetailResponse>,
                response: Response<LookMyTeamInfoDetailResponse>
            ) {
                try {
                    response.body()?.let { team.LookMyTeaminfoList(it) }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        })
    }

    fun     LookMyTeamInfopPofile(Id: Int, team: TeamDataCallback) {
        val call =
            RetrofitGenerator.createTeam().LookMyTeamInfoDetailProfile(App.prefs.myToken.toString(), Id)

        call.enqueue(object : Callback<LookMyTeamInfoProfileResponse> {
            override fun onFailure(call: Call<LookMyTeamInfoProfileResponse>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<LookMyTeamInfoProfileResponse>,
                response: Response<LookMyTeamInfoProfileResponse>
            ) {
                try {
                    response.body()?.let { team.LookMyTeamInfoListProfile(it) }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        })
    }

    fun TeamLeave(teamid:Int){

    val call =RetrofitGenerator.createTeam().leaveTeam(App.prefs.myToken.toString(),teamid)
        call.enqueue(object :Callback<LeaveTeamResponse>{
            override fun onFailure(call: Call<LeaveTeamResponse>, t: Throwable) {
                t.printStackTrace()
            }
            override fun onResponse(
                call: Call<LeaveTeamResponse>,
                response: Response<LeaveTeamResponse>
            ) {
                if(response.body()?.data?.message.equals("이미 매칭 된 팀, 나가기 불가")){
                    Toast.makeText(context,"이미 매칭 된 팀, 나가기 불가.",Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(context,"팀 나가기 완료",Toast.LENGTH_LONG).show()
                }


            }
        })
    }
}
