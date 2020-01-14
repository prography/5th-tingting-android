package com.example.tintint_jw.Model

import com.example.tintint_jw.Model.Team.LookTeamList.TeamResponse
import com.example.tintint_jw.Model.Team.MakeTeam.MakeTeamResponse

interface TeamDataCallback {

    fun onResult(data: TeamResponse?, start:Int, end:Int){}

    fun onIndivisualResult(data: MakeTeamResponse?, start:Int, end:Int){
    }
}