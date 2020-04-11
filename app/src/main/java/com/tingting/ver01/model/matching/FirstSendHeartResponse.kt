package com.tingting.ver01.model.matching

data class FirstSendHeartResponse(
    val data: Data = Data()
){
    data class Data(
        val message:String = ""
    )
}