package com.tingting.ver01.SearchTeam.RecylcerViewLoad

import androidx.recyclerview.widget.DiffUtil
import com.tingting.ver01.Model.Team.LookTeamList.TeamResponse


//이 부분 수정 필요.
class TeamDiffUtilCallback() :DiffUtil.ItemCallback<TeamResponse>(){
    override fun areItemsTheSame(oldItem: TeamResponse, newItem: TeamResponse): Boolean {
        return oldItem.data.teamList ==newItem.data.teamList
    }

    override fun areContentsTheSame(oldItem: TeamResponse, newItem: TeamResponse): Boolean {
        return oldItem.data.teamList ==newItem.data.teamList
    }
}