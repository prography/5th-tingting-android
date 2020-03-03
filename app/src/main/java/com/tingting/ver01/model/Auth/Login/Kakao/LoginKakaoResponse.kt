package com.tingting.ver01.model.Auth.Login.Kakao

import androidx.annotation.Keep


@Keep
data class LoginKakaoResponse(
          val `data`: Data = Data()
) {
          @Keep
          data class Data(
                    val message: String = "", // 로그에 성공했습니다.
                    val token: String = "" // eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6bnVsbCwiaWF0IjoxNTc4MDU1NjA1LCJleHAiOjE1ODE2NTU2MDUsImlzcyI6InRpbmd0aW5nIn0.eI5xfSC5bJJqawNBm2rXCkH1k1hITg9Wzz1Aq1F84b4
          )
}