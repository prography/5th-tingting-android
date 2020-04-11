package com.tingting.ver01.viewModel

import androidx.lifecycle.MutableLiveData
import com.tingting.ver01.model.ModelTeam
import com.tingting.ver01.model.TeamDataCallback
import com.tingting.ver01.model.team.lookIndivisualTeam.IndivisualTeamResponse

class TeamInfoActivityViewModel :BaseViewModel(){

    var teamData = MutableLiveData<IndivisualTeamResponse>()


    fun fetchData(bossId  : Int){
        dataLoading.value = false

        ModelTeam.getInstance().showIndivisualTeamList(bossId , object :TeamDataCallback{
            override fun onIndivisualResult(data: IndivisualTeamResponse?, start: Int, end: Int) {
                super.onIndivisualResult(data, start, end)
                teamData.value = data
            }
        })

        }


    }
