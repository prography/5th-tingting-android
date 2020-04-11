package com.tingting.ver01.model.matching

import androidx.annotation.Keep

@Keep
data class ShowAppliedTeamInfoResponse(
    val data: Data = Data()
) {
    @Keep
    data class Data(
        val message: String = "",
        val teamInfo: TeamInfo = TeamInfo(),
        val teamMembers: List<TeamMember> = listOf()
    ) {
        @Keep
        data class TeamInfo(
            val id:Int = 0,
            val name: String = "", //남자팀1
            val chat_address: String = "", //kakao/soeijfsif
            val owner_id: Int = 0,    //25
            val intro: String = "",    //저희팀은 정말 귀엽습니다
            val gender: Int = 0,      //0
            val place:String = "",      // 서울
            val max_member_number: Int = 0, //2
            val is_verified: Int = 0    // 1
        )

        @Keep
        data class TeamMember(
            val id: Int = 0,  //30
            val name: String = "",     //펭펭
            val thumbnail: String = "" //sfasdxc.jpg

        )
    }
}