package com.example.tintint_jw.Matching

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class PagerAdapter : FragmentPagerAdapter {

    private val list : ArrayList<Fragment> = ArrayList();

    constructor(fragmentManager: FragmentManager) : super(fragmentManager) {
        // 함수로 사람들 수에 따라서 맞게 불러 오도록 설정.
        list.add(FirstPagerFragment())
        list.add(SecondPagerFragment())
        list.add(ThirdPagerFragment())
        list.add(ForthPagerFragment())
    }

    /*override fun getPageTitle(position: Int): CharSequence? {
        return list[position].title();
    }*/

    override fun getItem(position: Int): Fragment {

        return list.get(position)
    }

    override fun getCount(): Int {
        return list.size
    }
}