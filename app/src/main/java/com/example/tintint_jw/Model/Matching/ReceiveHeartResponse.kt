package com.example.tintint_jw.Model.Matching

class ReceiveHeartResponse(val data: Data = Data()
){
    data class Data(
        val message:String = ""
    )
}