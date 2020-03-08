package com.tingting.ver01.ProfileTeamInfo.profileTeamInfoNotReady

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.tingting.ver01.R
import com.tingting.ver01.databinding.ActivityProfileNotReadyBinding
import com.tingting.ver01.teamInfo.ProfileTeamInfoNotReadyRecyclerViewAdapter
import com.tingting.ver01.teamInfo.TeamInfoRecyclerViewMargin
import com.tingting.ver01.viewModel.ProfileTeamInfoViewModel
import kotlinx.android.synthetic.main.activity_profile_not_ready.*

class ProfileTeamInfoNotReadyActivity : AppCompatActivity() {

    lateinit var dataBinding : ActivityProfileNotReadyBinding
    lateinit var notReadyAdapter : ProfileTeamInfoNotReadyRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_not_ready)

        dataBinding = DataBindingUtil.setContentView(this,R.layout.activity_profile_not_ready)
        dataBinding.viewmodel = ViewModelProviders.of(this).get(ProfileTeamInfoViewModel::class.java)

        dataBinding.setLifecycleOwner(this)

        var myTeamId = intent.getIntExtra("MyTeamId", 0)
        //data를 넣어주기 위해서!!!

        dataBinding.viewmodel?.fetchInfo(myTeamId)

        setAdapter()
        setObserver()
    }



    fun setObserver(){
        dataBinding.viewmodel?.data?.observe(this, Observer {
            notReadyAdapter.updateData(it)
        })
    }


    fun setAdapter(){
        var data = dataBinding?.viewmodel

        if(data!=null){
            val deco = TeamInfoRecyclerViewMargin(10)
            teamMemberRecyclerView.addItemDecoration(deco)

            notReadyAdapter = ProfileTeamInfoNotReadyRecyclerViewAdapter(dataBinding?.viewmodel!!)
            val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            teamMemberRecyclerView.layoutManager = layoutManager
            teamMemberRecyclerView.adapter = notReadyAdapter
        }
    }



}
