package com.tingting.ver01.profileTeamInfo.profileTeamInfoNotReady

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.tingting.ver01.BR
import com.tingting.ver01.model.team.lookMyTeamInfoDetail.LookMyTeamInfoDetailResponse
import com.tingting.ver01.viewModel.ProfileTeamInfoViewModel
import kotlinx.android.synthetic.main.recyclerview_team_info.view.*
import kotlinx.android.synthetic.main.recyclerview_team_info.view.position
import kotlinx.android.synthetic.main.recyclerview_team_info.view.profile
import kotlinx.android.synthetic.main.searchteam_info_member.view.*

class ProfileTeamInfoNotReadyHolder constructor(private val dataBinding: ViewDataBinding, profileTeamInfoViewModel: ProfileTeamInfoViewModel)  : RecyclerView.ViewHolder(dataBinding.root){


    val profileImg = itemView.profile
    val id = itemView.memberName
    val position = itemView.position

    fun setUp(data : LookMyTeamInfoDetailResponse.Data.TeamMember){
        dataBinding.setVariable(BR.notReadyItem, data)
        dataBinding.executePendingBindings()
    }

}