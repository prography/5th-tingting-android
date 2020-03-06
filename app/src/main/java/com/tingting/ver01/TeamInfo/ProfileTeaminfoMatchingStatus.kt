package com.tingting.ver01.TeamInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tingting.ver01.R

class ProfileTeaminfoMatchingStatus :Fragment(){


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val a = inflater.inflate(R.layout.fragment_profile_teaminfo_matching_status,container,false)

        return a;
    }
}