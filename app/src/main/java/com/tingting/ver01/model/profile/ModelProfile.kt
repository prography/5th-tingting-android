package com.tingting.ver01.model.profile

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.tingting.ver01.model.ProfileCallBack
import com.tingting.ver01.model.RetrofitGenerator
import com.tingting.ver01.SharedPreference.App
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModelProfile{

    constructor( context:FragmentActivity?){

    }
    constructor(contextA : AppCompatActivity){

    }
    fun getProfile(token: String, profile: ProfileCallBack) {

        val call = RetrofitGenerator.createProfile()
            .getProfile(token)

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

        try {
              profile.onSuccess(
                  body!!.data.myInfo.name
                ,body!!.data.myInfo.birth
                ,body!!.data.myInfo.height.toString()
                ,body!!.data.myInfo.thumbnail
                ,body!!.data.myInfo.gender.toString()
                ,body!!.data.myTeamList)

            profile.onSuccess2(

                body!!.data.myInfo.name
                ,body!!.data.myInfo.birth
                ,body!!.data.myInfo.height.toString()
                ,body!!.data.myInfo.thumbnail
                ,body!!.data.myInfo.gender.toString()
                ,body!!.data.myInfo.schoolName
                ,body!!.data.myTeamList

            )
            }  catch (e : Exception){

        }
                //파싱한 데이터 Intent에 실어서 보내줘야 될듯.
            }
        })
    }

    fun getSentMatchings(token: String, back: SentMatchingsCallback){
        val call = RetrofitGenerator.createProfile().getSentMatchings(App.prefs.myToken.toString())

        call.enqueue(object : Callback<GetProfileResponse>{
            override fun onFailure(call: Call<GetProfileResponse>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<GetProfileResponse>,
                response: Response<GetProfileResponse>
            ) {
                response.body()?.let { back.sentMatchings(it) }            }

        })
    }

    fun getTeammemberInfo(id:Int ,profile: ProfileCallBack){

        val call = RetrofitGenerator.createProfile().getTeammemberProfile(App.prefs.myToken.toString(),id)
        call.enqueue(object : Callback<GetTeammberProfileResponse>{
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


}