package com.example.tintint_jw.Matching

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.tintint_jw.R
import kotlinx.android.synthetic.main.fragment_matching_viewpager.view.*

class FirstPagerFragment() : Fragment() {

    var image: Int ?= null
    var age:String ?= null
    var height:String ?= null

    constructor(image: Int, name: String, age: String, height: String) : this() {
        this.image=image
        this.age = age
        this.height=height
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_matching_viewpager, null)
        Glide.with(view.profileImage).load(image).into(view.profileImage)

        // 처리
        return view
    }

}