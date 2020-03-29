package com.tingting.ver01.viewModel

import androidx.lifecycle.MutableLiveData
import com.tingting.ver01.model.profile.GetTeammberProfileResponse
import com.tingting.ver01.model.profile.ModelProfile

class FirstPagerFragmentViewModel :BaseViewModel(){

    var indivisualProfile = MutableLiveData<GetTeammberProfileResponse>()


    fun fetchData(id : Int){
        dataLoading.value = true

        ModelProfile.getProfileInstance().getTeammemberInfo(id){ isSuccess: Boolean, response: GetTeammberProfileResponse? ->
           dataLoading.value = false
            if(isSuccess){
                indivisualProfile.value = response
                empty.value = false
            }else{
                empty.value = true
            }

        }
    }
}