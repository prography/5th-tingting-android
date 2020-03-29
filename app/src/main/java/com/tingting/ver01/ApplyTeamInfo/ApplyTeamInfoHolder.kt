package com.tingting.ver01.ApplyTeamInfo

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.tingting.ver01.BR
import com.tingting.ver01.model.matching.ShowAppliedTeamInfoResponse
import com.tingting.ver01.model.team.lookIndivisualTeam.IndivisualTeamResponse
import com.tingting.ver01.viewModel.ApplyTeamInfoViewModel
import com.tingting.ver01.viewModel.TeamInfoActivityViewModel
import kotlinx.android.synthetic.main.recyclerview_team_info.view.position
import kotlinx.android.synthetic.main.recyclerview_team_info.view.profile
import kotlinx.android.synthetic.main.searchteam_info_member.view.*

class ApplyTeamInfoHolder constructor(var dataBinding: ViewDataBinding , var applyTeamInfoActivityViewModel: ApplyTeamInfoViewModel) : RecyclerView.ViewHolder(dataBinding.root){

    val position = itemView.position
    val profileImg = itemView.profile
    val memberName = itemView.memberName

    fun setup(item : ShowAppliedTeamInfoResponse.Data.TeamMember){
        dataBinding.setVariable(BR.applyTeamInfoItem, item)
        dataBinding.executePendingBindings()
    }
}