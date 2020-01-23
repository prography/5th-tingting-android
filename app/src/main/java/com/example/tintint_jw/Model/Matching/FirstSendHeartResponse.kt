package com.example.tintint_jw.Model.Matching

data class FirstSendHeartResponse(
    val data: Data = Data()
){
    data class Data(
        val message:String = ""
    )
}