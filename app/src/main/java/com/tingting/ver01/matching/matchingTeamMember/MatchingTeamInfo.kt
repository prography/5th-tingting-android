package com.tingting.ver01.matching.matchingTeamMember

import android.os.Bundle
import android.view.View
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
import com.tingting.ver01.model.matching.ShowMatchingTeamInfoResponse
import com.tingting.ver01.searchTeam.searchTeamMemberInfo.MatchingApplyTeamInfoAdapter
import com.tingting.ver01.teamInfo.TeamInfoRecyclerViewMargin
import com.tingting.ver01.viewModel.MatchingApplyTeamInfoViewModel
import kotlinx.android.synthetic.main.activity_search_team_info.*
import kotlinx.android.synthetic.main.dialog_send_message.view.*
import kotlinx.android.synthetic.main.dialog_univ_list.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MatchingTeamInfo : AppCompatActivity() {

    lateinit var teamMemberAdapter: MatchingApplyTeamInfoAdapter
    lateinit var dataBinding: ActivityMatchingApplyTeamInfoBinding
     var matchingId: Int =0
    var bossId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_matching_apply_team_info)

        dataBinding.viewmodel =
            ViewModelProviders.of(this).get(MatchingApplyTeamInfoViewModel::class.java)


         matchingId = intent.getIntExtra("MatchingTeamId",0)

        // 내가 현재 속한 팀 id
        var myTeamId = intent.getIntExtra("MyTeamId", 0)

        dataBinding.viewmodel?.fetchData(matchingId ,myTeamId)

        dataBinding.lifecycleOwner = this

        dataBinding.like.setOnClickListener(){

            val messgDialog = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.dialog_send_message, null)

            messgDialog.setView(dialogView)

            val check = messgDialog.show()
            val drawable = resources.getDrawable(R.drawable.dialog)

            dialogView.send.setOnClickListener {
                if (dialogView.message.text.toString().isEmpty()) {
                    Toast.makeText(applicationContext, "메시지를 입력해주세요", Toast.LENGTH_LONG).show()

                }else{

                    ModelMatching.getInstance().firstSendHeart(matchingId,myTeamId,dialogView.message.text.toString(),{ isSuccess: Int, response: FirstSendHeartResponse? ->

            when(isSuccess){
                201-> {
                    Toast.makeText(applicationContext, "매칭 신청하기 성공 ", Toast.LENGTH_LONG).show()
                    CoroutineScope(Dispatchers.Main).launch {
                        dataBinding.like.text = "수락 대기 중 입니다."
                        dataBinding.like.isEnabled = false
                    }
                }

                400 ->  Toast.makeText(applicationContext, "매칭을 신청 할 수 있는 팀이 아닙니다! .", Toast.LENGTH_LONG).show()
                403 ->  Toast.makeText(applicationContext, "팀에 속해있지 않습니다. ", Toast.LENGTH_LONG).show()
            }
                    })

                    check.dismiss()
                }

            }
            }


        // back button event
        dataBinding.back.setOnClickListener {
            finish()
        }


        //Edit Team info button click

        setObserver()
        setApdater()

    }




    fun setObserver() {
        dataBinding.viewmodel?.data?.observe(this, Observer {
            teamMemberAdapter.update(it)
            initview(it)
        })
    }

    fun setApdater() {
        var data = dataBinding.viewmodel

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
        var maxNum = item.data.teamInfo.max_member_number


        dataBinding.totalNumberInfo.text = maxNum.toString() + ":" + maxNum.toString()

        if(item.data.isHeartSent){
            dataBinding.like.text = "수락 대기 중 입니다."
            dataBinding.like.isEnabled = false
        }else{
            dataBinding.like.text = "좋아요"
        }

        var tag = item.data.teamInfo.tags

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


