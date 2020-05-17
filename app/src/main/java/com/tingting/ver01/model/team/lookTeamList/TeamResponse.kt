package com.tingting.ver01.model.team.lookTeamList
import androidx.annotation.Keep


@Keep
data class TeamResponse(
          val `data`: Data = Data()
) {
          @Keep
          data class Data(
                    val teamList: ArrayList<Team> = ArrayList()
          ) {
                    @Keep
                    data class Team(
                              val id: Int = 0, // 15
                              val max_member_number: Int = 0, // 4
                              val name: String = "", // 여자팀3
                              val owner_id: Int = 0, // 28
                              val place:String = "", // 서울
                              val hasPassword:Boolean = false,
                              val teamMembersInfo: List<TeamMembersInfo> = listOf(),
                              val tags:List<String> = listOf()
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