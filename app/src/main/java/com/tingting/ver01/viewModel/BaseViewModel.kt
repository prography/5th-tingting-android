package com.tingting.ver01.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel(){

    val empty = MutableLiveData<Boolean>().apply { value = false }

    val dataLoading = MutableLiveData<Boolean>().apply { value = false }

    val dataEmpty = MutableLiveData<Boolean>().apply { value = false }

    val loadMoreFalse = MutableLiveData<Boolean>().apply { value =false }
    val toastMessage = MutableLiveData<String>()

}