package com.tingting.ver01.viewModel

import androidx.lifecycle.MutableLiveData
import com.tingting.ver01.model.ModelTeam
import com.tingting.ver01.model.team.lookMyTeamInfoDetail.LookMyTeamInfoDetailResponse

class ProfileTeamInfoViewModel : BaseViewModel() {

    val data = MutableLiveData<LookMyTeamInfoDetailResponse>()
    val data2 = MutableLiveData<Int>()

    fun fetchInfo(id: Int) {
        dataLoading.value = true
        ModelTeam.getInstance()
            .lookMyTeamInfo(id) { isSuccessful: Boolean, response: LookMyTeamInfoDetailResponse? ->
                dataLoading.value = false
                if (isSuccessful) {
                    data.value = response

                    empty.value = false
                } else {
                    empty.value = true
                }
            }

    }



}