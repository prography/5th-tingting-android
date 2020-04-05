package com.tingting.ver01.profileTeamInfo.profileApply

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.tingting.ver01.BR
import com.tingting.ver01.R
import com.tingting.ver01.databinding.ActivityOtherTeamProfileBinding
import com.tingting.ver01.model.CodeCallBack
import com.tingting.ver01.model.ModelMatching
import com.tingting.ver01.model.ModelTeam
import com.tingting.ver01.model.team.lookIndivisualTeam.IndivisualTeamResponse
import com.tingting.ver01.searchTeam.searchTeamMemberInfo.SearchTeamMemberInfoAdapter
import com.tingting.ver01.teamInfo.TeamInfoRecyclerViewMargin
import com.tingting.ver01.viewModel.TeamInfoActivityViewModel
import kotlinx.android.synthetic.main.activity_search_team_info.*

class OtherTeamProfileActivity : AppCompatActivity() {

    lateinit var teamMemberAdapter: SearchTeamMemberInfoAdapter
    lateinit var dataBinding: ActivityOtherTeamProfileBinding

    var otehrTeamId = 0
    var myTeamId = 0
    var matchingNumber = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_other_team_profile)

        dataBinding.viewmodel =
            ViewModelProviders.of(this).get(TeamInfoActivityViewModel::class.java)

        // 처리
        val size = resources.getDimensionPixelSize(R.dimen.wide_size)

        otehrTeamId = intent.getIntExtra("matchingTeamId", 0)
        myTeamId = intent.getIntExtra("myTeamId", 0)
        matchingNumber = intent.getIntExtra("matchingId", 0)

        dataBinding.viewmodel?.fetchData(otehrTeamId)

        dataBinding.lifecycleOwner = this

        // back button event
        dataBinding.back.setOnClickListener {
            finish()
        }
        //Edit Team info button click

        //init screen
        dataBinding.agreeHeart.setOnClickListener {

                ModelMatching.getInstance().sendHeart(otehrTeamId, object:CodeCallBack{
                    override fun onSuccess(code: String, value: String) {
                        try{
                            if(code.equals("201")){
                                Toast.makeText(applicationContext, "매칭 신청하기 성공", Toast.LENGTH_LONG).show()
                            }
                            else if(code.equals("400")){
                                Toast.makeText(applicationContext, "매칭 정보가 없거나 이미 전원이 하트를 보냈습니다!", Toast.LENGTH_LONG).show()

                            }else if(code.equals("403")){
                                Toast.makeText(applicationContext, "팀에 속해있지 않습니다!", Toast.LENGTH_LONG).show()
                            }
                            else if(code.equals("500")){
                                Toast.makeText(applicationContext, "매칭 신청하기 실패", Toast.LENGTH_LONG).show()

                            }
                        }catch (e:Exception){
                        }
                    }
                })
        }

        setObserver()
        setApdater()

    }


    fun setObserver() {
        dataBinding.viewmodel?.teamData?.observe(this, Observer {
            teamMemberAdapter.update(it)
            initview(it)
        })
    }

    fun setApdater() {
        var data = dataBinding.viewmodel

        if (data != null) {
            val deco = TeamInfoRecyclerViewMargin(10)
            teamRecyclerView.addItemDecoration(deco)

            teamMemberAdapter = SearchTeamMemberInfoAdapter(dataBinding.viewmodel!!, this)
            val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            teamRecyclerView.layoutManager = layoutManager
            teamRecyclerView.adapter = teamMemberAdapter
        }
    }

    fun initview(item : IndivisualTeamResponse) {
        dataBinding.setVariable(BR.teaminfoItem,item)
        dataBinding.executePendingBindings()

        var gender = item.data.teamInfo.gender
        var maxNum = item.data.teamInfo.max_member_number

        when (gender) {
            0 -> dataBinding.genderInfo.text = "남자"
            1 -> dataBinding.genderInfo.text = "여자"
        }

        dataBinding.numberInfo.text = maxNum.toString() + ":" + maxNum.toString()

    }
}


