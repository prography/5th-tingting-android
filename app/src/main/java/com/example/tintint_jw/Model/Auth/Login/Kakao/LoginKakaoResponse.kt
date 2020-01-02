package com.example.tintint_jw.Model.Auth.Login.Kakao

data class LoginKakaoResponse(
    val data: token
)

data class token(
    val accessToken:String
)