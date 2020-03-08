package com.tingting.ver01.ProfileTeamInfo.profileTeamInfoReady

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tingting.ver01.databinding.ProfileTeamInfoItemBinding
import com.tingting.ver01.model.team.lookMyTeamInfoDetail.LookMyTeamInfoDetailResponse
import com.tingting.ver01.teamInfo.ProfileTeamInfoMatchingStatusHolder
import com.tingting.ver01.viewModel.ProfileTeamInfoViewModel

class ProfileTeamInfoMatchingStatusRecyclerAdapter(private val profileTeamInfoViewModel: ProfileTeamInfoViewModel)
    : RecyclerView.Adapter<ProfileTeamInfoMatchingStatusHolder>(){

    var teamList : List<LookMyTeamInfoDetailResponse.Data.TeamMatching> = emptyList()


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProfileTeamInfoMatchingStatusHolder {
        val inflater = LayoutInflater.from(parent.context)
        val dataBinding = ProfileTeamInfoItemBinding.inflate(inflater,parent,false)


        return ProfileTeamInfoMatchingStatusHolder(
            dataBinding,
            profileTeamInfoViewModel
        )
    }

    override fun getItemCount(): Int {
        return teamList.size
    }

    override fun onBindViewHolder(holder: ProfileTeamInfoMatchingStatusHolder, position: Int) {
            holder.setUp(teamList[position])
    }

    //데이터 업데이트
    fun updateData(teamData: LookMyTeamInfoDetailResponse){
        this.teamList = teamData.data.teamMatchings
        notifyDataSetChanged()
    }
}