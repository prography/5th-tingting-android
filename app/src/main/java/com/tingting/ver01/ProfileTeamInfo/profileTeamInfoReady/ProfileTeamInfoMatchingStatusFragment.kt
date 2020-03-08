package com.tingting.ver01.teamInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.tingting.ver01.ProfileTeamInfo.profileTeamInfoReady.ProfileTeamInfoMatchingStatusRecyclerAdapter
import com.tingting.ver01.databinding.FragmentProfileTeaminfoMatchingStatusBinding
import com.tingting.ver01.viewModel.ProfileTeamInfoViewModel
import kotlinx.android.synthetic.main.fragment_profile_teaminfo_matching_status.*


class ProfileTeamInfoMatchingStatusFragment : Fragment(){

    lateinit var matchingStatusRecyclerAdapter: ProfileTeamInfoMatchingStatusRecyclerAdapter
    lateinit var dataBinding : FragmentProfileTeaminfoMatchingStatusBinding



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

            dataBinding = FragmentProfileTeaminfoMatchingStatusBinding.inflate(inflater,container,false).apply {
            viewmodel = ViewModelProviders.of(this@ProfileTeamInfoMatchingStatusFragment).get(
                ProfileTeamInfoViewModel::class.java)

            setLifecycleOwner(lifecycleOwner)
        }

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        val data = dataBinding?.viewmodel

        if(data?.data?.value?.data?.teamMatchings?.size != 0){
            matchingStatusRecyclerAdapter =
                ProfileTeamInfoMatchingStatusRecyclerAdapter(
                    dataBinding?.viewmodel!!
                )
            val layoutManager = LinearLayoutManager(activity)
            teamMatchingStatusRecyclerView.layoutManager = layoutManager
            teamMatchingStatusRecyclerView.adapter = matchingStatusRecyclerAdapter
        }

    }



}