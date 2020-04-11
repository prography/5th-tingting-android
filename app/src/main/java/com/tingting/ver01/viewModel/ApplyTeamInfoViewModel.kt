package com.tingting.ver01.viewModel

import androidx.lifecycle.MutableLiveData
import com.tingting.ver01.model.ModelMatching
import com.tingting.ver01.model.TeamDataCallback
import com.tingting.ver01.model.matching.ShowAppliedTeamInfoResponse
import com.tingting.ver01.model.team.lookIndivisualTeam.IndivisualTeamResponse

class ApplyTeamInfoViewModel :BaseViewModel(){

    var applyTeamData = MutableLiveData<ShowAppliedTeamInfoResponse>()


    fun fetchData(id:Int, myTeamId: Int){
        dataLoading.value = false

        ModelMatching.getInstance().lookAppliedMatchingTeam(id, myTeamId){isSuccess: Boolean, response: ShowAppliedTeamInfoResponse? ->
            if(isSuccess){
                applyTeamData.value=response
            }else{
                applyTeamData.value=null
            }
        }
    }

}