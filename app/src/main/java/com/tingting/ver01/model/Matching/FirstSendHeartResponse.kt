package com.tingting.ver01.model.Matching

data class FirstSendHeartResponse(
    val data: Data = Data()
){
    data class Data(
        val message:String = ""
    )
}