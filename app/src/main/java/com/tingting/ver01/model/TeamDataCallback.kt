package com.tingting.ver01.model

import com.tingting.ver01.model.matching.ShowAllCandidateListResponse
import com.tingting.ver01.model.matching.ShowAppliedTeamInfoResponse
import com.tingting.ver01.model.matching.ShowMatchingTeamInfoResponse
import com.tingting.ver01.model.profile.LookMyTeamInfoProfileResponse
import com.tingting.ver01.model.team.lookIndivisualTeam.IndivisualTeamResponse
import com.tingting.ver01.model.team.lookMyTeamInfoDetail.LookMyTeamInfoDetailResponse
import com.tingting.ver01.model.team.lookMyTeamInfoDetail.LookTeamTagResponse
import com.tingting.ver01.model.team.lookTeamList.TeamResponse

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

    fun LookTeamTag(data: LookTeamTagResponse){

    }

}