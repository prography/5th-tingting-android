package com.example.tintint_jw.ProfileResponseRequest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tintint_jw.R

class ProfileResponseReAdapter(var context: Context, var item: ArrayList<ProfileResponseReData>):RecyclerView.Adapter<ProfileResponseReAdapter.Holder>(){

    interface ItemClick{
        fun Onclick(view:View, position:Int)
    }

    var itemClick :ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.profile_request_answer_item,parent,false)


        return Holder(view)
        /*.listen{ position, type ->
            val item2  = item.get(position)

            view.cancel.setOnClickListener(){
                Log.d("teaminfo","cancel 클릭됨")
                //서버에 데이터 리무브 함수 요청..
                item.removeAt(position);
                notifyDataSetChanged()
            }
        }*/
    }

    override fun getItemCount(): Int {
       return item.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(item[position], context)
        holder.name.setOnClickListener { v->
            itemClick?.Onclick(v, position)
        }
        holder.ok.setOnClickListener { v->
            itemClick?.Onclick(v, position)
        }
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name = itemView?.findViewById<TextView>(R.id.RequestName)
        val ok = itemView?.findViewById<ImageView>(R.id.ok)
        val cancel = itemView?.findViewById<ImageView>(R.id.cancel)

        fun bind(data:ProfileResponseReData, context: Context){
            name!!.text = data.name

        }
    }

    /*fun <T: RecyclerView.ViewHolder> T.listen(event : (position:Int, type : Int ) -> Unit) : T{
        itemView.setOnClickListener(){
            event.invoke(adapterPosition,getItemViewType())
        }
        return this
    }*/


}