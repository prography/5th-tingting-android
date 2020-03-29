package com.tingting.ver01.profileTeamInfo.profileTeamMember

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.tingting.ver01.BR
import com.tingting.ver01.R
import com.tingting.ver01.databinding.ActivityProfileTeamMemeberDetailBinding
import com.tingting.ver01.model.team.lookIndivisualTeam.IndivisualTeamResponse
import com.tingting.ver01.viewModel.TeamInfoActivityViewModel
import kotlinx.android.synthetic.main.activity_profile_team_memeber_detail.*
import kotlinx.android.synthetic.main.activity_search_teammember_info_detail.backButton
import kotlinx.android.synthetic.main.activity_search_teammember_info_detail.tab


class TeamInfoProfileDetailActivity : AppCompatActivity() {

    lateinit var teamMemberadapter: ProfileTeamMemberPagerAdapter
    lateinit var dataBinding: ActivityProfileTeamMemeberDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_profile_team_memeber_detail)
        dataBinding.viewmodel =
            ViewModelProviders.of(this).get(TeamInfoActivityViewModel::class.java)

        var boosId = intent.getIntExtra("MyTeamId", 0)

        dataBinding.viewmodel?.fetchData(boosId)

        //비율을 위해서 화면 크기에 따라서 조절
        backButton.setOnClickListener {
            finish()
        }
        backButton.bringToFront()

        observer()
    }

    fun observer() {
        dataBinding.viewmodel?.teamData?.observe(this, androidx.lifecycle.Observer {
            setAdapter(it)
        })


    }

    fun setAdapter(item: IndivisualTeamResponse) {


        dataBinding.setVariable(BR.teaminfoItem,item)
        dataBinding.executePendingBindings()
        Log.d("adapterData",item.data.teamMembers.toString())

        teamMemberadapter = ProfileTeamMemberPagerAdapter(supportFragmentManager, item)

        profileTeamMemberInfoViewPager.adapter = teamMemberadapter

        tab.setupWithViewPager(profileTeamMemberInfoViewPager)

        profileTeamMemberInfoViewPager.currentItem = 0
        teamMemberadapter.notifyDataSetChanged()


    }

}
