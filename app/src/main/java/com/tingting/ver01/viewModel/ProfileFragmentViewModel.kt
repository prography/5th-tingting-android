package com.tingting.ver01.viewModel

import androidx.lifecycle.MutableLiveData
import com.tingting.ver01.model.profile.GetProfileResponse
import com.tingting.ver01.model.profile.ModelProfile

class ProfileFragmentViewModel :BaseViewModel(){

    //
    var profileUserLiveData = MutableLiveData<GetProfileResponse>()


    fun fetchuserInfo(){
        dataLoading.value = true
        //나는 인스턴스로 데이터를 주입했으나
        //dagger를 활용해서 데이터를 주입 할 수 도있다.
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


    fun heartCancel(){

    }

}