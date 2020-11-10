package com.tingting.ver01.viewModel

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.tingting.ver01.model.CodeCallBack
import com.tingting.ver01.model.ModelMatching
import com.tingting.ver01.model.matching.FirstSendHeartResponse
import com.tingting.ver01.model.matching.ShowMatchingTeamInfoResponse
import com.tingting.ver01.model.profile.GetProfileResponse
import com.tingting.ver01.sharedPreference.App
import com.tingting.ver01.view.Main.MainActivity


class MatchingApplyTeamInfoViewModel : BaseViewModel(){


    var  data = MutableLiveData<ShowMatchingTeamInfoResponse>()

    fun fetchData(matchingid:Int, myTeamID : Int){

        ModelMatching.getInstance().lookMatchingTeam(matchingid,myTeamID) { isSuccess: Boolean, response: ShowMatchingTeamInfoResponse? ->

            if(isSuccess==true){
                data.value = response
                dataEmpty.value=false

            }else{
                data.value = null
                dataEmpty.value = true
            }
        }
    }

    fun sendLike(context : Context ,receiveId : Int, sendId : Int , message:String){
        ModelMatching.getInstance().firstSendHeart(receiveId,sendId,message) { isSuccess: Int, response: FirstSendHeartResponse? ->

            when(isSuccess){
                201-> {
                    Toast.makeText(context, "매칭 신청하기 성공 ", Toast.LENGTH_LONG).show()
                }
                400 ->  Toast.makeText(context, "매칭을 신청 할 수 있는 팀이 아닙니다! .", Toast.LENGTH_LONG).show()
                403 ->  Toast.makeText(context, "팀에 속해있지 않습니다. ", Toast.LENGTH_LONG).show()
            }
        }
    }

}