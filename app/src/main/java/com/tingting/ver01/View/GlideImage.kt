package com.tingting.ver01.View

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.request.RequestOptions
import com.tingting.ver01.sharedPreference.App

class GlideImage {

     fun DecryptUrl(thumnail :String) : GlideUrl{
        val glideUrl = GlideUrl(thumnail) { mapOf(Pair("Authorization", App.prefs.myToken.toString())) }
         return glideUrl
    }

//     fun setImage(context:Context, glideUrl: GlideUrl ,view : ImageView ){
//        Glide.with(context).load(glideUrl).signature(ObjectKey((System.currentTimeMillis()))).apply(RequestOptions.circleCropTransform()).into(view)
//    }

    fun setImage(context:Context, glideUrl: GlideUrl ,view : ImageView ){

        Glide.with(context).load(glideUrl).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).apply(RequestOptions.circleCropTransform()).into(view)
    }
    fun setImageAdapter(context:View, glideUrl: GlideUrl ,view :ImageView ){

        Glide.with(context).load(glideUrl).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).apply(RequestOptions.circleCropTransform()).into(view)
    }

}