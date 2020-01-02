package com.example.tintint_jw.SearchTeam

import android.content.Context
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.tintint_jw.R

class SearchTeamAdapter(context: Context, searchListData: MutableList<SearchTeamData>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var searchListData =searchListData
    var context:Context = context

    interface ItemClick
    {
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            MemberType.MEMBER_ONE.ordinal -> Holder1(LayoutInflater.from(parent.context).inflate(R.layout.current_matching_team_item1, parent, false))
            MemberType.MEMBER_TWO.ordinal->Holder2(LayoutInflater.from(parent.context).inflate(R.layout.current_matching_team_item2, parent, false))
            MemberType.MEMBER_THREE.ordinal->Holder3(LayoutInflater.from(parent.context).inflate(R.layout.current_matching_team_item3, parent, false))
            MemberType.MEMBER_FOUR.ordinal->Holder4(LayoutInflater.from(parent.context).inflate(R.layout.current_matching_team_item4, parent, false))

            else-> Holder1(LayoutInflater.from(parent.context).inflate(R.layout.current_matching_team_item1, parent, false))
        }

    }

    override fun getItemCount(): Int {
        return searchListData.size

     }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)){
            MemberType.MEMBER_ONE.ordinal->{
                val viewHolder = holder as Holder1
                viewHolder.bind(searchListData[position])
                viewHolder?.itemView?.setOnClickListener(){v->
                    itemClick?.onClick(v,position)
                }
            }

            MemberType.MEMBER_TWO.ordinal->{
                val viewHolder = holder as Holder2
                viewHolder.bind(searchListData[position])
                viewHolder?.itemView?.setOnClickListener(){v->
                    itemClick?.onClick(v,position)
                }
            }

            MemberType.MEMBER_THREE.ordinal->{
                val viewHolder = holder as Holder3
                viewHolder.bind(searchListData[position])
                viewHolder?.itemView?.setOnClickListener(){v->
                    itemClick?.onClick(v,position)
                }
            }

            MemberType.MEMBER_FOUR.ordinal->{
                val viewHolder = holder as Holder4
                viewHolder.bind(searchListData[position])
                viewHolder?.itemView?.setOnClickListener(){v->
                    itemClick?.onClick(v,position)
                }
            }
        }
    }

    inner class Holder1(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val img = itemView.findViewById<ImageView>(R.id.img_m1)
        val text = itemView.findViewById<TextView>(R.id.teamInfo1)

        fun bind(searchTeam: SearchTeamData) {
            //img.setImageResource(searchTeam.img1)
            Glide.with(itemView)
                .load(searchTeam.img1)
                .apply(RequestOptions.circleCropTransform())
                .into(img)
            text.text = searchTeam.text


        }

    }
    inner class Holder2(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val img1 = itemView.findViewById<ImageView>(R.id.img_m2First)
        val img2 = itemView.findViewById<ImageView>(R.id.img_m2Sec)
        val text = itemView.findViewById<TextView>(R.id.teamInfo2)

        fun bind(searchTeam: SearchTeamData) {
            /*img1.setImageResource(searchTeam.img1)
            img2.setImageResource(searchTeam.img2)*/
            Glide.with(itemView)
                .load(searchTeam.img1)
                .apply(RequestOptions.circleCropTransform())
                .into(img1)
            Glide.with(itemView)
                .load(searchTeam.img2)
                .apply(RequestOptions.circleCropTransform())
                .into(img2)

            text.text = searchTeam.text

            itemView.setOnClickListener{
                Toast.makeText(context, "2명", Toast.LENGTH_SHORT).show()
            }
        }

    }
    inner class Holder3(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val img1 = itemView.findViewById<ImageView>(R.id.img_m3First)
        val img2 = itemView.findViewById<ImageView>(R.id.img_m3Sec)
        val img3 = itemView.findViewById<ImageView>(R.id.img_m3Third)
        val text = itemView.findViewById<TextView>(R.id.teamInfo3)

        fun bind(searchTeam: SearchTeamData) {
            Glide.with(itemView)
                .load(searchTeam.img1)
                .apply(RequestOptions.circleCropTransform())
                .into(img1)
            Glide.with(itemView)
                .load(searchTeam.img2)
                .apply(RequestOptions.circleCropTransform())
                .into(img2)
            Glide.with(itemView)
                .load(searchTeam.img3)
                .apply(RequestOptions.circleCropTransform())
                .into(img3)

            text.text = searchTeam.text

            itemView.setOnClickListener{
                Toast.makeText(context, "3명", Toast.LENGTH_SHORT).show()
            }
        }

    }
    inner class Holder4(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val img1 = itemView.findViewById<ImageView>(R.id.img_m4First)
        val img2 = itemView.findViewById<ImageView>(R.id.img_m4Sec)
        val img3 = itemView.findViewById<ImageView>(R.id.img_m4Third)
        val img4 = itemView.findViewById<ImageView>(R.id.img_m4Fourth)
        val text = itemView.findViewById<TextView>(R.id.teamInfo4)

        fun bind(searchTeam: SearchTeamData) {
            Glide.with(itemView)
                .load(searchTeam.img1)
                .apply(RequestOptions.circleCropTransform())
                .into(img1)
            Glide.with(itemView)
                .load(searchTeam.img2)
                .apply(RequestOptions.circleCropTransform())
                .into(img2)
            Glide.with(itemView)
                .load(searchTeam.img3)
                .apply(RequestOptions.circleCropTransform())
                .into(img3)
            Glide.with(itemView)
                .load(searchTeam.img4)
                .apply(RequestOptions.circleCropTransform())
                .into(img4)
            text.text = searchTeam.text

        }

    }

    enum class MemberType(viewType: Int){
        MEMBER_ONE(1),
        MEMBER_TWO(2),
        MEMBER_THREE(3),
        MEMBER_FOUR(4)
    }

    override fun getItemViewType(position: Int): Int {
        return when(searchListData[position].key){
            "one"-> MemberType.MEMBER_ONE.ordinal
            "two"->MemberType.MEMBER_TWO.ordinal
            "three"->MemberType.MEMBER_THREE.ordinal
            "four"->MemberType.MEMBER_FOUR.ordinal
            else -> 0
        }

    }



}