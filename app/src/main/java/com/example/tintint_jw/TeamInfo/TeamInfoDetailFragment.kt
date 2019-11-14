package com.example.tintint_jw.TeamInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tintint_jw.R
import com.example.tintint_jw.View.TeamInfo
import kotlinx.android.synthetic.main.fragment_search_team.view.*


class TeamInfoDetailFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_team_info_detail, null)



        return view
    }

}