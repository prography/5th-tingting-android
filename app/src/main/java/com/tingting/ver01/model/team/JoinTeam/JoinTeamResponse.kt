package com.tingting.ver01.model.team.JoinTeam
import androidx.annotation.Keep


@Keep
data class JoinTeamResponse(
          val `data`: Data = Data()
) {
          @Keep
          data class Data(
                    val message: String = "" // 합류 성공
          )
}