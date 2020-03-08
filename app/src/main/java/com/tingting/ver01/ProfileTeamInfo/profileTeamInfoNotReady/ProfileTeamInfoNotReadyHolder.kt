package com.tingting.ver01.ProfileTeamInfo.profileTeamInfoNotReady

import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.tingting.ver01.model.team.lookMyTeamInfoDetail.LookMyTeamInfoDetailResponse
import com.tingting.ver01.viewModel.ProfileTeamInfoViewModel
import kotlinx.android.synthetic.main.recyclerview_team_info.view.*

class ProfileTeamInfoNotReadyHolder constructor(private val dataBinding: ViewDataBinding, profileTeamInfoViewModel: ProfileTeamInfoViewModel)  : RecyclerView.ViewHolder(dataBinding.root){


    val profileImg = itemView?.profile
    val id = itemView?.id
    val position = itemView?.position

    fun setUp(data : LookMyTeamInfoDetailResponse.Data.TeamMember){
        dataBinding.setVariable(BR.notReadyItem, data)
        dataBinding.executePendingBindings()
    }

}