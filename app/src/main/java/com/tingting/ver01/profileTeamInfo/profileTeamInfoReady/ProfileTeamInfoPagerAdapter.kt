package com.tingting.ver01.profileTeamInfo.profileTeamInfoReady

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.tingting.ver01.teamInfo.ProfileTeamInfoMatchingRecordFragment
import com.tingting.ver01.teamInfo.ProfileTeamInfoMatchingStatusFragment
import com.tingting.ver01.view.Auth.FindIdAndPw.findid
import com.tingting.ver01.view.Auth.FindIdAndPw.findpw

class ProfileTeamInfoPagerAdapter(fm:FragmentManager, var teamid:Int) : FragmentPagerAdapter(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    val MAX_PAGE = 2

    override fun getItem(position: Int): Fragment {
        val fragment = when(position){
            0-> ProfileTeamInfoMatchingStatusFragment(teamid)
            1-> ProfileTeamInfoMatchingRecordFragment()
            else-> ProfileTeamInfoMatchingStatusFragment(teamid)
        }
        return fragment
    }

    override fun getCount(): Int {
        return MAX_PAGE
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val title = when(position){
            0->{ "매칭 현황 " }


            1->"매칭 기록"
            else->""
        }
        return title
    }

}