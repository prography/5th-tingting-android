package com.tingting.ver01.Model.Matching

data class FirstSendHeartResponse(
    val data: Data = Data()
){
    data class Data(
        val message:String = ""
    )
}