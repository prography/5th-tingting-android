package com.tingting.ver01.model

import com.tingting.ver01.model.Auth.Findidpw.FindPwResponse

interface PwCallback {

    fun pwResponse(data:FindPwResponse)
    fun onSuccess(code:Int)
}