package com.tingting.ver01.teamInfo

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.tingting.ver01.BR
import com.tingting.ver01.model.team.lookMyTeamInfoDetail.LookMyTeamInfoDetailResponse
import com.tingting.ver01.viewModel.ProfileTeamInfoViewModel
import kotlinx.android.synthetic.main.profile_team_info_item.view.*

class ProfileTeamInfoMatchingStatusHolder constructor(private val dataBinding: ViewDataBinding, private val viewmodel : ProfileTeamInfoViewModel)
    : RecyclerView.ViewHolder(dataBinding.root)
{

    val okBtn = itemView?.okBtn
    val cancelBtn = itemView?.cancelBtn

    //데이터 자료형
    fun setUp(itemData : LookMyTeamInfoDetailResponse.Data.TeamMatching ){
        dataBinding.setVariable(BR.searchTeamItem,itemData)
        dataBinding.executePendingBindings()
    }




}