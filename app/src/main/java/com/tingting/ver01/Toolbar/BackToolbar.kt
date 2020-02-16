package com.tingting.ver01.Toolbar

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

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
