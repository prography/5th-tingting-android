package com.tingting.ver01.model.Matching

class ReceiveHeartResponse(val data: Data = Data()
){
    data class Data(
        val message:String = ""
    )
}