package com.tingting.ver01.model

import android.app.Activity
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.tingting.ver01.model.matching.*
import com.tingting.ver01.sharedPreference.App
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModelMatching {

    constructor(Fcontext: FragmentActivity?)
    constructor(Acontext : Activity)
    constructor()

    fun lookTeamList(limit : Int, page : Int , onResult: (isSuccess: Boolean, response: ShowAllCandidateListResponse?)->Unit) {

        val call = RetrofitGenerator.createMatchingTeam().lookTeamList(App.prefs.myToken.toString(),limit,page)

        call.enqueue(object : Callback<ShowAllCandidateListResponse>{
            override fun onFailure(call: Call<ShowAllCandidateListResponse>, t: Throwable) {
                t.printStackTrace()
            }
            override fun onResponse(
                call: Call<ShowAllCandidateListResponse>,
                response: Response<ShowAllCandidateListResponse>
            ) {
                  if(response.isSuccessful && response.body()!=null){
                      onResult(true,response.body())
                  }else{
                      onResult(false,null)
                  }
            }
        })
    }

    fun lookMatchingTeam(matchingId :Int, myTeamId:Int, back : TeamDataCallback ){
        val call =RetrofitGenerator.createMatchingTeam().lookOneMatchingTeam(App.prefs.myToken.toString(), matchingId, myTeamId)

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

    fun lookAppliedMatchingTeam(id:Int, myTeamId: Int, onResult: (isSuccess: Boolean, response: ShowAppliedTeamInfoResponse?) -> Unit){
        val call = RetrofitGenerator.createMatchingTeam().lookAppliedMatchingTeam(App.prefs.myToken.toString(), id, myTeamId)

        call.enqueue(object :Callback<ShowAppliedTeamInfoResponse>{
            override fun onFailure(call: Call<ShowAppliedTeamInfoResponse>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<ShowAppliedTeamInfoResponse>,
                response: Response<ShowAppliedTeamInfoResponse>
            ) {
                if(response.isSuccessful){
                    onResult(true,response.body())
                }else{
                    onResult(false,null)
                }

            }

        })

    }

    fun firstSendHeart(receiveTeamId:Int, sendTeamId:Int, message:String, back: CodeCallBack){

        val call = RetrofitGenerator.createMatchingTeam().firstSendHeart(App.prefs.myToken.toString(), SendMessage(receiveTeamId,sendTeamId, message))

        call.enqueue(object : Callback<FirstSendHeartResponse>{
            override fun onFailure(call: Call<FirstSendHeartResponse>, t: Throwable) {

                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<FirstSendHeartResponse>,
                response: Response<FirstSendHeartResponse>
            ) {
                var code:Int = response.code()
                var value:String = response.body().toString()
                Log.i("first heart", code.toString())
                back.onSuccess(code.toString(), value)
                Log.i("first heart", value)

            }

        })
    }

    fun sendHeart(matchingId: Int, back: CodeCallBack){
        val call = RetrofitGenerator.createMatchingTeam().sendHeart(App.prefs.myToken.toString(), SendHeartRequest(matchingId))

        call.enqueue(object :Callback<SendHeartResponse>{
            override fun onFailure(call: Call<SendHeartResponse>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<SendHeartResponse>,
                response: Response<SendHeartResponse>
            ) {
                val code :Int = response.code()
                val value:String = response.body().toString()


                back.onSuccess(code.toString(),value)
            }

        })
    }

    fun receiveHeart(matchingId:Int, back:CodeCallBack){

        val call = RetrofitGenerator.createMatchingTeam().receiveHeart(App.prefs.myToken.toString(), ReceiveHeartRequest(matchingId))

        call.enqueue(object :Callback<ReceiveHeartResponse>{
            override fun onFailure(call: Call<ReceiveHeartResponse>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<ReceiveHeartResponse>,
                response: Response<ReceiveHeartResponse>
            ) {
                var code:Int = response.code()
                var value:String = response.body().toString()

            }

        })

    }

    fun refuseHeart(matchingId:Int, back:CodeCallBack){

        val call = RetrofitGenerator.createMatchingTeam().refuseHeart(App.prefs.myToken.toString(), ReceiveHeartRequest(matchingId))

        call.enqueue(object :Callback<ReceiveHeartResponse>{
            override fun onFailure(call: Call<ReceiveHeartResponse>, t: Throwable) {
                t.printStackTrace()
            }
            override fun onResponse(
                call: Call<ReceiveHeartResponse>,
                response: Response<ReceiveHeartResponse>
            ) {
                var code:Int = response.code()
                var value:String = response.body().toString()
                back.onSuccess(code.toString(), value)
            }

        })
    }

    companion object{
        var INSTANCE : ModelMatching? = null
        fun getInstance() = INSTANCE ?: ModelMatching().also{
            INSTANCE= it
        }
    }

}