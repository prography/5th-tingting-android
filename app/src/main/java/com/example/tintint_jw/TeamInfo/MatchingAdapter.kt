package com.example.tintint_jw.TeamInfo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tintint_jw.R
import kotlinx.android.synthetic.main.toolbar_custom.view.*

class MatchingAdapter(val context: Context, val matchingData: ArrayList<MatchingData>) :
    RecyclerView.Adapter<MatchingAdapter.Holder>() {
    var itemPosition:Int = 0

    interface ItemClick{
        fun onClick(view:View, position: Int)
    }

    var click:ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_matching_complete, parent, false)

        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(matchingData[position], context)
        itemPosition = holder.adapterPosition
        holder.itemView.setOnClickListener {
            v->
            click?.onClick(v, position)
        }
    }


    override fun getItemCount(): Int {
        return matchingData.size
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //val acceptNum = itemView?.findViewById<TextView>(R.id.acceptedNum)
        val teamName = itemView?.findViewById<TextView>(R.id.teamnameTV)
        val teamMaxMember = itemView?.findViewById<TextView>(R.id.teamMaxMember)
        val teamRegion = itemView?.findViewById<TextView>(R.id.teamRegion)

        fun bind(matchingData: MatchingData, context: Context) {

            //acceptNum.setText(matchingData.AcceptedNum.toString())
            teamName.text = matchingData.teamName+"/"
            teamMaxMember.text = matchingData.teamMaxMember.toString()+"명/"
            teamRegion.text = matchingData.teamRegion

            /*itemView.setOnClickListener{
                itemClick(matchingData)
            }*/
        }

    }
}