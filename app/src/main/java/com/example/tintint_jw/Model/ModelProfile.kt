package com.example.tintint_jw.Model

import GetProfileResponse
import android.util.Log
import androidx.fragment.app.FragmentActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModelProfile(val context:FragmentActivity?){

    fun getProfile(token: String, profile: ProfileCallBack) {

        val call = RetrofitGenerator.createProfile().getProfile(token)

        call.enqueue(object : Callback<GetProfileResponse> {

            override fun onFailure(call: Call<GetProfileResponse>, t: Throwable) {
                t.printStackTrace()
                call.cancel()
            }

            override fun onResponse(
                call: Call<GetProfileResponse>,
                response: Response<GetProfileResponse>
            ) {

                var body: GetProfileResponse? = response.body()

                Log.d("KaKaoTest",response.body().toString())
                Log.d("KaKaoTest",response.body().toString().get(0).toString())

              profile.onSuccess(body!!.data.myInfo.name
                    ,body!!.data.myInfo.birth
                    ,body!!.data.myInfo.height.toString()
                    ,body!!.data.myInfo.thumbnail
                    ,body!!.data.myInfo.gender.toString()
                    ,body!!.data.myTeamList)

                //파싱한 데이터 Intent에 실어서 보내줘야 될듯.
            }
        })
    }

}