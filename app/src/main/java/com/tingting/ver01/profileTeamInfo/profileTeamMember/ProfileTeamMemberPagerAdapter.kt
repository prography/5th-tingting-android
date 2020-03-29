package com.tingting.ver01.profileTeamInfo.profileTeamMember

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.tingting.ver01.model.team.lookIndivisualTeam.IndivisualTeamResponse

class ProfileTeamMemberPagerAdapter  : FragmentPagerAdapter {

    private val list: ArrayList<ProfileTeamMemberpager> = ArrayList()


    constructor(fragmentManager: FragmentManager, item : IndivisualTeamResponse) : super(fragmentManager) {
        Log.d("viewModeldataCheck", item.data.teamMembers.size.toString())


        for( i in 0..item.data.teamMembers.size-1){
            var id = item.data.teamMembers.get(item.data.teamMembers.size -i -1).id

            Log.d("viewModeldataCheck", id.toString())
            list.add(ProfileTeamMemberpager(id))
        }

    }

    override fun getItem(position: Int): Fragment {
        return list.get(position)
    }

    override fun getCount(): Int {
        return list.size
    }

}