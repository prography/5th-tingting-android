package com.example.tintint_jw.Model

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.example.tintint_jw.Model.Matching.ShowAllCandidateList
import com.example.tintint_jw.Model.Matching.ShowAllCandidateListResponse
import com.example.tintint_jw.Model.Matching.ShowMatchingTeamInfoResponse
import com.example.tintint_jw.SharedPreference.App
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModelMatching {

    constructor(Fcontext: FragmentActivity?){

    }
    constructor(Acontext : Activity){

    }
    fun lookTeamList(token: String, teamId: Int , back : TeamDataCallback) {

        val call = RetrofitGenerator.createMatchingTeam().lookTeamList(token);

        call.enqueue(object : Callback<ShowAllCandidateListResponse>{
            override fun onFailure(call: Call<ShowAllCandidateListResponse>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<ShowAllCandidateListResponse>,
                response: Response<ShowAllCandidateListResponse>
            ) {
                   back.showAllCandidateList(response.body())
            }
        })
    }

    fun lookMatchingTeam(matchingId :Int , back : TeamDataCallback ){
        val call = RetrofitGenerator.createMatchingTeam().lookOneMatchingTeam(App.prefs.myToken.toString(), matchingId,1)

        call.enqueue(object : Callback<ShowMatchingTeamInfoResponse>{

            override fun onFailure(call: Call<ShowMatchingTeamInfoResponse>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<ShowMatchingTeamInfoResponse>,
                response: Response<ShowMatchingTeamInfoResponse>
            ) {
                response.body()?.let { back.LookMatchingTeamInfo(it) }
            }
        })
    }


}