package com.tingting.ver01.model.team

import androidx.fragment.app.FragmentActivity
import com.tingting.ver01.model.RetrofitGenerator
import com.tingting.ver01.model.team.LookTeamList.TeamResponse
import com.tingting.ver01.model.TeamDataCallback
import retrofit2.Call
import retrofit2.Response

class ModelSearchTeam (val context: FragmentActivity?){

    fun showTeamList(token: String, team: TeamDataCallback){

        val call = RetrofitGenerator.createTeam().lookTeamList(token)

        call.enqueue(object :retrofit2.Callback<TeamResponse>{

            override fun onFailure(call: Call<TeamResponse>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<TeamResponse>,
                response: Response<TeamResponse>
            ) {

                val listing = response.body()

                team.onResult(listing,0,1)

            }
        })
    }

}