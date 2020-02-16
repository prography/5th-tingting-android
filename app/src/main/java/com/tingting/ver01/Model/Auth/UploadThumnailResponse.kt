package com.tingting.ver01.Model.Auth

import androidx.annotation.Keep


@Keep
data class UploadThumnailResponse(
    val `data`: Data = Data()
) {
    @Keep
    data class Data(
        val message: String = "" // 이미지 저장에 성공하였습니다.
    )
}