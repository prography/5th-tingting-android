package com.tingting.ver01.viewModel

import androidx.lifecycle.MutableLiveData
import com.tingting.ver01.model.team.lookTeamList.TeamResponse
import com.tingting.ver01.model.team.ModelSearchTeam

class SearchTeamFragmentViewModel : BaseViewModel(){

    //ViewModel은 데이터 타입을 받아와서 view에 전달해줘야한다.
    //데이터 타입 선언
 var teamLiveData = MutableLiveData<TeamResponse>()

    //model로 부터 데이터 받아오기
    fun fetchTeamInfo(){
        dataLoading.value = false

        ModelSearchTeam.getProfileInstance().showTeamList{ isSuccess: Boolean, response: TeamResponse? ->
            dataLoading.value=true

            if(isSuccess){
                teamLiveData.value = response
                empty.value=false
            }else{
                empty.value=true
            }
        }

    }
}