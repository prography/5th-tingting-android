package com.tingting.ver01.profileTeamInfo

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tingting.ver01.databinding.RecyclerItemProfileTeaminfoBinding
import com.tingting.ver01.model.profile.GetProfileResponse
import com.tingting.ver01.profileTeamInfo.profileTeamInfoNotReady.ProfileTeamInfoNotReadyActivity
import com.tingting.ver01.teamInfo.ProfileTeamInfoReadyActivity
import com.tingting.ver01.viewModel.ProfileFragmentViewModel

class ProflieTeamInfoAdapter(private  val profileFragmentViewModel: ProfileFragmentViewModel, context: Context) :
    RecyclerView.Adapter<ProfileTeamInfoHolder>() {

    var teamList : List<GetProfileResponse.Data.MyTeam> = emptyList()
    var data : GetProfileResponse? = null
    lateinit var context : ViewGroup
    lateinit var intent: Intent
    //position 과 view를 Adapter에서 제공해주기 때문에 이를 활용한 interface를 만드는것.

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileTeamInfoHolder {
        val inflater = LayoutInflater.from(parent.context)
        val dataBinding = RecyclerItemProfileTeaminfoBinding.inflate(inflater,parent,false)
        context = parent
         return ProfileTeamInfoHolder(dataBinding, profileFragmentViewModel)
    }

    override fun getItemCount(): Int {
        return teamList.size
    }

    override fun onBindViewHolder(holder: ProfileTeamInfoHolder, position: Int) {
        holder.setup(teamList[position])

        holder.showTeamInfo.setOnClickListener {
            if(teamList[position].is_ready){
                intent = Intent(context.context, ProfileTeamInfoReadyActivity::class.java)
            }else{
                intent = Intent(context.context, ProfileTeamInfoNotReadyActivity::class.java)
            }
            intent.putExtra("MyTeamId", teamList[position].id)
            context.context.startActivity(intent)
        }
    }

    fun updateTeamData(teamData:GetProfileResponse){
        this.teamList = teamData.data.myTeamList
        data = teamData
        notifyDataSetChanged()
    }

}