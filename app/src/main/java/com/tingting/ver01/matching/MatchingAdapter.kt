package com.tingting.ver01.matching

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tingting.ver01.databinding.RecyclerItemMatching4Binding
import com.tingting.ver01.matching.matchingTeamMember.MatchingTeamInfo
import com.tingting.ver01.model.matching.ShowAllCandidateListResponse
import com.tingting.ver01.view.Main.MatchingFragment
import com.tingting.ver01.viewModel.MatchingFragmentViewModel

class MatchingAdapter constructor( var matchingFragmentViewModel: MatchingFragmentViewModel) : RecyclerView.Adapter<MatchingViewHolder>() {

    var matchingList : ArrayList<ShowAllCandidateListResponse.Data.Matching> = ArrayList()
    var matchingTotalList : ArrayList<ShowAllCandidateListResponse.Data.Matching> = ArrayList()

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
            var intent = Intent(context.context, MatchingTeamInfo::class.java)
                intent.putExtra("MatchingTeamId",matchingList.get(position).id)
                //내 팀 id를 어떻게 가지고 올지 생각해보자ㅏㅏㅏㅏ
                intent.putExtra("MyTeamId",MatchingFragment.myTeamId)
                context.context.startActivity(intent)
        }

    }

    //최초 이미지
    fun update(item: ArrayList<ShowAllCandidateListResponse>, number : Int){

        Log.d("Matching", item.size.toString());
        for(i in 0..item.size-1){

            for(j in 0..item.get(i).data.matchingList.size-1){
            //    matchingList.add(item.get(i).data.matchingList.get(j))

                if(number==item.get(i).data.matchingList.get(j).max_member_number){
                    matchingList.add(item.get(i).data.matchingList.get(j))

                }

            }
        }

        notifyDataSetChanged()
    }

    //스크롤시 추가로 데이터를 불러오는 부분..!

    fun addData(item: ArrayList<ShowAllCandidateListResponse>, number : Int){

        for(i in 0..item.size-1){

            for(j in 0..item.get(i).data.matchingList.size-1){

                if(number==item.get(i).data.matchingList.get(j).max_member_number){
                    matchingList.add(item.get(i).data.matchingList.get(j))

                }

            }
        }
        notifyDataSetChanged()
    }

    fun refresh(){
        this.matchingList.clear()
        this.matchingTotalList.clear()
    }

    fun dataclear(){
        matchingList.clear()
    }


}