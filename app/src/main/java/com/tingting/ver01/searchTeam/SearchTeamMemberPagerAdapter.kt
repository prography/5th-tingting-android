package com.tingting.ver01.matching

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.tingting.ver01.model.team.lookIndivisualTeam.IndivisualTeamResponse
import com.tingting.ver01.searchTeam.SearchTeamMemberPager

class SearchTeamMemberPagerAdapter  : FragmentPagerAdapter {

    private val list: ArrayList<SearchTeamMemberPager> = ArrayList()


    constructor(fragmentManager: FragmentManager, item : IndivisualTeamResponse) : super(fragmentManager) {
        Log.d("viewModeldataCheck", item.data.teamMembers.size.toString())


        for( i in 0..item.data.teamMembers.size-1){

            var id = item.data.teamMembers.get(item.data.teamMembers.size-1 - i).id

            Log.d("viewModeldataCheck", id.toString())
                list.add(SearchTeamMemberPager(id))
        }

    }

    override fun getItem(position: Int): Fragment {
        return list.get(position)
    }

    override fun getCount(): Int {
        return list.size
    }

}