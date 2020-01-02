package com.example.tintint_jw.Matching

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tintint_jw.R
import com.example.tintint_jw.SearchTeam.SearchTeamAdapter
import java.lang.NullPointerException

class MatchingAdapter(context: Context, teamList:MutableList<TeamData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var teamList = teamList
    var context: Context = context

    interface MatchingClick{
         fun Onclick(view:View, position:Int)
    }

    var matchingclick :MatchingClick? = null

    inner class Holder1(itemView: View):RecyclerView.ViewHolder(itemView) {
        val img1 = itemView.findViewById<ImageView>(R.id.img1)
        val info1 = itemView.findViewById<TextView>(R.id.info1)
        val info2 = itemView.findViewById<TextView>(R.id.info2)

        fun bind(teamData: TeamData){
            img1.setImageResource(teamData.img1)
            info1.setText(teamData.info1)
            info2.setText(teamData.info2)

        }


    }

    inner class Holder2(itemView: View):RecyclerView.ViewHolder(itemView) {
        val img1 = itemView.findViewById<ImageView>(R.id.img1)
        val img2 = itemView.findViewById<ImageView>(R.id.img2)
        val info1 = itemView.findViewById<TextView>(R.id.info1)
        val info2 = itemView.findViewById<TextView>(R.id.info2)

        fun bind(teamData: TeamData){
            img1.setImageResource(teamData.img1)
            img2.setImageResource(teamData.img2)
            info1.setText(teamData.info1)
            info2.setText(teamData.info2)

        }

    }

    inner class Holder3(itemView: View):RecyclerView.ViewHolder(itemView) {
        val img1 = itemView.findViewById<ImageView>(R.id.img1)
        val img2 = itemView.findViewById<ImageView>(R.id.img2)
        val img3 = itemView.findViewById<ImageView>(R.id.img3)
        val info1 = itemView.findViewById<TextView>(R.id.info1)
        val info2 = itemView.findViewById<TextView>(R.id.info2)

        fun bind(teamData: TeamData){
            img1.setImageResource(teamData.img1)
            img2.setImageResource(teamData.img2)
            img3.setImageResource(teamData.img3)
            info1.setText(teamData.info1)
            info2.setText(teamData.info2)

        }

    }

    inner class Holder4(itemView: View):RecyclerView.ViewHolder(itemView) {
        val img1 = itemView.findViewById<ImageView>(R.id.img1)
        val img2 = itemView.findViewById<ImageView>(R.id.img2)
        val img3 = itemView.findViewById<ImageView>(R.id.img3)
        val img4 = itemView.findViewById<ImageView>(R.id.img4)
        val info1 = itemView.findViewById<TextView>(R.id.info1)
        val info2 = itemView.findViewById<TextView>(R.id.info2)

        fun bind(teamData: TeamData){
            img1.setImageResource(teamData.img1)
            img2.setImageResource(teamData.img2)
            img3.setImageResource(teamData.img3)
            img4.setImageResource(teamData.img4)
            info1.setText(teamData.info1)
            info2.setText(teamData.info2)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder{
        //val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_matching4,parent,false)
        return when(viewType){
            MemberType.MEMBER_ONE.ordinal -> Holder1(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.recycler_item_matching1,
                    parent,
                    false
                )
            )
            MemberType.MEMBER_TWO.ordinal-> Holder2(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.recycler_item_matching2,
                    parent,
                    false
                )
            )
            MemberType.MEMBER_THREE.ordinal-> Holder3(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.recycler_item_matching3,
                    parent,
                    false
                )
            )
            MemberType.MEMBER_FOUR.ordinal->Holder4(LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_matching4, parent, false))

            else-> Holder1(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.recycler_item_matching1,
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
                viewHolder.bind(teamList[position])

                viewHolder?.img1?.setOnClickListener(){v->
                    matchingclick?.Onclick(v,position)
                }

            }
            MemberType.MEMBER_TWO.ordinal->{
                val viewHolder = holder as Holder2
                viewHolder.bind(teamList[position])
                viewHolder?.img1?.setOnClickListener(){v->
                    matchingclick?.Onclick(v,position)
                }
                viewHolder?.img2?.setOnClickListener(){v->
                    matchingclick?.Onclick(v,position)
                }
            }
            MemberType.MEMBER_THREE.ordinal->{
                val viewHolder = holder as Holder3
                viewHolder.bind(teamList[position])

                viewHolder?.img1?.setOnClickListener(){v->
                    matchingclick?.Onclick(v,position)
                }

            }
            MemberType.MEMBER_FOUR.ordinal->{
                val viewHolder = holder as Holder4
                viewHolder.bind(teamList[position])
                viewHolder?.itemView?.setOnClickListener(){v->
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
        teamList.add(TeamData(R.drawable.iu2,"테스트용 데이터","테스트용 데이터"))
        notifyItemInserted(teamList.size- 1 )

    }



}