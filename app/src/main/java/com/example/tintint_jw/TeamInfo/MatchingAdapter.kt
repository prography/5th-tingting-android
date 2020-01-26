package com.example.tintint_jw.TeamInfo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tintint_jw.Model.Matching.ShowAllCandidateListResponse
import com.example.tintint_jw.R
import com.example.tintint_jw.SearchTeam.SearchTeamAdapter
import kotlinx.android.synthetic.main.toolbar_custom.view.*

class MatchingAdapter(val context: Context, val matchingData: ArrayList<MatchingData>) :
    RecyclerView.Adapter<MatchingAdapter.Holder>() {


    interface ItemClick{
        fun onClick(view : View,position: Int)
    }
    var itemClick : ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_matching_complete, parent, false)

        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(matchingData[position], context)

        holder?.MatchingText?.setOnClickListener(){v->
            itemClick?.onClick(v,position)
        }
    }


    override fun getItemCount(): Int {
        return matchingData.size
    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val acceptNum = itemView?.findViewById<TextView>(R.id.acceptedNum)
        val matchingName = itemView?.findViewById<TextView>(R.id.teamnameTV)
        val MatchingText = itemView?.findViewById<TextView>(R.id.matchingText)
        val id = itemView?.findViewById<TextView>(R.id.id)

        fun bind(matchingData: MatchingData, context: Context) {
            acceptNum.setText(matchingData.AcceptedNum)
            matchingName.setText(matchingData.name)


        }

    }
}