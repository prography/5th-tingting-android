package com.tingting.ver01.profileTeamInfo

import android.widget.ImageView
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.tingting.ver01.BR
import com.tingting.ver01.model.profile.GetProfileResponse
import com.tingting.ver01.viewModel.ProfileFragmentViewModel
import kotlinx.android.synthetic.main.recycler_item_profile_teaminfo.view.*

//viewHolder, Adapter 둘다 data를 사용하기 때문에 data를 보내주는 viewModel을 사용해야한다.
//ViewHolder는 dataBinding 함수를 가지고온다.
//viewHolder에서 inflate 한 변수들을 가지고온다.
class ProfileTeamInfoHolder constructor(private val dataBinding: ViewDataBinding ,val profileFragmentViewModel: ProfileFragmentViewModel)
    :RecyclerView.ViewHolder(dataBinding.root){

    val name = itemView.TeamName
    val showTeamInfo = itemView.ShowTeamInfo
    val leader: ImageView = itemView.leader
    val teamSize = itemView.teamSize

    fun setup(itemData:GetProfileResponse.Data.MyTeam){
        //BR에는 Binding한 객체의 variable 이름이 들어감
        dataBinding.setVariable(BR.teamData, itemData)
        dataBinding.executePendingBindings()

        }
    }

