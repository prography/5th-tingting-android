package com.tingting.ver01.ProfileTeamInfo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tingting.ver01.R

class ProflieTeamInfoAdapter(val context: Context, val Item: ArrayList<ProfileTeamInfoData>) :
    RecyclerView.Adapter<ProflieTeamInfoAdapter.Holder>() {

    //crate inner class holder
    interface ItemClick
    {
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.recycler_item_profile_teaminfo, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return Item.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(Item[position], context)

        holder?.showTeamInfo.setOnClickListener(){v->
            itemClick?.onClick(v,position)
        }
    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name = itemView?.findViewById<TextView>(R.id.TeamName)
        val showTeamInfo = itemView?.findViewById<Button>(R.id.ShowTeamInfo)
        //make bind
        fun bind(data: ProfileTeamInfoData, context: Context) {
            name!!.text = data.name

        }

    }
}