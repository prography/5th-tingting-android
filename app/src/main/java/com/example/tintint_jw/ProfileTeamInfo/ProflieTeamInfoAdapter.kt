package com.example.tintint_jw.ProfileTeamInfo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tintint_jw.R
import kotlinx.android.synthetic.main.recycler_item_profile_teaminfo.view.*

class ProflieTeamInfoAdapter(val context: Context, val Item: ArrayList<ProfileTeamInfoData>, val itemClick :(ProfileTeamInfoData) -> Unit) :
    RecyclerView.Adapter<ProflieTeamInfoAdapter.Holder>() {

    //crate inner class holder


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.recycler_item_profile_teaminfo, parent, false)
        return Holder(view,itemClick)
    }

    override fun getItemCount(): Int {
        return Item.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(Item[position], context)
    }

    inner class Holder(itemView: View, itemClick:(ProfileTeamInfoData)->Unit) : RecyclerView.ViewHolder(itemView) {
        val name = itemView?.findViewById<TextView>(R.id.TeamName)

        //make bind
        fun bind(data: ProfileTeamInfoData, context: Context) {
            name!!.text = data.name

            itemView.ShowTeamInfo.setOnClickListener{
                itemClick(data)
            }

        }

    }
}