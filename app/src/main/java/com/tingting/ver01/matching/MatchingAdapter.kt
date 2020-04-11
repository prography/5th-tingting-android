package com.tingting.ver01.matching

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tingting.ver01.view.Main.MatchingFragment
import com.tingting.ver01.databinding.RecyclerItemMatching4Binding
import com.tingting.ver01.model.matching.ShowAllCandidateListResponse
import com.tingting.ver01.viewModel.MatchingFragmentViewModel

class MatchingAdapter constructor(context: Context, var matchingFragmentViewModel: MatchingFragmentViewModel) : RecyclerView.Adapter<MatchingViewHolder>() {

    var matchingList : MutableList<ShowAllCandidateListResponse.Data.Matching> = ArrayList()
    var matchingMyList : List<ShowAllCandidateListResponse.Data.MyTeam> = emptyList()

    lateinit var context : ViewGroup


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchingViewHolder {
        var inflater = LayoutInflater.from(parent.context)
         var dataBinding = RecyclerItemMatching4Binding.inflate(inflater,parent,false)
        context = parent
        return MatchingViewHolder(dataBinding, matchingFragmentViewModel )
    }



    override fun getItemCount(): Int {
        return matchingList.size
    }

    override fun onBindViewHolder(holder: MatchingViewHolder, position: Int) {

        holder.setHolder(matchingList[position])

        holder.arrow.setOnClickListener {
            var intent = Intent(context.context,MatchingApplyTeamInfo::class.java)
                intent.putExtra("MatchingTeamId",matchingList.get(position).id)
                //내 팀 id를 어떻게 가지고 올지 생각해보자ㅏㅏㅏㅏ
                intent.putExtra("MyTeamId",MatchingFragment.myTeamId)
                context.context.startActivity(intent)
        }

    }

    fun update(item: ShowAllCandidateListResponse, number : Int){
        matchingList.clear()

        if (!item.data.matchingList.isEmpty()) {
                for (i in 0..item.data.matchingList.size - 1) {
                    if (number == item.data.matchingList.get(i).max_member_number) {
                        this.matchingList.add(item.data.matchingList.get(i))
                    }
                }
        }

        this.matchingMyList = item.data.myTeamList
        notifyDataSetChanged()
    }

    fun addData(item: ArrayList<ShowAllCandidateListResponse>, number : Int){
        matchingList.clear()

        for(i in 0..item.size-1){

            Log.d("addData22","addData22")

        if (!item.get(i).data.matchingList.isEmpty()) {
            for (j in 0..item.get(i).data.matchingList.size - 1) {
                if (number == item.get(i).data.matchingList.get(j).max_member_number) {
                    this.matchingList.add(item.get(i).data.matchingList.get(j))
                }
            }
        }

        }
        notifyDataSetChanged()
    }

}