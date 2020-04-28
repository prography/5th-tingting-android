package com.tingting.ver01.teamInfo

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tingting.ver01.R
import com.tingting.ver01.view.Main.MainActivity
import com.tingting.ver01.databinding.RecyclerviewTeamInfoBinding
import com.tingting.ver01.databinding.SearchteamInfoMemberBinding
import com.tingting.ver01.model.team.lookMyTeamInfoDetail.LookMyTeamInfoDetailResponse
import com.tingting.ver01.profileTeamInfo.profileTeamInfoNotReady.ProfileTeamInfoNotReadyHolder
import com.tingting.ver01.profileTeamInfo.profileTeamMember.TeamInfoProfileDetailActivity
import com.tingting.ver01.viewModel.ProfileTeamInfoViewModel


class ProfileTeamInfoNotReadyRecyclerViewAdapter(private val profileTeamInfoViewModel: ProfileTeamInfoViewModel, var context: Context) :
    RecyclerView.Adapter<ProfileTeamInfoNotReadyHolder>() {

    var data: List<LookMyTeamInfoDetailResponse.Data.TeamMember> = emptyList()
    lateinit  var data2: LookMyTeamInfoDetailResponse.Data.TeamInfo
    var owner : Int = 0;
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProfileTeamInfoNotReadyHolder {

        var inflater = LayoutInflater.from(parent.context)
        val dataBinding = SearchteamInfoMemberBinding.inflate(inflater, parent, false)

        return ProfileTeamInfoNotReadyHolder(dataBinding, profileTeamInfoViewModel)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ProfileTeamInfoNotReadyHolder, position: Int) {


        holder.setUp(data[itemCount -1 -position])

        //set teamMemberPosition
        holder.itemView.setOnClickListener {
            var intent = Intent(context, TeamInfoProfileDetailActivity::class.java)
            //여기 무슨값이 들어가야 되는지 체크
            intent.putExtra("MyTeamId",data2.id)
            context.startActivity(intent)

        }
        owner = data2.owner_id;

         holder.id.text = data[itemCount-1-position].name

        if(owner == data[itemCount - 1 -position].id){
            holder.position.setText("팀장")
            holder.position.setBackgroundResource(R.drawable.team_member_position_button1)
        }else{
            holder.position.setText("팀원")
            holder.position.setBackgroundResource(R.drawable.team_member_position_button2)
        }

        //set Profile Image
        MainActivity.glide.setImage(holder.profileImg.context,MainActivity.glide.DecryptUrl(data[itemCount -1 -position].thumbnail),holder.profileImg)

    }

    fun updateData(data :LookMyTeamInfoDetailResponse){
        this.data = data.data.teamMembers
        this.data2 = data.data.teamInfo
        notifyDataSetChanged()
    }
}