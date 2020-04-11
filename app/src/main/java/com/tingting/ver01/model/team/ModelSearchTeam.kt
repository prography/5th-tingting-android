package com.tingting.ver01.model.team

import androidx.fragment.app.FragmentActivity
import com.tingting.ver01.sharedPreference.App
import com.tingting.ver01.model.RetrofitGenerator
import com.tingting.ver01.model.team.lookTeamList.TeamResponse
import retrofit2.Call
import retrofit2.Response

class ModelSearchTeam {

    constructor(context : FragmentActivity?)

    constructor()

    fun showTeamList(limit : Int, page:Int, result :(isSuccess :Boolean, response : TeamResponse? ) -> Unit ){

        val call = RetrofitGenerator.createTeam().lookTeamList(App.prefs.myToken.toString(),limit, page)

        call.enqueue(object :retrofit2.Callback<TeamResponse>{

            override fun onFailure(call: Call<TeamResponse>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<TeamResponse>,
                response: Response<TeamResponse>
            ) {

                if(response!=null && response.isSuccessful){
                    result(true, response.body())
                } else{
                    result(false,null)
                }
             //   team.onResult(listing,0,1)

            }
        })
    }
    companion object {
        private var INSTANCE: ModelSearchTeam? = null
        fun getProfileInstance() = INSTANCE ?: ModelSearchTeam().also {
            INSTANCE = it
        }
    }
}