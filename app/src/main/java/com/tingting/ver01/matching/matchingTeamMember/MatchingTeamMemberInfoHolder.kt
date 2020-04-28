package com.tingting.ver01.matching.matchingTeamMember

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.tingting.ver01.BR
import com.tingting.ver01.model.matching.ShowMatchingTeamInfoResponse
import com.tingting.ver01.viewModel.ApplyTeamInfoViewModel
import com.tingting.ver01.viewModel.MatchingApplyTeamInfoViewModel
import kotlinx.android.synthetic.main.searchteam_info_member.view.*

class MatchingTeamMemberInfoHolder constructor(var dataBinding: ViewDataBinding, var searchTeamInfoActivityViewModel: MatchingApplyTeamInfoViewModel) : RecyclerView.ViewHolder(dataBinding.root){

    val position = itemView.position
    val profileImg = itemView.profile
    val memberName = itemView.memberName

    fun setup(item : ShowMatchingTeamInfoResponse.Data.TeamMember){
        dataBinding.setVariable(BR.searchTeamItem, item)
        dataBinding.executePendingBindings()
    }
}