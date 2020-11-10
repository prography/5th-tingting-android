package com.tingting.ver01.matching.matchingTeamMember

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.tingting.ver01.BR
import com.tingting.ver01.R
import com.tingting.ver01.databinding.ActivityMatchingApplyTeamInfoBinding
import com.tingting.ver01.model.ModelMatching
import com.tingting.ver01.model.matching.FirstSendHeartResponse
import com.tingting.ver01.model.matching.ShowAllCandidateListResponse
import com.tingting.ver01.model.matching.ShowMatchingTeamInfoResponse
import com.tingting.ver01.searchTeam.searchTeamMemberInfo.MatchingApplyTeamInfoAdapter
import com.tingting.ver01.teamInfo.TeamInfoRecyclerViewMargin
import com.tingting.ver01.viewModel.MatchingApplyTeamInfoViewModel
import com.tingting.ver01.viewModel.MatchingFragmentViewModel
import kotlinx.android.synthetic.main.activity_matching_apply_team_info.*
import kotlinx.android.synthetic.main.activity_search_team_info.*
import kotlinx.android.synthetic.main.activity_search_team_info.teamRecyclerView
import kotlinx.android.synthetic.main.dialog_send_message.view.*
import kotlinx.android.synthetic.main.dialog_univ_list.view.*
import kotlinx.android.synthetic.main.team_choice_spinner.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MatchingTeamInfo : AppCompatActivity() {

    lateinit var teamMemberAdapter: MatchingApplyTeamInfoAdapter
    lateinit var dataBinding: ActivityMatchingApplyTeamInfoBinding
     var matchingId: Int =0
     var bossId = 0
    var myTeamId = 0

    var myTeamArray : ArrayList<String> = ArrayList()
    var myTeamIdInfo : ArrayList<Int> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_matching_apply_team_info)

        dataBinding.viewmodel =
            ViewModelProviders.of(this).get(MatchingApplyTeamInfoViewModel::class.java)

        dataBinding.matchingFragmentViewModel = ViewModelProviders.of(this).get(MatchingFragmentViewModel::class.java)


        dataBinding.matchingFragmentViewModel!!.fetchdata(0,0)

        matchingId = intent.getIntExtra("MatchingTeamId",0)
        myTeamArray.add("팀을 선택해주세요")
        // 내가 현재 속한 팀 id

        dataBinding.viewmodel?.fetchData(matchingId ,myTeamId)

        dataBinding.lifecycleOwner = this



        dataBinding.like.setOnClickListener(){

            dataBinding.teamSpinner.visibility = View.VISIBLE

            }


        // back button event
        dataBinding.back.setOnClickListener {
            finish()
        }

        dataBinding.teamSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

            if(position!=0){
                myTeamId = myTeamIdInfo.get(position-1)
                Log.e("myTeamLog" , myTeamId.toString())

                val messgDialog = AlertDialog.Builder(this@MatchingTeamInfo)
                val dialogView = layoutInflater.inflate(R.layout.dialog_send_message, null)

                messgDialog.setView(dialogView)

                val check = messgDialog.show()
                val drawable = resources.getDrawable(R.drawable.dialog)

                dialogView.send.setOnClickListener {
                    if (dialogView.message.text.toString().isEmpty()) {
                        Toast.makeText(applicationContext, "메시지를 입력해주세요", Toast.LENGTH_LONG).show()

                    }else{

                        ModelMatching.getInstance().firstSendHeart(matchingId,myTeamId,dialogView.message.text.toString()) { isSuccess: Int, response: FirstSendHeartResponse? ->

                            when(isSuccess){
                                201-> {
                                    Toast.makeText(applicationContext, "매칭 신청하기 성공 ", Toast.LENGTH_LONG).show()
                                    CoroutineScope(Dispatchers.Main).launch {
                                        dataBinding.like.text = "수락 대기 중 입니다."
                                        dataBinding.like.isEnabled = false
                                    }
                                }

                                400 ->  Toast.makeText(applicationContext, "매칭을 신청 할 수 있는 팀이 아닙니다!. 팀원수를 확인해 주세요.", Toast.LENGTH_LONG).show()
                                403 ->  Toast.makeText(applicationContext, "팀에 속해있지 않습니다. ", Toast.LENGTH_LONG).show()
                            }
                        }
                        check.dismiss()
                    }

                }
            }


            }
        }





        setObserver()
        setApdater()

    }





    fun setObserver() {
        dataBinding.viewmodel?.data?.observe(this, Observer {
            teamMemberAdapter.update(it)
            initview(it)
        })

        dataBinding.matchingFragmentViewModel!!.data.observe(this, Observer {

            for(i in it.data.myTeamList.indices){
                val stringFormat = String.format("%s %d명",it.data.myTeamList.get(i).name, it.data.myTeamList.get(i).max_member_number)
                myTeamIdInfo.add(it.data.myTeamList.get(i).id)
                myTeamArray.add(stringFormat)
            }

            val myAdapter = ArrayAdapter(this ,  android.R.layout.simple_spinner_dropdown_item, myTeamArray)
            dataBinding.teamSpinner.adapter = myAdapter

        })

    }

    fun setApdater() {
        val data = dataBinding.viewmodel

        if (data != null) {
            val deco = TeamInfoRecyclerViewMargin(10)
            teamRecyclerView.addItemDecoration(deco)

            teamMemberAdapter = MatchingApplyTeamInfoAdapter(dataBinding.viewmodel!!, this, matchingId)
            val layoutManager = GridLayoutManager(this, 2)
            teamRecyclerView.layoutManager = layoutManager
            teamRecyclerView.adapter = teamMemberAdapter
        }
    }

    fun initview(item : ShowMatchingTeamInfoResponse) {
        dataBinding.setVariable(BR.teaminfoItem,item)
        dataBinding.executePendingBindings()

        var gender = item.data.teamInfo.gender
        val maxNum = item.data.teamInfo.max_member_number


        dataBinding.totalNumberInfo.text = maxNum.toString() + ":" + maxNum.toString()

        if(item.data.isHeartSent){
            dataBinding.like.text = "수락 대기 중 입니다."
            dataBinding.like.isEnabled = false
        }else{
            dataBinding.like.text = "좋아요"
        }

        val tag = item.data.teamInfo.tags

        for( i in 0..tag.size-1){
            when(i){
                0-> {
                    dataBinding.tag1.setText("#"+tag.get(0))
                    dataBinding.tag1.visibility = View.VISIBLE
                }
                1-> {
                    dataBinding.tag2.setText("#"+tag.get(1))
                    dataBinding.tag2.visibility = View.VISIBLE
                }
                2-> {
                    dataBinding.tag3.setText("#"+tag.get(2))
                    dataBinding.tag3.visibility = View.VISIBLE
                }
            }

        }
    }


}


