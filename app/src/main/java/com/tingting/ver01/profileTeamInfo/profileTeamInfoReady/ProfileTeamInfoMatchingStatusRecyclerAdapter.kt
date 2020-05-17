package com.tingting.ver01.profileTeamInfo.profileTeamInfoReady

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.tingting.ver01.ApplyTeamInfo.ApplyTeamInfoActivity
import com.tingting.ver01.databinding.ProfileTeamInfoItemBinding
import com.tingting.ver01.model.CodeCallBack
import com.tingting.ver01.model.ModelMatching
import com.tingting.ver01.model.team.lookMyTeamInfoDetail.LookMyTeamInfoDetailResponse
import com.tingting.ver01.teamInfo.ProfileTeamInfoMatchingStatusHolder
import com.tingting.ver01.view.Main.MainActivity
import com.tingting.ver01.viewModel.ProfileTeamInfoViewModel
import kotlinx.coroutines.*

class ProfileTeamInfoMatchingStatusRecyclerAdapter(private val profileTeamInfoViewModel: ProfileTeamInfoViewModel, val myTeamId:Int)
    : RecyclerView.Adapter<ProfileTeamInfoMatchingStatusHolder>(){

    var teamList : ArrayList<LookMyTeamInfoDetailResponse.Data.TeamMatching> = ArrayList()
    var teamData : LookMyTeamInfoDetailResponse.Data? = null
    lateinit var view : ViewGroup
    var currentNum = 0;
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProfileTeamInfoMatchingStatusHolder {
        val inflater = LayoutInflater.from(parent.context)
        val dataBinding = ProfileTeamInfoItemBinding.inflate(inflater,parent,false)
        view = parent

        return ProfileTeamInfoMatchingStatusHolder(
            dataBinding,
            profileTeamInfoViewModel
        )
    }

    override fun getItemCount(): Int {
        return teamList.size
    }

    override fun onBindViewHolder(holder: ProfileTeamInfoMatchingStatusHolder, position: Int) {
            holder.setUp(teamList[position])


        holder.regionInfo.text = teamData?.teamInfo?.place + " | "

        holder.teamName.text = teamData?.teamInfo?.name


        if(teamList[position].is_matched){

        }else{
            currentNum = teamList[position].accepter_number

            holder.agreeNumber.text = teamList[position].accepter_number.toString()+"/"+teamList[position].sendTeam.max_member_number.toString()
            holder.chatAddress.visibility = View.GONE
        }
        //view message 설정
        if(teamList[position].is_accepted){
            holder.cancelBtn.visibility = View.GONE
            holder.okBtn.visibility = View.GONE
            holder.chatAddress.visibility = View.GONE
        }else{
            holder.waitingMatching.visibility = View.GONE
            holder.chatAddress.visibility = View.GONE
        }
        //button visible 설정






        var number=1;
        for(i in teamList[position].sendTeam.membersInfo.size -1 downTo 0){

            when(number){
                1-> MainActivity.glide.setImage(holder.img1.context, MainActivity.glide.DecryptUrl(
                   teamList[position].sendTeam.membersInfo.get(i).thumbnail
                ),holder.img1)
                2-> MainActivity.glide.setImage(holder.img2.context, MainActivity.glide.DecryptUrl(
                    teamList[position].sendTeam.membersInfo.get(i).thumbnail
                ),holder.img2)
                3-> MainActivity.glide.setImage(holder.img3.context, MainActivity.glide.DecryptUrl(
                    teamList[position].sendTeam.membersInfo.get(i).thumbnail
                ),holder.img3)
                4-> MainActivity.glide.setImage(holder.img4.context, MainActivity.glide.DecryptUrl(
                    teamList[position].sendTeam.membersInfo.get(i).thumbnail
                ),holder.img4)
            }
            number++
        }

        when(teamList[position].sendTeam.membersInfo.size){
            2->{
                holder.img3.visibility = View.GONE
                holder.img4.visibility = View.GONE
            }
            3-> holder.img4.visibility = View.GONE
        }


        holder.itemView.setOnClickListener {
            //상대 프로필 보여주는 api 콜
            if(!teamList[position].is_matched){
            val intent = Intent(view.context, ApplyTeamInfoActivity::class.java)
            //myTeamId
            //상대 팀 ID
            intent.putExtra("myTeamId",myTeamId)
            intent.putExtra("acceptValue",teamList[position].is_accepted)
            intent.putExtra("matchingTeamId", teamList[position].sendTeam.id)
            view.context.startActivity(intent)
            }else{
                Toast.makeText(view.context,"매칭이 완료 된 팀은 오픈채팅으로 확인해주세요",Toast.LENGTH_LONG).show()
            }

        }

        holder.okBtn.setOnClickListener {
            // 수락 하는 api 콜
            ModelMatching.getInstance().receiveHeart(teamList[position].id, object : CodeCallBack {

                override fun onSuccess(code: String, value: String) {
                    if(code.equals("201")){
                        currentNum+=1
                        Toast.makeText(view.context.applicationContext,"수락 되었습니다.", Toast.LENGTH_LONG).show()

                        CoroutineScope(Dispatchers.Main).launch {

                            holder.okBtn.visibility = View.GONE
                            holder.cancelBtn.visibility =View.GONE
                            holder.waitingMatching.visibility=View.VISIBLE

                            holder.agreeNumber2.visibility=View.VISIBLE
                            holder.agreeNumber2.setText(currentNum.toString()+"/"+teamList[position].sendTeam.max_member_number.toString())
                            holder.agreeNumber.visibility = View.GONE
                            notifyDataSetChanged()
                        }



                        notifyDataSetChanged()

                    }else if(code.equals("400")){
                        Toast.makeText(view.context.applicationContext,"매칭 정보가 없습니다!", Toast.LENGTH_LONG).show()

                    }else if(code.equals("403")){
                        Toast.makeText(view.context.applicationContext,"팀에 속해있지 않습니다!", Toast.LENGTH_LONG).show()

                    }else{
                        Toast.makeText(view.context.applicationContext,"매칭 수락하기 실패", Toast.LENGTH_LONG).show()

                    }
                }
            })

        }

        holder.cancelBtn.setOnClickListener {
            //취소하는 api 콜
            ModelMatching.getInstance().refuseHeart(teamList[position].id, object : CodeCallBack {

                override fun onSuccess(code: String, value: String) {
                    if(code.equals("201")){
                        Toast.makeText(view.context.applicationContext,"거절 되었습니다.", Toast.LENGTH_LONG).show()
                        teamList.removeAt(position)
                    }else if(code.equals("400")){
                        Toast.makeText(view.context.applicationContext,"매칭 정보가 없습니다!", Toast.LENGTH_LONG).show()

                    }else if(code.equals("403")){
                        Toast.makeText(view.context.applicationContext,"팀에 속해있지 않습니다!", Toast.LENGTH_LONG).show()

                    }else{
                    }
                }
            })

            notifyDataSetChanged()
        }

    }

    //데이터 업데이트
    fun updateData(teamData: LookMyTeamInfoDetailResponse){
        this.teamList = teamData.data.teamMatchings
        this.teamData = teamData.data

        notifyDataSetChanged()
    }
}