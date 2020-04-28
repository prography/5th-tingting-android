package com.tingting.ver01.teamInfo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.tingting.ver01.databinding.FragmentProfileTeaminfoMatchingStatusBinding
import com.tingting.ver01.profileTeamInfo.profileTeamInfoReady.ProfileTeamInfoMatchingStatusRecyclerAdapter
import com.tingting.ver01.viewModel.ProfileTeamInfoViewModel
import kotlinx.android.synthetic.main.fragment_profile_teaminfo_matching_status.*


class ProfileTeamInfoMatchingStatusFragment(var teamId:Int) : Fragment(){

    lateinit var matchingStatusRecyclerAdapter: ProfileTeamInfoMatchingStatusRecyclerAdapter
    lateinit var dataBinding : FragmentProfileTeaminfoMatchingStatusBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

            Log.d("Fragment","ProfileTeamINfoMatching실행")

        dataBinding = FragmentProfileTeaminfoMatchingStatusBinding.inflate(inflater,container,false).apply {
            viewmodel = ViewModelProviders.of(this@ProfileTeamInfoMatchingStatusFragment).get(
                ProfileTeamInfoViewModel::class.java)

                lifecycleOwner = viewLifecycleOwner
        }

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataBinding.viewmodel?.fetchInfo(teamId)
        setAdapter()
        setObserver()

    }

    private fun setObserver() {
        dataBinding.viewmodel?.data?.observe(viewLifecycleOwner, Observer {
            if(it.data.teamMatchings.size!=0){
                matchingStatusRecyclerAdapter.updateData(it)
            }
        })
    }

    fun setAdapter(){
        val data = dataBinding.viewmodel

        if(data?.data?.value?.data?.teamMatchings?.size != 0){
            val deco = TeamInfoRecyclerViewMargin(10)
            dataBinding.teamMatchingStatusRecyclerView.addItemDecoration(deco)

            matchingStatusRecyclerAdapter =
                ProfileTeamInfoMatchingStatusRecyclerAdapter(
                    dataBinding.viewmodel!!,teamId)
            val layoutManager = LinearLayoutManager(activity)
            teamMatchingStatusRecyclerView.layoutManager = layoutManager
            teamMatchingStatusRecyclerView.adapter = matchingStatusRecyclerAdapter
        }

    }



}