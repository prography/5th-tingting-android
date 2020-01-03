package com.example.tintint_jw.Model.Team.MakeTeam

data class MakeTeamRequest(val owner_id:String, val password:String, val gender:Int, val name:String,
                           val max_member_number:Int,val intro : String, val chat_address:String)