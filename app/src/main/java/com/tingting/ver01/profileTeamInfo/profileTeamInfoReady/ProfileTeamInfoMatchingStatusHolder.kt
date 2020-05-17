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

    val okBtn = itemView.okBtn
    val cancelBtn = itemView.cancelBtn
    val waitingMatching = itemView.waitingMatching
    val agreeNumber = itemView.agreeNumber
    val agreeNumber2 = itemView.agreeNumber2
    val chatAddress = itemView.chatAddress
    val regionInfo = itemView.regionInfo
    val teamName = itemView.teamName

    val img1 = itemView.img_m4First
    val img2 = itemView.img_m4Sec
    val img3 = itemView.img_m4Third
    val img4 = itemView.img_m4Fourth

    //데이터 자료형
    fun setUp(itemData : LookMyTeamInfoDetailResponse.Data.TeamMatching ){
        dataBinding.setVariable(BR.searchMatchingItem, itemData)
        dataBinding.executePendingBindings()
    }




}