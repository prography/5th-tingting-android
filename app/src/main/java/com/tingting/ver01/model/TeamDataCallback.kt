package com.tingting.ver01.model

import com.tingting.ver01.model.Matching.ShowAllCandidateListResponse
import com.tingting.ver01.model.Matching.ShowAppliedTeamInfoResponse
import com.tingting.ver01.model.Matching.ShowMatchingTeamInfoResponse
import com.tingting.ver01.model.profile.LookMyTeamInfoProfileResponse
import com.tingting.ver01.model.team.LookIndivisualTeam.IndivisualTeamResponse
import com.tingting.ver01.model.team.LookTeamList.TeamResponse
import com.tingting.ver01.model.team.lookMyTeamInfoDetail.LookMyTeamInfoDetailResponse

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