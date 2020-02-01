package com.tingting.ver01.Model.Auth.Login.Kakao


data class LoginKakaoResponse(
    val data: token
)

data class token(
    val accessToken:String
)