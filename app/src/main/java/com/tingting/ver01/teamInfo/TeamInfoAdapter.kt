package com.tingting.ver01.teamInfo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.request.RequestOptions
import com.tingting.ver01.R
import com.tingting.ver01.SharedPreference.App

class TeamInfoAdapter(
    val context: Context,
    val teamListData: ArrayList<TeamInfoData>,
    var itemClick: (TeamInfoData) -> Unit
) :
    RecyclerView.Adapter<TeamInfoAdapter.Holder>() {
    lateinit var glideUrl: GlideUrl

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.recyclerview_team_info, parent, false)

        return Holder(view, itemClick)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(teamListData[position], context)
    }


    override fun getItemCount(): Int {
        return teamListData.size
    }

    inner class Holder(itemView: View, itemClick: (TeamInfoData) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        val profile = itemView?.findViewById<ImageView>(R.id.profile)
        val position = itemView?.findViewById<TextView>(R.id.position)
        val id = itemView?.findViewById<TextView>(R.id.id)

        fun bind(teaminfo: TeamInfoData, context: Context) {


            glideUrl =  GlideUrl(teaminfo.mainImage) { mapOf(Pair("Authorization", App.prefs.myToken.toString())) }

            Glide.with(context).load(glideUrl).skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .apply(RequestOptions.circleCropTransform()).into(profile)


            //  Glide.with(profile).load(teaminfo.mainImage).apply(RequestOptions.circleCropTransform()).into(profile)

            if (teaminfo.position.equals("0")) {
                position?.text = "팀장"
                position.setBackgroundResource(R.drawable.button1)
            } else {
                position?.text = "팀원"
                position.setBackgroundResource(R.drawable.button2)
            }
            //position?.text = teaminfo.position
            id?.text = teaminfo.name

            itemView.setOnClickListener {
                itemClick(teaminfo)
            }
        }

    }
}