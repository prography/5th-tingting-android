package com.tingting.ver01.teamInfo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tingting.ver01.ProfileTeamInfo.profileTeamInfoNotReady.ProfileTeamInfoNotReadyHolder
import com.tingting.ver01.R
import com.tingting.ver01.View.Main.MainActivity
import com.tingting.ver01.databinding.RecyclerviewTeamInfoBinding
import com.tingting.ver01.model.team.lookMyTeamInfoDetail.LookMyTeamInfoDetailResponse
import com.tingting.ver01.viewModel.ProfileTeamInfoViewModel


class ProfileTeamInfoNotReadyRecyclerViewAdapter(private val profileTeamInfoViewModel: ProfileTeamInfoViewModel) :
    RecyclerView.Adapter<ProfileTeamInfoNotReadyHolder>() {

    var data: List<LookMyTeamInfoDetailResponse.Data.TeamMember> = emptyList()


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProfileTeamInfoNotReadyHolder {

        var inflater = LayoutInflater.from(parent.context)
        val dataBinding = RecyclerviewTeamInfoBinding.inflate(inflater, parent, false)

        return ProfileTeamInfoNotReadyHolder(dataBinding, profileTeamInfoViewModel)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ProfileTeamInfoNotReadyHolder, position: Int) {
         holder.setUp(data[position])

        //set teamMemberPosition
        if(position==0){
            holder.position.setText("팀장")
            holder.position.setBackgroundResource(R.drawable.button1)
        }else{
            holder.position.setText("팀원")
            holder.position.setBackgroundResource(R.drawable.button2)
        }

        //set Profile Image
        MainActivity.glide.setImage(holder.profileImg.context,MainActivity.glide.DecryptUrl(data[position].thumbnail),holder.profileImg)

    }

    fun updateData(data :LookMyTeamInfoDetailResponse ){
        this.data = data.data.teamMembers
        notifyDataSetChanged()
    }
}