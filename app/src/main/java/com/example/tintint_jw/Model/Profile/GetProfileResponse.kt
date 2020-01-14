
import androidx.annotation.Keep
@Keep
data class GetProfileResponse(
          val `data`: Data = Data()
) {
          @Keep
          data class Data(
                    val myInfo: MyInfo = MyInfo(),
                    val myTeamList: List<MyTeam> = listOf()
          ) {
                    @Keep
                    data class MyInfo(
                              val birth: String = "", // 1999-09-15
                              val gender: Int = 0, // 1
                              val height: Int = 0, // 188
                              val is_deleted: Int = 0, // 0
                              val name: String = "", // 틴틴
                              val schoolName: String = "", // 한양대학교
                              val thumbnail: String = "" // d2323223fffv.png
                    )

                    @Keep
                    data class MyTeam(
                              val id: Int = 0, // 13
                              val name: String = "" // 얌얌
                    )
          }
}