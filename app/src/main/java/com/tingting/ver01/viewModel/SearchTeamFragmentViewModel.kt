package com.tingting.ver01.viewModel

import android.content.Context
import android.content.Intent
import android.media.audiofx.DynamicsProcessing
import android.os.Vibrator
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.tingting.ver01.model.ModelTeam
import com.tingting.ver01.model.team.ModelSearchTeam
import com.tingting.ver01.model.team.lookTeamList.TeamResponse
import com.tingting.ver01.searchTeam.SearchTeamInfo
import com.tingting.ver01.view.Main.SearchTeamFragment

class SearchTeamFragmentViewModel : BaseViewModel() {

    //ViewModel은 데이터 타입을 받아와서 view에 전달해줘야한다.
    //데이터 타입 선언
    var teamLiveData = MutableLiveData<ArrayList<TeamResponse?>>()
    var dataArray : ArrayList<TeamResponse?> = ArrayList()

    //model로 부터 데이터 받아오기
    fun fetchTeamInfo(limit:Int, page:Int ) {
        dataLoading.value = false

        ModelSearchTeam.getProfileInstance()
            .showTeamList(limit, page) { isSuccess: Boolean, response: TeamResponse? ->
                dataLoading.value = true

                if (isSuccess) {
                    dataArray.add(response)
                    teamLiveData.value = dataArray

                    empty.value = false
                } else {
                    empty.value = true
                }
                }

    }

    fun teamJoin(teamId: Int, password: String, context : Context) {
        dataLoading.value = false
        var result = 0

        ModelTeam.getInstance().JoinTeam(teamId, password) { isSuccess: Boolean, response: Int? ->
            dataLoading.value = true

                when(response){
                    201 ->   {
                        val searchTeamDetailIntent = Intent(context.applicationContext, SearchTeamInfo::class.java)
                        searchTeamDetailIntent.putExtra("teamBossId", teamId)
                        searchTeamDetailIntent.putExtra("teamPassword",password)

                        context.startActivity(searchTeamDetailIntent)
                    }
                    403 -> {
                        var vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

                        vibrator.vibrate(300)

                        Toast.makeText(context.applicationContext, "비밀번호가 틀렸습니다.", Toast.LENGTH_LONG).show()
                    }

                    404 ->  Toast.makeText(context.applicationContext, "합류할 수 있는 팀이 존재하지 않습니다.", Toast.LENGTH_LONG).show()
                    500 ->  Toast.makeText(context.applicationContext, "합류에 실패하였습니다.", Toast.LENGTH_LONG).show()
                    else->  Toast.makeText(context.applicationContext, "일시적인 서버 오류입니다. ", Toast.LENGTH_LONG).show()

                }
        }


    }

    fun addTeamInfo(limit:Int, page:Int ) {
        dataLoading.value = false

        ModelSearchTeam.getProfileInstance()
            .showTeamList(limit, page) { isSuccess: Boolean, response: TeamResponse? ->
                dataLoading.value = true

                if (isSuccess) {
                    dataArray.add(response)
                    teamLiveData.value = dataArray

                    empty.value = false
                } else {
                    empty.value = true
                }
            }

    }

    fun refresh(){
        teamLiveData.value?.clear()
        fetchTeamInfo(5,1)
    }


}