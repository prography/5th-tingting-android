package com.tingting.ver01.viewModel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.tingting.ver01.model.profile.GetProfileResponse
import com.tingting.ver01.model.profile.ModelProfile
import com.tingting.ver01.view.Auth.PictureRegisterActivity
import com.tingting.ver01.view.Main.MainActivity

class ProfileFragmentViewModel :BaseViewModel(){

    //
    var profileUserLiveData = MutableLiveData<GetProfileResponse>()


    fun fetchuserInfo(context: Context){
        dataLoading.value = true
        //나는 인스턴스로 데이터를 주입했으나
        //dagger를 활용해서 데이터를 주입 할 수 도있다.
        ModelProfile.getProfileInstance().getProfile{ isSuccess, response ->
            dataLoading.value=false
            if(isSuccess){

//                val url =  response?.data?.myInfo?.thumbnail
//                var test = url?.let { MainActivity.glide.DecryptUrl(it)}
//
//                    Log.d("pictureTest",test.toString())
//
//
//                if(url?.let { MainActivity.glide.DecryptUrl(it) } == null){
//                    val intent = Intent(context, PictureRegisterActivity::class.java)
//                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
//                    Toast.makeText(context,"등록 된 사진이 없습니다. 사진을 등록해주시길 바랍니다.",Toast.LENGTH_SHORT).show()
//                    context.startActivity(intent )
//                }

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