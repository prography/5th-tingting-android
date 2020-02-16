package com.tingting.ver01.Model

import LookMyTeamInfoDetailResponse
import com.tingting.ver01.Model.Matching.ShowAllCandidateListResponse
import com.tingting.ver01.Model.Matching.ShowAppliedTeamInfoResponse
import com.tingting.ver01.Model.Matching.ShowMatchingTeamInfoResponse
import com.tingting.ver01.Model.Profile.LookMyTeamInfoProfileResponse
import com.tingting.ver01.Model.Team.LookIndivisualTeam.IndivisualTeamResponse
import com.tingting.ver01.Model.Team.LookTeamList.TeamResponse

interface TeamDataCallback {

    fun onResult(data: TeamResponse?, start:Int, end:Int){}

    fun onIndivisualResult(data: IndivisualTeamResponse?, start:Int, end:Int){

    }

    fun showAllCandidateList(data : ShowAllCandidateListResponse?){

    }

    fun LookMyTeaminfoList(data : LookMyTeamInfoDetailResponse){

    }

    fun LookMatchingTeamInfo(data : ShowMatchingTeamInfoResponse){

    }
    fun LookMyTeamInfoListProfile(data : LookMyTeamInfoProfileResponse){

    }

    fun LookAppliedTeamInfo(data: ShowAppliedTeamInfoResponse){

    }

}