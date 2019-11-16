package com.example.tintint_jw.Matching

import android.graphics.drawable.Drawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.tintint_jw.R
import kotlinx.android.synthetic.main.fragment_matching_viewpager.view.*

class TeamInfoPagerAdapter : FragmentPagerAdapter {

    private val list: ArrayList<FirstPagerFragment> = ArrayList();

    constructor(fragmentManager: FragmentManager) : super(fragmentManager) {

        //서버로부터 정보 받아와서 개수만큼 뿌려줌.
        for(i in 1..4){
            list.add(FirstPagerFragment(R.drawable.iu, "iu", "27", "168"))
        }

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