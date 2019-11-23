package com.example.tintint_jw.ProfileResponseRequest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tintint_jw.R

class ProfileResponseReAdapter(var context: Context, var item: ArrayList<ProfileResponseReData>):RecyclerView.Adapter<ProfileResponseReAdapter.Holder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.profile_request_answer_item,parent,false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
       return item.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(item[position], context)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name = itemView?.findViewById<TextView>(R.id.RequestName)

        fun bind(data:ProfileResponseReData, context: Context){
            name!!.text = data.name
        }

    }


}