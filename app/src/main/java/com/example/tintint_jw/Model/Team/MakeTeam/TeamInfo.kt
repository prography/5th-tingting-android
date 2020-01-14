package com.example.tintint_jw.Model.Team.MakeTeam

data class TeamInfo(val name:String, val chat_address:String, val owner_id :Int, val intro:String, val gender:Int,
                    val password:String, val max_member_number : Int, val is_verified:Int)