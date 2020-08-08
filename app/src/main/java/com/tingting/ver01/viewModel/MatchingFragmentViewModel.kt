package com.tingting.ver01.viewModel

import androidx.lifecycle.MutableLiveData
import com.tingting.ver01.model.ModelMatching
import com.tingting.ver01.model.matching.ShowAllCandidateListResponse
import com.tingting.ver01.model.team.lookTeamList.TeamResponse

class MatchingFragmentViewModel :BaseViewModel(){

    // 처음에 나의 팀 보여주기와 관련 된 데이터
    var data = MutableLiveData<ShowAllCandidateListResponse>()

    // 매칭 recyclerview와 관련 된 데이터
    var arrayData =MutableLiveData<ArrayList<ShowAllCandidateListResponse>>()
    var arrayDataItem : ArrayList<ShowAllCandidateListResponse> = ArrayList()

    fun fetchdata(limit : Int, page: Int){
        dataLoading.value =false

        ModelMatching.getInstance().lookTeamList(limit,page) { isSuccess: Boolean, response: ShowAllCandidateListResponse? ->
            if(isSuccess && response!=null){
                data.value = response

                arrayDataItem.add(response)
                arrayData.value = arrayDataItem
                empty.value = false

            }else{
                empty.value=true
            }
        }
    }


    fun addData(limit : Int, page : Int ){
        dataLoading.value =false
        arrayDataItem.clear()


        ModelMatching.getInstance().lookTeamList(limit,page) { isSuccess: Boolean, response: ShowAllCandidateListResponse? ->
            if(isSuccess && response!=null){
                arrayDataItem.add(response)
               arrayData.value = arrayDataItem
                empty.value = false
            }else{
                empty.value=true
            }
        }

    }

    fun refresh(){
        arrayDataItem.clear()
       arrayData.value?.clear()

        addData(20,1)
    }

}