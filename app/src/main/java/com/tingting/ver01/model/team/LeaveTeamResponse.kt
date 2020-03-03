package com.tingting.ver01.model.team
import androidx.annotation.Keep


@Keep
data class LeaveTeamResponse(
          val `data`: Data = Data()
) {
          @Keep
          data class Data(
                    val message: String = ""
                 
          )
}