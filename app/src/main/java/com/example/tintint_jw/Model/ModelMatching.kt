package com.example.tintint_jw.Model

import androidx.fragment.app.FragmentActivity
import com.example.tintint_jw.Model.Matching.ShowAllCandidateListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModelMatching(val context: FragmentActivity?) {

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

}