package com.tingting.ver01.view.Auth.FindIdAndPw

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.tingting.ver01.view.Auth.FindIdAndPw.findid
import com.tingting.ver01.view.Auth.FindIdAndPw.findpw

class AccountPagerAdapter(fm:FragmentManager) : FragmentPagerAdapter(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    val MAX_PAGE = 2

    override fun getItem(position: Int): Fragment {
        val fragment = when(position){
            0-> findid()
            1-> findpw()
            else-> findid()
        }
        return fragment
    }

    override fun getCount(): Int {
        return MAX_PAGE
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val title = when(position){
            0->"아이디 찾기"
            1->"비밀번호 찾기"
            else->""
        }
        return title

    }
}