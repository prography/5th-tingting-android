package com.example.tintint_jw.Model.Matching

data class SendHeartResponse(
    val data: Data = Data()
){
    data class Data(
        val message:String = ""
    )
}