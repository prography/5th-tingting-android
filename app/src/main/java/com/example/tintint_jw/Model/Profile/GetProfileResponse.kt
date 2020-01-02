package com.example.tintint_jw.Model.Profile

data class GetProfileResponse(val data :Data )

data class Data(val myInfo : List<Personal>, val myTeamList : List<Int>, val school : List<School>)

data class Personal(val name:String, val birth: String, val height : String , val thumbnail : String, val gender : Int,val Is_deleted:Int)

data class School(val name : String)

