package com.tingting.ver01.model.profile

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.tingting.ver01.model.ProfileCallBack
import com.tingting.ver01.model.RetrofitGenerator
import com.tingting.ver01.SharedPreference.App
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModelProfile {

    constructor(context: FragmentActivity?) {

    }

    constructor(contextA: AppCompatActivity) {

    }

    constructor() {

    }

    fun getProfile(onResult: (isSuccess: Boolean, response: GetProfileResponse?) -> Unit) {

        val call = RetrofitGenerator.createProfile().getProfile(App.prefs.myToken.toString())

        call.enqueue(object : Callback<GetProfileResponse> {

            override fun onFailure(call: Call<GetProfileResponse>, t: Throwable) {
                onResult(false, null)
            }

            override fun onResponse(
                call: Call<GetProfileResponse>,
                response: Response<GetProfileResponse>
            ) {
                if (response != null && response.isSuccessful) {
                    onResult(true, response.body()!!)
                } else {
                    onResult(false, null)
                }
            }
        })
    }

    fun getSentMatchings(token: String, back: SentMatchingsCallback) {
        val call = RetrofitGenerator.createProfile().getSentMatchings(App.prefs.myToken.toString())

        call.enqueue(object : Callback<GetProfileResponse> {
            override fun onFailure(call: Call<GetProfileResponse>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<GetProfileResponse>,
                response: Response<GetProfileResponse>
            ) {
                response.body()?.let { back.sentMatchings(it) }
            }

        })
    }

    fun getTeammemberInfo(id: Int, profile: ProfileCallBack) {

        val call =
            RetrofitGenerator.createProfile().getTeammemberProfile(App.prefs.myToken.toString(), id)
        call.enqueue(object : Callback<GetTeammberProfileResponse> {
            override fun onFailure(call: Call<GetTeammberProfileResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<GetTeammberProfileResponse>,
                response: Response<GetTeammberProfileResponse>
            ) {

                response.body()?.let { profile.onTeammemberProfileSuccess(it) }

            }
        })

    }


    companion object {
        private var INSTANCE: ModelProfile? = null
        fun getProfileInstance() = INSTANCE ?: ModelProfile().also {
            INSTANCE = it
        }
    }
}