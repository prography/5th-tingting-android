package com.example.tintint_jw.Model

import LookMyTeamInfoDetailResponse
import com.example.tintint_jw.Model.Matching.ShowAllCandidateListResponse
import com.example.tintint_jw.Model.Matching.ShowAppliedTeamInfoResponse
import com.example.tintint_jw.Model.Matching.ShowMatchingTeamInfoResponse
import com.example.tintint_jw.Model.Profile.LookMyTeamInfoProfileResponse
import com.example.tintint_jw.Model.Team.LookIndivisualTeam.IndivisualTeamResponse
import com.example.tintint_jw.Model.Team.LookTeamList.TeamResponse

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