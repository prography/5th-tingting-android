package com.example.tintint_jw.Model.Team.LookTeamList
import androidx.annotation.Keep


@Keep
data class TeamResponse(
          val `data`: Data = Data()
) {
          @Keep
          data class Data(
                    val teamList: List<Team> = listOf()
          ) {
                    @Keep
                    data class Team(
                              val id: Int = 0, // 15
                              val max_member_number: Int = 0, // 4
                              val name: String = "", // 여자팀3
                              val owner_id: Int = 0, // 28
                              val teamMembersInfo: List<TeamMembersInfo> = listOf()
                    ) {
                              @Keep
                              data class TeamMembersInfo(
                                        val id: Int = 0, // 20
                                        val name: String = "", // 탕탕
                                        val thumbnail: String = "" // fjfjfjtttt22sedsv.png
                              )
                    }
          }
}