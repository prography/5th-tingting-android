package com.tingting.ver01.viewModel

import androidx.lifecycle.MutableLiveData
import com.tingting.ver01.model.ModelMatching
import com.tingting.ver01.model.matching.ShowAllCandidateListResponse

class MatchingFragmentViewModel :BaseViewModel(){

    var data = MutableLiveData<ShowAllCandidateListResponse>()

    fun fetchdata(){
        dataLoading.value =false
        ModelMatching.getInstance().lookTeamList { isSuccess: Boolean, response: ShowAllCandidateListResponse? ->
            if(isSuccess && response!=null){
                data.value = response
                empty.value = false
            }else{
                empty.value=true
            }
        }
    }


}