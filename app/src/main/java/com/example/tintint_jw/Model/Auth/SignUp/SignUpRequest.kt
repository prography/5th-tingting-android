package com.example.tintint_jw.Model.Auth.SignUp

import retrofit2.http.Headers


data class SignUpRequest(val local_id:String, val password :String, val gender:String,
                         val name:String, val birth:String, val thumbnail:String, val authenticated_address : String, val height:String)
