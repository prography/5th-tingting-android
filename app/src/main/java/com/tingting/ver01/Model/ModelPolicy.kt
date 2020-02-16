package com.tingting.ver01.Model

import android.app.Activity
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModelPolicy(val context: Activity) {

    fun GetPolicyRule(callBack:CodeCallBack){
        val call = RetrofitGenerator.create().GetPolicyRule()

        call.enqueue(object : retrofit2.Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<String>,
                response: Response<String>
            ) {
                var code:Int = response.code()
                var value:String = response.body().toString()
                Log.i("policy code", code.toString())
                callBack.onSuccess(code.toString(), value)
                Log.d("policy", value)

            }

        })

    }

    fun GetPolicyPrivate(callBack: CodeCallBack) {
        val call = RetrofitGenerator.create().GetPolicyPrivate()

        call.enqueue(object :Callback<String>{
            override fun onFailure(call: Call<String>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                var code:Int = response.code()
                var value:String = response.body().toString()
                callBack.onSuccess(code.toString(), value)
            }

        })

    }
}