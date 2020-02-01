package com.tingting.ver01.Model

import com.tingting.ver01.Model.Profile.GetTeammberProfileResponse

interface ProfileCallBack {


    fun onSuccess(name:String,
                  birth:String,
                  height:String,
                  thumnail:String,
                  gender:String,
                  myTeamData: List<GetProfileResponse.Data.MyTeam>){

    }

    fun onSuccess2(name:String,
                  birth:String,
                  height:String,
                  thumnail:String,
                  gender:String,
                   school:String,
                  myTeamData: List<GetProfileResponse.Data.MyTeam>){

    }

    fun onTeammemberProfileSuccess(data : GetTeammberProfileResponse){

    }
}