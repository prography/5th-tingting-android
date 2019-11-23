package com.example.tintint_jw.Toolbar

import android.annotation.SuppressLint
import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.tintint_jw.R
import kotlin.coroutines.coroutineContext

class BackToolbar {

    @SuppressLint("ResourceType")
    public constructor(activity: AppCompatActivity, title:String, customtoolbar:Int) {

        val toolbar = activity.findViewById<Toolbar>(customtoolbar)
       // val back = activity.findViewById<>()
        activity.setSupportActionBar(toolbar)
//        activity.supportActionBar!!.setLogo(R.drawable.back)
//        activity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }


}
