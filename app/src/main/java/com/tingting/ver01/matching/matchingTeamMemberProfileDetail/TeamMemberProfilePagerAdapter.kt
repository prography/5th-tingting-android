package com.tingting.ver01.matching.matchingTeamMemberProfileDetail

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.tingting.ver01.model.team.lookIndivisualTeam.IndivisualTeamResponse

class TeamMemberProfilePagerAdapter  : FragmentPagerAdapter {

    private val list: ArrayList<TeamMemeberProfilePager> = ArrayList()


    constructor(fragmentManager: FragmentManager, item : IndivisualTeamResponse) : super(fragmentManager) {
        Log.d("viewModeldataCheck", item.data.teamMembers.size.toString())


        for( i in 0..item.data.teamMembers.size-1){
            var id = item.data.teamMembers.get(item.data.teamMembers.size-1-i).id

            Log.d("viewModeldataCheck", id.toString())
            list.add(TeamMemeberProfilePager(id))
        }

    }

    override fun getItem(position: Int): Fragment {
        return list.get(position)
    }

    override fun getCount(): Int {
        return list.size
    }

}