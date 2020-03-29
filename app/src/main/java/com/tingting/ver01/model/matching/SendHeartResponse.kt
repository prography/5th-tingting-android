package com.tingting.ver01.model.matching

data class SendHeartResponse(
    val data: Data = Data()
){
    data class Data(
        val message:String = ""
    )
}