package com.tingting.ver01.profileTeamInfo.profileApply

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tingting.ver01.R
import com.tingting.ver01.databinding.SearchteamInfoMemberBinding
import com.tingting.ver01.model.team.lookIndivisualTeam.IndivisualTeamResponse
import com.tingting.ver01.searchTeam.searchTeamMemberInfo.SearchTeamMemberInfoHolder
import com.tingting.ver01.view.Main.MainActivity
import com.tingting.ver01.viewModel.TeamInfoActivityViewModel

class OtherTeamMemberInfoAdapter(val searchTeamInfoActivityViewModel: TeamInfoActivityViewModel, val context : Context) :
    RecyclerView.Adapter<SearchTeamMemberInfoHolder>() {

    var data: List<IndivisualTeamResponse.Data.TeamMember> = emptyList()
    lateinit  var data2: IndivisualTeamResponse.Data.TeamInfo

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchTeamMemberInfoHolder {
        var inflater = LayoutInflater.from(parent.context)
        var databinding = SearchteamInfoMemberBinding.inflate(inflater, parent, false)
        return SearchTeamMemberInfoHolder(databinding, searchTeamInfoActivityViewModel)
    }

    override fun onBindViewHolder(holder: SearchTeamMemberInfoHolder, position: Int) {
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

    fun update(data :IndivisualTeamResponse ){
        this.data = data.data.teamMembers
        this.data2 = data.data.teamInfo
        notifyDataSetChanged()
    }

}