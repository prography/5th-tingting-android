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
import com.tingting.ver01.R
import com.tingting.ver01.databinding.FragmentProfileTeaminfoMatchingStatusBinding
import com.tingting.ver01.profileTeamInfo.profileTeamInfoReady.ProfileTeamInfoMatchingStatusRecyclerAdapter
import com.tingting.ver01.viewModel.ProfileTeamInfoViewModel
import kotlinx.android.synthetic.main.fragment_profile_teaminfo_matching_status.*


class ProfileTeamInfoMatchingRecordFragment() : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        var view:View = inflater.inflate(R.layout.fragmetn_profile_teaminfo_record, container, false)

        return view
    }






}