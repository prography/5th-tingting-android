package com.example.tintint_jw.Model.Team.JoinTeam
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