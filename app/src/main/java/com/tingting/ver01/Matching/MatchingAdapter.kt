package com.tingting.ver01.Matching

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tingting.ver01.R
import com.tingting.ver01.View.Main.MainActivity

class MatchingAdapter(context: Context, teamList:MutableList<TeamData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var teamList = teamList
    var context: Context = context

    interface MatchingClick{
         fun Onclick(view:View, position:Int)
    }

    var matchingclick :MatchingClick? = null

    inner class Holder1(itemView: View):RecyclerView.ViewHolder(itemView) {
        val img1 = itemView.findViewById<ImageView>(R.id.img_m1)
        val info1 = itemView.findViewById<TextView>(R.id.teamInfo1)
        val arrow = itemView.findViewById<ImageView>(R.id.ArrowToDetail)

        fun bind(teamData: TeamData){

            Glide.with(itemView).load(teamData.img1).apply(RequestOptions().circleCrop()).into(img1)
            //img1.setImageResource(teamData.img1)
            info1.setText(teamData.info1)

        }


    }

    inner class Holder2(itemView: View):RecyclerView.ViewHolder(itemView) {
        val img1 = itemView.findViewById<ImageView>(R.id.img_m2First)
        val img2 = itemView.findViewById<ImageView>(R.id.img_m2Sec)
        val info1 = itemView.findViewById<TextView>(R.id.teamInfo2)
        val arrow = itemView.findViewById<ImageView>(R.id.ArrowToDetail)

        fun bind(teamData: TeamData){


            MainActivity.glide.setImageAdapter(itemView,
                MainActivity.glide.DecryptUrl(teamData.img1),img1)


            MainActivity.glide.setImageAdapter(itemView,
                MainActivity.glide.DecryptUrl(teamData.img2),img2)

//            Glide.with(itemView).load(teamData.img1).apply(RequestOptions().circleCrop()).into(img1)
//            Glide.with(itemView).load(teamData.img2).apply(RequestOptions().circleCrop()).into(img2)

            /*img1.setImageResource(teamData.img1)
            img2.setImageResource(teamData.img2)*/
            info1.setText(teamData.info1)

        }

    }

    inner class Holder3(itemView: View):RecyclerView.ViewHolder(itemView) {
        val img1 = itemView.findViewById<ImageView>(R.id.img_m3First)
        val img2 = itemView.findViewById<ImageView>(R.id.img_m3Sec)
        val img3 = itemView.findViewById<ImageView>(R.id.img_m3Third)
        val info1 = itemView.findViewById<TextView>(R.id.teamInfo3)
        val arrow = itemView.findViewById<ImageView>(R.id.ArrowToDetail)

        fun bind(teamData: TeamData){
            /*img1.setImageResource(teamData.img1)
            img2.setImageResource(teamData.img2)
            img3.setImageResource(teamData.img3)*/
            MainActivity.glide.setImageAdapter(itemView,
                MainActivity.glide.DecryptUrl(teamData.img1),img1)
            MainActivity.glide.setImageAdapter(itemView,
                MainActivity.glide.DecryptUrl(teamData.img2),img2)
            MainActivity.glide.setImageAdapter(itemView,
                MainActivity.glide.DecryptUrl(teamData.img3),img3)



            info1.setText(teamData.info1)

        }

    }

    inner class Holder4(itemView: View):RecyclerView.ViewHolder(itemView) {
        val img1 = itemView.findViewById<ImageView>(R.id.img_m4First)
        val img2 = itemView.findViewById<ImageView>(R.id.img_m4Sec)
        val img3 = itemView.findViewById<ImageView>(R.id.img_m4Third)
        val img4 = itemView.findViewById<ImageView>(R.id.img_m4Fourth)
        val info1 = itemView.findViewById<TextView>(R.id.teamInfo4)
        val arrow = itemView.findViewById<ImageView>(R.id.ArrowToDetail)

        fun bind(teamData: TeamData){

            MainActivity.glide.setImageAdapter(itemView,
                MainActivity.glide.DecryptUrl(teamData.img1),img1)
            MainActivity.glide.setImageAdapter(itemView,
                MainActivity.glide.DecryptUrl(teamData.img2),img2)
            MainActivity.glide.setImageAdapter(itemView,
                MainActivity.glide.DecryptUrl(teamData.img3),img3)
            MainActivity.glide.setImageAdapter(itemView,
                MainActivity.glide.DecryptUrl(teamData.img4),img4)

            info1.setText(teamData.info1)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder{
        //val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_matching4,parent,false)
        return when(viewType){
            MemberType.MEMBER_ONE.ordinal -> Holder1(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.current_matching_team_item1,
                    parent,
                    false
                )
            )
            MemberType.MEMBER_TWO.ordinal-> Holder2(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.current_matching_team_item2,
                    parent,
                    false
                )
            )
            MemberType.MEMBER_THREE.ordinal-> Holder3(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.current_matching_team_item3,
                    parent,
                    false
                )
            )
            MemberType.MEMBER_FOUR.ordinal->Holder4(LayoutInflater.from(parent.context).inflate(R.layout.current_matching_team_item4, parent, false))

            else-> Holder1(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.current_matching_team_item1,
                    parent,
                    false
                )
            )
        }
    }



    override fun getItemCount(): Int {
        return teamList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)){
            MemberType.MEMBER_ONE.ordinal->{
                val viewHolder = holder as Holder1
                if(position == 0){
                    var wrap:FrameLayout= viewHolder.itemView.findViewById<FrameLayout>(R.id.frame)
                    wrap.setBackgroundResource(R.drawable.edge_gray_topnbottom)
                }
                viewHolder.bind(teamList[position])

                viewHolder?.arrow?.setOnClickListener(){v->
                    matchingclick?.Onclick(v,position)
                }

            }
            MemberType.MEMBER_TWO.ordinal->{
                val viewHolder = holder as Holder2
                if(position == 0){
                    var wrap:FrameLayout= viewHolder.itemView.findViewById<FrameLayout>(R.id.frame)
                    wrap.setBackgroundResource(R.drawable.edge_gray_topnbottom)
                }
                viewHolder.bind(teamList[position])
                viewHolder?.arrow?.setOnClickListener(){v->
                    matchingclick?.Onclick(v,position)
                }

            }
            MemberType.MEMBER_THREE.ordinal->{
                val viewHolder = holder as Holder3
                if(position == 0){
                    var wrap:FrameLayout= viewHolder.itemView.findViewById<FrameLayout>(R.id.frame)
                    wrap.setBackgroundResource(R.drawable.edge_gray_topnbottom)
                }
                viewHolder.bind(teamList[position])

                viewHolder?.arrow?.setOnClickListener(){v->
                    matchingclick?.Onclick(v,position)
                }

            }
            MemberType.MEMBER_FOUR.ordinal->{
                val viewHolder = holder as Holder4
                if(position == 0){
                    var wrap:FrameLayout= viewHolder.itemView.findViewById<FrameLayout>(R.id.frame)
                    wrap.setBackgroundResource(R.drawable.edge_gray_topnbottom)

                }
                viewHolder.bind(teamList[position])
                viewHolder?.arrow?.setOnClickListener(){v->
                    matchingclick?.Onclick(v,position)
                }
            }
        }


    }

    enum class MemberType(viewType: Int){
        MEMBER_ONE(1),
        MEMBER_TWO(2),
        MEMBER_THREE(3),
        MEMBER_FOUR(4)
    }

    override fun getItemViewType(position: Int): Int {
        return when(teamList[position].key){
            "one"-> MemberType.MEMBER_ONE.ordinal
            "two"-> MemberType.MEMBER_TWO.ordinal
            "three"-> MemberType.MEMBER_THREE.ordinal
            "four"-> MemberType.MEMBER_FOUR.ordinal
            else -> 0
        }
    }

    fun getItem(position: Int):TeamData{
        return teamList.get(position)

    }

    fun addData(listItems: ArrayList<TeamData>){
        teamList.addAll(listItems)
        notifyDataSetChanged()
    }


    fun addLoading(){
        //teamList.add(TeamData(R.drawable.iu2,1,"테스트용 데이터"))
        notifyItemInserted(teamList.size- 1 )

    }



}