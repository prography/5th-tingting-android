package com.tingting.ver01.View

import android.content.Context
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.request.RequestOptions
import com.tingting.ver01.SharedPreference.App
import kotlinx.android.synthetic.main.profile_fragment.view.*

class GlideImage {

     fun DecryptUrl(thumnail :String) : GlideUrl{
        val glideUrl = GlideUrl(thumnail) { mapOf(Pair("Authorization", App.prefs.myToken.toString())) }
         return glideUrl
    }

     fun setImage(context:Context, glideUrl: GlideUrl ,view : View ){
        Glide.with(context).load(glideUrl).apply(RequestOptions.circleCropTransform()).into(view.newteamProfileImg)
    }

}