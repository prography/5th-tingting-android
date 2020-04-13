package com.tingting.ver01.model.team.lookMyTeamInfoDetail

import androidx.annotation.Keep

@Keep
data class LookTeamTagResponse(
    val `data` : Data = Data()
){
    @Keep
    data class Data(val tags : List<Tags> = listOf()){
        @Keep
        data class Tags(
            val id:Int,         // 1
            val name:String     // "술고래"
        )
    }
}