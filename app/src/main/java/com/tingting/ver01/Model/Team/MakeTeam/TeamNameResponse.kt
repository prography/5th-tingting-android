package com.tingting.ver01.Model.Team.MakeTeam
import androidx.annotation.Keep


@Keep
data class TeamNameResponse(
          val `data`: Data = Data()
) {
          @Keep
          data class Data(
                    val message: String = ""
          )
}