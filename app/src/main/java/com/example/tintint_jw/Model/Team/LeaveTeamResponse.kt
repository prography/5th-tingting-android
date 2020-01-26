package com.example.tintint_jw.Model.Team
import androidx.annotation.Keep


@Keep
data class LeaveTeamResponse(
          val `data`: Data = Data()
) {
          @Keep
          data class Data(
                    val message: String = "" // 팀장 권한으로 팀 제거 완료(팀 채널)
          )
}