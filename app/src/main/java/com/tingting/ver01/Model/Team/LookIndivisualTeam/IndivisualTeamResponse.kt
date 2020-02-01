package com.tingting.ver01.Model.Team.LookIndivisualTeam
import androidx.annotation.Keep


@Keep
data class IndivisualTeamResponse(
          val `data`: Data = Data()
) {
          @Keep
          data class Data(
                    val teamInfo: TeamInfo = TeamInfo(),
                    val teamMembers: List<TeamMember> = listOf()
          ) {
                    @Keep
                    data class TeamInfo(
                              val chat_address: String = "", // sjeifsj/isjfisjefisjif
                              val gender: Int = 0, // 1
                              val intro: String = "", // 저희는여자팀
                              val is_verified: Int = 0, // 1
                              val max_member_number: Int = 0, // 2
                              val name: String = "", // 여자팀3
                              val owner_id: Int = 0 // 45
                    )

                    @Keep
                    data class TeamMember(
                              val id: Int = 0, // 45
                              val name: String = "", // 핀핀
                              val thumbnail: String = "" // d23211123fffv.png
                    )
          }
}