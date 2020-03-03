package com.tingting.ver01.model.profile
import androidx.annotation.Keep


@Keep
data class PatchProfileResponse(
          val errorMessage: String = "" // 내정보 수정하기 실패
)