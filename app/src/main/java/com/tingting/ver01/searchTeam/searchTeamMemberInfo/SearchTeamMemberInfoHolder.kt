package com.tingting.ver01.searchTeam.searchTeamMemberInfo

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.tingting.ver01.BR
import com.tingting.ver01.model.team.lookIndivisualTeam.IndivisualTeamResponse
import com.tingting.ver01.viewModel.TeamInfoActivityViewModel
import kotlinx.android.synthetic.main.recyclerview_team_info.view.position
import kotlinx.android.synthetic.main.recyclerview_team_info.view.profile
import kotlinx.android.synthetic.main.searchteam_info_member.view.*

class SearchTeamMemberInfoHolder constructor(var dataBinding: ViewDataBinding , var searchTeamInfoActivityViewModel: TeamInfoActivityViewModel) : RecyclerView.ViewHolder(dataBinding.root){

    val position = itemView.position
    val profileImg = itemView.profile
    val memberName = itemView.memberName

    fun setup(item : IndivisualTeamResponse.Data.TeamMember){
        dataBinding.setVariable(BR.searchTeamItem, item)
        dataBinding.executePendingBindings()
    }
}