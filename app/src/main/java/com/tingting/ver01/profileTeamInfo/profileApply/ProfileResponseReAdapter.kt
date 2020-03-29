package com.tingting.ver01.profileTeamInfo.profileApply

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tingting.ver01.databinding.ProfileRequestAnswerItemBinding
import com.tingting.ver01.model.profile.GetProfileResponse
import com.tingting.ver01.viewModel.ProfileFragmentViewModel

class ProfileResponseReAdapter(var profileFragmentViewModel: ProfileFragmentViewModel, var context:Context):RecyclerView.Adapter<ProfileResponseReHolder>(){

    var data : List<GetProfileResponse.Data.SentMatchings> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileResponseReHolder {

        val inflater = LayoutInflater.from(parent.context)
        val dataBinding = ProfileRequestAnswerItemBinding.inflate(inflater)

        return ProfileResponseReHolder(dataBinding ,profileFragmentViewModel)
    }

    override fun getItemCount(): Int {
       return data.size
    }

    override fun onBindViewHolder(holder: ProfileResponseReHolder, position: Int) {
        holder.setUp(data[position])

        //ok 버튼 눌렀을 때
        holder.okBtn.setOnClickListener {
            var intent = Intent(context,OtherTeamProfileActivity::class.java )


            intent.putExtra("myTeamId",data[position].sendTeam.id)
            intent.putExtra("matchingTeamId",data[position].receiveTeam.id)
            intent.putExtra("matchingId", data[position].id)

            context.startActivity(intent)

            //team nubemr
            //applyTeamInfoActivity

        }
        //cancel 버튼 눌렀을 때
        holder.cancelBtn.setOnClickListener {

        }
    }

    fun updateData(applyData : GetProfileResponse){
        this.data = applyData.data.sentMatchings
        notifyDataSetChanged()

    }


}