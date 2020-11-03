package com.tingting.ver01.model.team.MakeTeam

data class MakeTeamRequest(
    val gender:Int, val name:String, val place:String,
    val password:String?,
    val maxMemberNumber:Int,val tagIds : ArrayList<Int>)