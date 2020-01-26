package com.example.tintint_jw.Model.Profile
import androidx.annotation.Keep


@Keep
data class GetTeammberProfileResponse(
          val `data`: Data = Data()
) {
          @Keep
          data class Data(
                    val userInfo: UserInfo = UserInfo()
          ) {
                    @Keep
                    data class UserInfo(
                              val birth: String = "", // 1993-07-07
                              val gender: Int = 0, // 1
                              val height: Int = 0, // 196
                              val name: String = "", // 퉁퉁
                              val schoolName: String = "", // 네이버대학교
                              val thumbnail: String = "" // sfsefsfsvdsv.png
                    )
          }
}