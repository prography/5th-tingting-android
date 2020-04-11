package com.tingting.ver01.model.matching

class ReceiveHeartResponse(val data: Data = Data()
){
    data class Data(
        val message:String = ""
    )
}