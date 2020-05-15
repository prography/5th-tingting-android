package com.tingting.ver01.searchTeam.searchTeamMemberInfo

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tingting.ver01.R
import com.tingting.ver01.view.Main.MainActivity
import com.tingting.ver01.databinding.SearchteamInfoMemberBinding
import com.tingting.ver01.matching.matchingTeamMember.MatchingTeamMemberInfoHolder
import com.tingting.ver01.matching.matchingTeamMemberProfileDetail.MatchingDetail
import com.tingting.ver01.model.matching.ShowMatchingTeamInfoResponse
import com.tingting.ver01.model.team.lookIndivisualTeam.IndivisualTeamResponse
import com.tingting.ver01.searchTeam.SearchTeamInfoDetailActivity
import com.tingting.ver01.viewModel.MatchingApplyTeamInfoViewModel
import com.tingting.ver01.viewModel.TeamInfoActivityViewModel

class MatchingApplyTeamInfoAdapter(val matchTeamInfoActivityViewModel: MatchingApplyTeamInfoViewModel, val context : Context, val matchingId:Int) :
    RecyclerView.Adapter<MatchingTeamMemberInfoHolder>() {

    var data: List<ShowMatchingTeamInfoResponse.Data.TeamMember> = emptyList()
    lateinit  var data2: ShowMatchingTeamInfoResponse.Data.TeamInfo
    var owner = 0;
    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchingTeamMemberInfoHolder {
        var inflater = LayoutInflater.from(parent.context)
        var databinding = SearchteamInfoMemberBinding.inflate(inflater, parent, false)

        return MatchingTeamMemberInfoHolder(databinding, matchTeamInfoActivityViewModel)
    }

    override fun onBindViewHolder(holder: MatchingTeamMemberInfoHolder, position: Int) {
        holder.setup(data[itemCount -1 -position])

        //teamInfoDetail로
        holder.itemView.setOnClickListener {
            var intent = Intent(context, MatchingDetail::class.java)
            intent.putExtra("MatchingTeamId", matchingId)
            context.startActivity(intent)

        }
        owner = data2.owner_id;

        if(owner == data[itemCount - 1 -position].id){
            holder.position.text = "팀장"
            holder.position.setBackgroundResource(R.drawable.team_member_position_button1)
        }else{
            holder.position.text = "팀원"
            holder.position.setBackgroundResource(R.drawable.team_member_position_button2)
        }

        //set Profile Image
        MainActivity.glide.setImage(holder.profileImg.context,
            MainActivity.glide.DecryptUrl(data[itemCount -1 -position].thumbnail),holder.profileImg)

        holder.memberName.text = data[itemCount -1 -position].name

    }

    fun update(data : ShowMatchingTeamInfoResponse ){
        this.data = data.data.teamMembers
        this.data2 = data.data.teamInfo
        notifyDataSetChanged()
    }

}