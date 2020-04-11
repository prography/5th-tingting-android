package com.tingting.ver01.ApplyTeamInfo

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tingting.ver01.R
import com.tingting.ver01.view.Main.MainActivity
import com.tingting.ver01.databinding.SearchteamInfoMemberBinding
import com.tingting.ver01.model.matching.ShowAppliedTeamInfoResponse
import com.tingting.ver01.model.team.lookIndivisualTeam.IndivisualTeamResponse
import com.tingting.ver01.profileTeamInfo.profileApply.OtherTeamInfoDetailActivity
import com.tingting.ver01.searchTeam.SearchTeamInfoDetailActivity
import com.tingting.ver01.viewModel.ApplyTeamInfoViewModel
import com.tingting.ver01.viewModel.TeamInfoActivityViewModel

class ApplyTeamInfoAdapter(val searchTeamInfoActivityViewModel: ApplyTeamInfoViewModel, val context : Context) :
    RecyclerView.Adapter<ApplyTeamInfoHolder>() {

    var data: List<ShowAppliedTeamInfoResponse.Data.TeamMember> = emptyList()
    lateinit  var data2: ShowAppliedTeamInfoResponse.Data.TeamInfo

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplyTeamInfoHolder {
        var inflater = LayoutInflater.from(parent.context)
        var databinding = SearchteamInfoMemberBinding.inflate(inflater, parent, false)
        return ApplyTeamInfoHolder(databinding, searchTeamInfoActivityViewModel)
    }

    override fun onBindViewHolder(holder: ApplyTeamInfoHolder, position: Int) {
        holder.setup(data[itemCount -1 -position])

        //teamInfoDetail로
        holder.itemView.setOnClickListener {
            var intent = Intent(context, OtherTeamInfoDetailActivity::class.java)
            intent.putExtra("MyTeamId", data2.owner_id)
            context.startActivity(intent)

        }

        if(position==0){
            holder.position.text = "팀장"
            holder.position.setBackgroundResource(R.drawable.button1)
        }else{
            holder.position.text = "팀원"
            holder.position.setBackgroundResource(R.drawable.button2)
        }

        //set Profile Image
        MainActivity.glide.setImage(holder.profileImg.context,
            MainActivity.glide.DecryptUrl(data[itemCount -1 -position].thumbnail),holder.profileImg)

        holder.memberName.text = data[itemCount -1 -position].name

    }

    fun update(data :ShowAppliedTeamInfoResponse.Data ){
        this.data = data.teamMembers
        this.data2 = data.teamInfo
        notifyDataSetChanged()
    }

}