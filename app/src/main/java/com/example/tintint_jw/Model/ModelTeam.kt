package com.example.tintint_jw.Model

import android.app.Activity
import com.example.tintint_jw.Model.Team.MakeTeam.MakeTeamRequest
import com.example.tintint_jw.Model.Team.MakeTeam.MakeTeamResponse
import retrofit2.Call
import retrofit2.Response

class ModelTeam(val context:Activity){

    fun makeTeam( token: String, password:String,  gender:Int,  name:String,
                  max_member_number:Int, intro : String,  chat_address:String){

        val request  = MakeTeamRequest(password,gender,name,max_member_number,intro,chat_address)
        val call = RetrofitGenerator.createTeam().makeTeam(token,request)

        call.enqueue(object :retrofit2.Callback<MakeTeamResponse> {
            override fun onFailure(call: Call<MakeTeamResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<MakeTeamResponse>,
                response: Response<MakeTeamResponse>
            ) {

            }

        })
    }
}
