package com.tingting.ver01.model.matching
import androidx.annotation.Keep


@Keep
data class ShowAllCandidateListResponse(
          val `data`: Data = Data()
) {
          @Keep
          data class Data(
                    val matchingList: List<Matching> = listOf(),
                    val myTeamList: List<MyTeam> = listOf()
          ) {
                    @Keep
                    data class Matching(
                              val id: Int = 0, // 16
                              val max_member_number: Int = 0, // 2
                              val membersInfo: List<MembersInfo> = listOf(),
                              val name: String = "", // 남자팀3
                              val owner_id: Int = 0, // 28
                              val place:String = "",
                              val tags:List<String> = listOf()
                    ) {
                              @Keep
                              data class MembersInfo(
                                        val id: Int = 0, // 28
                                        val name: String = "", // 팡팡
                                        val thumbnail: String = "" // s222fsvsefsedsv.png
                              )
                    }

                    @Keep
                    data class MyTeam(
                              val id: Int = 0, // 13
                              val max_member_number: Int = 0, // 4
                              val name: String = "" // 여자팀2
                    )
          }
}