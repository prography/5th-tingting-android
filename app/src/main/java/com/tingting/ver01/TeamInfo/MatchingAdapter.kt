package com.tingting.ver01.TeamInfo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.tingting.ver01.R

class MatchingAdapter(val context: Context, val matchingData: ArrayList<MatchingData>) :
    RecyclerView.Adapter<MatchingAdapter.Holder>() {


    interface ItemClick{
        fun onClick(view : View,position: Int)
    }

    var click : ItemClick? = null
    var seeChat : ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_matching_complete, parent, false)

        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(matchingData[position], context)

        holder?.MatchingText?.setOnClickListener(){v->
            click?.onClick(v,position)
        }

        holder?.chatAddress?.setOnClickListener {
            v->
            seeChat?.onClick(v, position)
        }
    }


    override fun getItemCount(): Int {
        return matchingData.size
    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val check = itemView?.findViewById<ImageView>(R.id.check)
        val acceptNum = itemView?.findViewById<TextView>(R.id.acceptedNum)
        val matchingName = itemView?.findViewById<TextView>(R.id.teamnameTV)
        val MatchingText = itemView?.findViewById<ConstraintLayout>(R.id.matchingText)
        val chatAddress = itemView?.findViewById<Button>(R.id.chatAddress)

        val id = itemView?.findViewById<TextView>(R.id.id)

        fun bind(matchingData: MatchingData, context: Context) {

            matchingName.setText(matchingData.name)

            if(matchingData.isMatched){
                acceptNum.visibility = View.GONE
                check.visibility = View.GONE
                chatAddress.visibility = View.VISIBLE

            }
            else{
                chatAddress.visibility = View.GONE
                acceptNum.visibility = View.VISIBLE
                check.visibility = View.VISIBLE

                acceptNum.setText(matchingData.AcceptedNum)

            }

        }

    }
}