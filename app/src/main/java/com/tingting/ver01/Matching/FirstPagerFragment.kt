package com.tingting.ver01.Matching

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.tingting.ver01.R
import kotlinx.android.synthetic.main.fragment_matching_viewpager.view.*

class FirstPagerFragment() : Fragment() {

    var image: String? = null
    var age: String? = null
    var height: String? = null
    var name: String? = null
    var school: String? = null
    var intro: String? = null

    constructor(image: String, name: String, age: String, height: String, school: String) : this() {

        this.image = image
        this.name = name
        this.age = age
        this.height = height
        this.school = school
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_matching_viewpager, null)
        view.teammemberName.setText(name + ", " + age)
        view.teammemberHeight.setText(height)
        view.teammemberSchool.setText("학교 : " + school)

        Glide.with(view.profileImage).load(image).into(view.profileImage)

        // 처리
        return view
    }

}