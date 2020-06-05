package com.tingting.ver01.model

import com.tingting.ver01.model.matching.RetrofitMatching
import com.tingting.ver01.model.profile.RetrofitProfile
import com.tingting.ver01.model.team.RetrofitTeam
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

//import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitGenerator {
    val builder = OkHttpClient.Builder().readTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)


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
       .baseUrl("https://api.tingting.kr")
        //.baseUrl("http://13.209.81.52")
        .addConverterFactory(ToStringConverterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        //.addConverterFactory(ScalarsConverterFactory.create())
        .build()


    fun create() : RetrofitService = retrofit.create(RetrofitService::class.java)
    fun createTeam() : RetrofitTeam = retrofit.create(RetrofitTeam::class.java)
    fun createProfile() : RetrofitProfile = retrofit.create(RetrofitProfile::class.java)
    fun createMatching() : RetrofitMatching = retrofit.create(RetrofitMatching::class.java)
    fun createMatchingTeam () : RetrofitMatching = retrofit.create(RetrofitMatching::class.java)
}