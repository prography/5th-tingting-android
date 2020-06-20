package com.tingting.ver01.view

import android.R
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.signature.ObjectKey
import com.tingting.ver01.sharedPreference.App
import com.tingting.ver01.view.Auth.PictureRegisterActivity


class GlideImage {
    companion object{

        var sign = Math.random();

    }

     fun DecryptUrl(thumnail :String) : GlideUrl{



            val glideUrl = GlideUrl(thumnail) { mapOf(Pair("Authorization", App.prefs.myToken.toString())) }


         return glideUrl

     }


    fun setImage(context:Context, glideUrl: GlideUrl ,view : ImageView ){


       Glide.with(context).load(glideUrl).signature(ObjectKey(sign)).apply(RequestOptions.circleCropTransform()).into(view)

}

    fun setImageProfileActivity(context:Context, glideUrl: GlideUrl ,view : ImageView ){


            Glide.with(context).load(glideUrl).signature(ObjectKey(sign)).apply(RequestOptions.circleCropTransform()).listener(object :RequestListener<Drawable>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.d("profileActivityImage","Test")
                    val intent = Intent(context, PictureRegisterActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    Toast.makeText(context,"등록 된 사진이 없습니다. 사진을 등록해주시길 바랍니다.", Toast.LENGTH_SHORT).show()

                    context.startActivity(intent)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                     view.setImageDrawable(resource)
                    return true
                }
            }).into(view)



    }
    fun setImageAdapter(context:View, glideUrl: GlideUrl ,view :ImageView ){

        Glide.with(context).load(glideUrl).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).apply(RequestOptions.circleCropTransform()).into(view)
    }

    fun setSquareImage(context:Context, glideUrl: GlideUrl ,view : ImageView ){


        Glide.with(context).load(glideUrl).signature(ObjectKey(sign)).into(view)

    }



}