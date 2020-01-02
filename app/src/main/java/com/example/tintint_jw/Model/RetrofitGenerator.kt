package com.example.tintint_jw.Model

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitGenerator {
    val builder = OkHttpClient.Builder()

    //log 찍는 방법.
    init{
        val httpLoggingInterceptor  =  HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        builder.addInterceptor(httpLoggingInterceptor)
    }



    //retrofit 재설정.
    val okHttpClient = builder.build()
    private val retrofit = Retrofit.Builder().client(okHttpClient)
        .baseUrl("https://tingting.kr")
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    fun create() : RetrofitService = retrofit.create(RetrofitService::class.java)

}