package com.tingting.ver01.ProfileTeamInfo.profileApply

import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.tingting.ver01.model.profile.GetProfileResponse
import com.tingting.ver01.viewModel.ProfileFragmentViewModel
import kotlinx.android.synthetic.main.profile_request_answer_item.view.*

class ProfileResponseReHolder constructor(var dataBinding: ViewDataBinding, var profileFragmentViewModel: ProfileFragmentViewModel) : RecyclerView.ViewHolder(dataBinding.root){



    //변수찾고
    val okBtn = itemView?.ok
    val cancelBtn = itemView?.cancel


    fun setUp(item:GetProfileResponse.Data.SentMatchings){
        dataBinding.setVariable(BR.applyItem,item)
        dataBinding.executePendingBindings()

    }
}