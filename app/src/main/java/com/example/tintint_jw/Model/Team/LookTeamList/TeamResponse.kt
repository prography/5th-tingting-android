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
                              val id: Int = 0, // 17
                              val owner_id: Int = 0, // 74
                              val name: String = "", // 우오오
                              val password: String? = null, // 0000
                              val max_member_number: Int = 0, // 1
                              val teamMembersInfo: List<Any> = listOf()
                    )
          }
}