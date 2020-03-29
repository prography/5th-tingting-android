package com.tingting.ver01.searchTeam

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.tingting.ver01.BR
import com.tingting.ver01.R
import com.tingting.ver01.databinding.ActivitySearchTeammemberInfoDetailBinding
import com.tingting.ver01.matching.SearchTeamMemberPagerAdapter
import com.tingting.ver01.model.team.lookIndivisualTeam.IndivisualTeamResponse
import com.tingting.ver01.viewModel.TeamInfoActivityViewModel
import kotlinx.android.synthetic.main.activity_search_teammember_info_detail.*

class SearchTeamInfoDetailActivity : AppCompatActivity() {

    lateinit var dataBinding: ActivitySearchTeammemberInfoDetailBinding
    lateinit var teamTeamMemberAdapter: SearchTeamMemberPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_teammember_info_detail)

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_search_teammember_info_detail)
        dataBinding.viewmodel =
            ViewModelProviders.of(this).get(TeamInfoActivityViewModel::class.java)

        dataBinding.viewmodel?.fetchData(SearchTeamAdapter.teamId)
        backButton.bringToFront()

        backButton.setOnClickListener {
            finish()
        }

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

            teamTeamMemberAdapter =
                SearchTeamMemberPagerAdapter(supportFragmentManager, item)

            searchTeamMemberInfoViewPager.adapter = teamTeamMemberAdapter

            tab.setupWithViewPager(searchTeamMemberInfoViewPager)

        searchTeamMemberInfoViewPager.currentItem = 0
            teamTeamMemberAdapter.notifyDataSetChanged()

    }
}
