package com.tingting.ver01.model.matching

data class SendMessage(
    val receiveTeamId:Int, val sendTeamId:Int, val message:String)