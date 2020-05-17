package com.tingting.ver01.model

import android.app.Activity
import android.util.Log
import com.tingting.ver01.model.profile.LookMyTeamInfoProfileResponse
import com.tingting.ver01.model.team.JoinTeam.JoinTeamRequest
import com.tingting.ver01.model.team.JoinTeam.JoinTeamResponse
import com.tingting.ver01.model.team.LeaveTeamResponse
import com.tingting.ver01.model.team.MakeTeam.MakeTeamRequest
import com.tingting.ver01.model.team.MakeTeam.MakeTeamResponse
import com.tingting.ver01.model.team.MakeTeam.TeamNameResponse
import com.tingting.ver01.model.team.UpdateTeam.UpdateMyTeaminfo
import com.tingting.ver01.model.team.lookIndivisualTeam.IndivisualTeamResponse
import com.tingting.ver01.model.team.lookMyTeamInfoDetail.LookMyTeamInfoDetailResponse
import com.tingting.ver01.model.team.lookMyTeamInfoDetail.LookTeamTagResponse
import com.tingting.ver01.sharedPreference.App
import com.tingting.ver01.view.Main.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class  ModelTeam {

    lateinit var context : Activity
    constructor()
    constructor(activityContext: Activity){
        context = activityContext
    }
    fun makeTeam(
        token: String, gender: Int, name: String, place:String, password:String,
        max_member_number: Int, tagIds: ArrayList<Int>, chat_address: String, back: CodeCallBack
    ) {

        val request = MakeTeamRequest(gender, name, place, password, max_member_number, tagIds, chat_address)
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

    fun showIndivisualTeamList(bossid: Int, team: TeamDataCallback) {
        val call = RetrofitGenerator.createTeam().oneTeamInfo(App.prefs.myToken.toString(), bossid)

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

    fun JoinTeam(teamid: Int, password : String , onResult: (isSuccess: Boolean, response: Int?) -> Unit) {
        val request = JoinTeamRequest(password)
        val call = RetrofitGenerator.createTeam().joinTeam(App.prefs.myToken.toString(), teamid, request)

        call.enqueue(object : retrofit2.Callback<JoinTeamResponse> {
            override fun onFailure(call: Call<JoinTeamResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<JoinTeamResponse>,
                response: Response<JoinTeamResponse>
            ) {
                if(response.isSuccessful && response.body()!=null){
                    onResult(true,response.code())
                }else{
                    onResult(false,response.code())
                }

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
        TagIds: List<Int>,
        Chat_address: String,
        back:CodeCallBack
    ) {
        val request = UpdateMyTeaminfo(
            place,
            Password,
            MainActivity.gender,
            Name,
            Max_member_number.toInt(),
            TagIds,
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

    fun lookMyTeamInfo(Id: Int, onResult :(isSuccessful : Boolean, response : LookMyTeamInfoDetailResponse?) ->Unit) {
        val call = RetrofitGenerator.createTeam().LookMyTeamInfoDetail(App.prefs.myToken.toString(), Id)

        call.enqueue(object : Callback<LookMyTeamInfoDetailResponse> {
            override fun onFailure(call: Call<LookMyTeamInfoDetailResponse>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<LookMyTeamInfoDetailResponse>,
                response: Response<LookMyTeamInfoDetailResponse>
            ) {

                if(response.code()==200){
                    onResult(true ,response.body())
                }else{
                    onResult(false,null)
                }
            }
        })
    }

    fun LookMyTeamInfopPofile(Id: Int, team: TeamDataCallback) {
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

    fun LookMyTeamInfo2(Id: Int, team: TeamDataCallback) {
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






    fun teamLeave(teamid:Int, onResult: (isSuccess: Boolean, response: Int?) -> Unit){

    val call = RetrofitGenerator.createTeam().leaveTeam(App.prefs.myToken.toString(),teamid)


        call.enqueue(object :Callback<LeaveTeamResponse>{
            override fun onFailure(call: Call<LeaveTeamResponse>, t: Throwable) {
                t.printStackTrace()
            }
            override fun onResponse(
                call: Call<LeaveTeamResponse>,
                response: Response<LeaveTeamResponse>
            ) {
                if(response.isSuccessful&&response.code()==200){
                    onResult(true,response.code())
                }else{
                    onResult(false,response.code())
                }
            }
        })
    }

    fun lookTeamTag(team:TeamDataCallback){
        val call = RetrofitGenerator.createTeam().lookTeamTag(App.prefs.myToken.toString())

        call.enqueue(object :Callback<LookTeamTagResponse>{
            override fun onFailure(call: Call<LookTeamTagResponse>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<LookTeamTagResponse>,
                response: Response<LookTeamTagResponse>
            ) {
                try{
                    response.body()?.let {
                        team.LookTeamTag(it)
                    }
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }

        })
    }

    companion object{
        var INSTANCE : ModelTeam? = null
        fun getInstance() = INSTANCE ?: ModelTeam().also {
            INSTANCE = it
        }

    }


}
