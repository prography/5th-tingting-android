package com.tingting.ver01.model.Matching

data class SendMessage(
    val receiveTeamId:Int, val sendTeamId:Int, val message:String)