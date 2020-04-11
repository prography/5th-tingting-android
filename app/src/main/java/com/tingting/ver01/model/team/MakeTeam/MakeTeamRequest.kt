package com.tingting.ver01.model.team.MakeTeam

data class MakeTeamRequest(
    val gender:Int, val name:String, val place:String,
    val password:String?,
    val max_member_number:Int,val intro : String,
    val chat_address:String)