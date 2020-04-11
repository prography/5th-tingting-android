package com.tingting.ver01.dataBase

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tingting.ver01.model.profile.GetProfileResponse

@Keep

@Entity
data class profileFragmentDataBase(
    val `data`: Data = Data()
) {
    @Keep
    data class Data(
        val myInfo: MyInfo = MyInfo(),
        val myTeamList: List<MyTeam> = listOf(),
        val sentMatchings:List<SentMatchings> = listOf()
    ) {
        @Keep
        data class MyInfo(
            val birth: String = "", // 1999-09-15
            val gender: Int = 0, // 1
            val height: Int = 0, // 188
            @PrimaryKey
            val name: String = "", // 틴틴
            val schoolName: String = "", // 한양대학교
            val thumbnail: String = "" // d2323223fffv.png
        )

        @Keep
        data class MyTeam(
            val id: Int = 0, // 13
            val name: String = "", // 얌얌
            val max_member_number:Int = 0,
            val is_ready : Boolean = false
        )

        @Keep
        data class SentMatchings(
            val id:Int = 0,
            val created_at:String = "",
            val sendTeam:SendTeam = SendTeam(),
            val receiveTeam:ReceiveTeam = ReceiveTeam()
        ){
            @Keep
            data class SendTeam(
                val id:Int = 0,
                val name: String = ""
            )
            @Keep
            data class ReceiveTeam(
                val id:Int=0,
                val name:String = ""
            )
        }
    }
}

fun profileFragmentDataBase.asDomainModel() : GetProfileResponse{
    return GetProfileResponse(this.data as GetProfileResponse.Data)
}

