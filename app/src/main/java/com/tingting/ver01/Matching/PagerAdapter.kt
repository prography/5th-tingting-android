package com.tingting.ver01.Matching

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class PagerAdapter : FragmentPagerAdapter {

    private val list: ArrayList<FirstPagerFragment> = ArrayList();

    constructor(fragmentManager: FragmentManager) : super(fragmentManager) {

        //서버로부터 정보 받아와서 개수만큼 뿌려줌.
    }


    override fun getItem(position: Int): Fragment {

        return list.get(position)
    }

    override fun getCount(): Int {
        return list.size
    }

    fun addData(data : FirstPagerFragment){

        list.add(data)
    }


}

