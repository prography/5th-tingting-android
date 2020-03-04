package com.tingting.ver01.viewModel

import androidx.lifecycle.MutableLiveData
import com.tingting.ver01.model.profile.GetProfileResponse
import com.tingting.ver01.model.profile.ModelProfile

class ProfileFragmentViewModel :BaseViewModel(){


    var profileUserLiveData = MutableLiveData<GetProfileResponse>()

    fun fetchuserInfo(){
        dataLoading.value = true
        ModelProfile.getProfileInstance().getProfile{ isSuccess, response ->
            dataLoading.value=false
            if(isSuccess){
                profileUserLiveData.value = response
                empty.value = false

            }else{
                empty.value= true
            }
        }
    }

}