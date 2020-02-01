package com.tingting.ver01.Model.Auth

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.tingting.ver01.Model.Auth.School.SchoolAuthRequest
import com.tingting.ver01.Model.Auth.School.SchoolAuthResponse
import com.tingting.ver01.Model.Auth.School.SchoolCompleteRequest
import com.tingting.ver01.Model.Auth.School.SchoolCompleteResponse
import com.tingting.ver01.Model.CodeCallBack
import com.tingting.ver01.Model.IdCallBack
import com.tingting.ver01.Model.RetrofitGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModelSchoolAuth(val context: Context) {

    fun schoolAuth(name: String, email: String, id : CodeCallBack) {

        val schoolAuthRequest = SchoolAuthRequest(name, email)
        val call = RetrofitGenerator.create().SchoolAuth(schoolAuthRequest)

        call.enqueue(object : Callback<SchoolAuthResponse> {
            override fun onFailure(call: Call<SchoolAuthResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(context, "이미 가입된 이메일이거나 가입이 불가능한 이메일입니다.", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<SchoolAuthResponse>,
                response: Response<SchoolAuthResponse>
            ) {
                try{
                    var code:Int = response.code()
                    var message:String = response.message()
                    //var a: SchoolAuthResponse? = response.body()
                    id.onSuccess(code.toString(), message)
                    Log.d("School Auth response", response.message().toString())

                }catch (e : Exception){
                    id.onSuccess(response.code().toString(),"")
                }

            }
        })

    }

    /*fun schoolAuthConfirm(token: String) {
        val schoolAuthConfirmRequest = SchoolAuthConfirmRequest(token)
        val call = RetrofitGenerator.create().SchoolAuthConfirm(schoolAuthConfirmRequest)

        call.enqueue(object : Callback<SchoolAuthConfirmResponse> {
            override fun onFailure(call: Call<SchoolAuthConfirmResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(context, "이메일 인증에 실패하였습니다.", Toast.LENGTH_LONG).show()

            }

            override fun onResponse(
                call: Call<SchoolAuthConfirmResponse>,
                response: Response<SchoolAuthConfirmResponse>
            ) {
                Log.d("response", response.toString())
                var a: SchoolAuthConfirmResponse? = response.body()
                Log.d("response2", response.message().toString())
            }
        })
    }*/

    fun schoolAuthComplete(email: String, id:IdCallBack) {
        try{
        val schoolAuthCompleteRequest = SchoolCompleteRequest(email)
        val call = RetrofitGenerator.create().SchoolAuthComplete(email)

        call.enqueue(object : Callback<SchoolCompleteResponse> {
            override fun onFailure(call: Call<SchoolCompleteResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(context, "인증이 필요한 이메일입니다.", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<SchoolCompleteResponse>,
                response: Response<SchoolCompleteResponse>
            ) {
                Log.d("Complete response", response.toString())
                var a: SchoolCompleteResponse? = response.body()
                try{
                    id.onSuccess(a!!.data.message)
                }catch(e: KotlinNullPointerException){

                }
                Log.d("Complete response2", response.message().toString())
            }
        })}
        catch (e : IllegalArgumentException){
            e.printStackTrace()
        }

    }


}