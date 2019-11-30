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

class MatchingAdapter(val context: Context, val matchingData: ArrayList<MatchingData>, val itemClick:(MatchingData) -> Unit) :
    RecyclerView.Adapter<MatchingAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_matching_complete, parent, false)

        return Holder(view, itemClick)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(matchingData[position], context)
    }


    override fun getItemCount(): Int {
        return matchingData.size
    }

    inner class Holder(itemView: View, itemClick: (MatchingData) -> Unit) : RecyclerView.ViewHolder(itemView) {
        val acceptNum = itemView?.findViewById<TextView>(R.id.acceptedNum)

        val id = itemView?.findViewById<TextView>(R.id.id)

        fun bind(matchingData: MatchingData, context: Context) {

            acceptNum.setText(matchingData.AcceptedNum)

            itemView.setOnClickListener{
                itemClick(matchingData)
            }
        }

    }
}