package com.tingting.ver01.SearchTeam

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import com.tingting.ver01.SearchTeam.RecylcerViewLoad.TeamDiffUtilCallback
import com.tingting.ver01.TeamInfo.TeamInfoActivity
import com.tingting.ver01.databinding.CurrentMatchingTeamItem4Binding
import com.tingting.ver01.model.team.lookTeamList.TeamResponse
import com.tingting.ver01.viewModel.SearchTeamFragmentViewModel

class SearchTeamAdapter(private  val searchTeamFragmentViewModel: SearchTeamFragmentViewModel, context: Context): PagedListAdapter<TeamResponse , SearchTeamViewHolder>(TeamDiffUtilCallback()) {

    val context = context

    private var searchListData : MutableList<TeamResponse.Data.Team> = ArrayList()

    //모든 view는context를 가지고있다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchTeamViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val dataBinding = CurrentMatchingTeamItem4Binding.inflate(inflater,parent,false)

        return SearchTeamViewHolder(dataBinding, SearchTeamFragmentViewModel())
    }

    override fun getItemCount(): Int {
        return searchListData.size

     }

    override fun onBindViewHolder(holder : SearchTeamViewHolder , position: Int) {


        holder.setup(searchListData[position])

        holder.arrowToDetail.setOnClickListener(){

            val searchTeamDetailIntent = Intent(context.applicationContext, TeamInfoActivity::class.java)
            searchTeamDetailIntent.putExtra("MyTeamId", searchListData.get(position).id)
            context.startActivity(searchTeamDetailIntent)
        }

    }

    fun updateData(data : TeamResponse, number : Int){
        this.searchListData.clear()

        if(!data.data.teamList.isEmpty()){
        if(number==0){
            for(i in 0..data.data.teamList.size-1){
                this.searchListData.add(data.data.teamList.get(i))
            }
        }else{
        for(i in 0..data.data.teamList.size-1){
            if(number== data.data.teamList.get(i).max_member_number){
                this.searchListData.add(data.data.teamList.get(i))
            }
        }
        }
        }
       // this.searchListData = data.data.teamList as MutableList<TeamResponse.Data.Team>

        notifyDataSetChanged()
    }




}