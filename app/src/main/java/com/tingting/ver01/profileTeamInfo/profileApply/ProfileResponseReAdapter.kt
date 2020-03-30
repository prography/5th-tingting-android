package com.tingting.ver01.profileTeamInfo.profileApply

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.tingting.ver01.databinding.ProfileRequestAnswerItemBinding
import com.tingting.ver01.model.CodeCallBack
import com.tingting.ver01.model.ModelMatching
import com.tingting.ver01.model.profile.GetProfileResponse
import com.tingting.ver01.viewModel.ProfileFragmentViewModel

class ProfileResponseReAdapter(var profileFragmentViewModel: ProfileFragmentViewModel, var context:Context):RecyclerView.Adapter<ProfileResponseReHolder>(){

    var data : List<GetProfileResponse.Data.SentMatchings> = emptyList()
    lateinit var view : ViewGroup
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileResponseReHolder {

        val inflater = LayoutInflater.from(parent.context)
        val dataBinding = ProfileRequestAnswerItemBinding.inflate(inflater)
        view = parent
        return ProfileResponseReHolder(dataBinding ,profileFragmentViewModel)
    }

    override fun getItemCount(): Int {
       return data.size
    }

    override fun onBindViewHolder(holder: ProfileResponseReHolder, position: Int) {
        holder.setUp(data[position])

        //ok 버튼 눌렀을 때
        holder.okBtn.setOnClickListener {
        ModelMatching.getInstance().receiveHeart(data[position].id,object :CodeCallBack{
            override fun onSuccess(code: String, value: String) {
                try{
                    if(code.equals("201")){
                        Toast.makeText(view.context.applicationContext, "매칭 신청하기 성공", Toast.LENGTH_LONG).show()
                    }
                    else if(code.equals("400")){
                        Toast.makeText(view.context.applicationContext, "매칭 정보가 없거나 이미 전원이 하트를 보냈습니다!", Toast.LENGTH_LONG).show()

                    }else if(code.equals("403")){
                        Toast.makeText(view.context.applicationContext, "팀에 속해있지 않습니다!", Toast.LENGTH_LONG).show()
                    }
                    else if(code.equals("500")){
                        Toast.makeText(view.context.applicationContext, "매칭 신청하기 실패", Toast.LENGTH_LONG).show()

                    }
                }catch (e:Exception){
                }
            }
        })


        }
        //cancel 버튼 눌렀을 때
        holder.cancelBtn.setOnClickListener {

        }

        holder.itemView.setOnClickListener(){
            var intent = Intent(context,OtherTeamProfileActivity::class.java )


            intent.putExtra("myTeamId",data[position].sendTeam.id)
            intent.putExtra("matchingTeamId",data[position].receiveTeam.id)
            intent.putExtra("matchingId", data[position].id)

            context.startActivity(intent)

        }
    }

    fun updateData(applyData : GetProfileResponse){
        this.data = applyData.data.sentMatchings
        notifyDataSetChanged()

    }


}