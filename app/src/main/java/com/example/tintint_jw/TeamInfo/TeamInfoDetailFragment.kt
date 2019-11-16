package com.example.tintint_jw.TeamInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tintint_jw.Matching.PagerAdapter
import com.example.tintint_jw.Matching.TeamInfoPagerAdapter
import com.example.tintint_jw.R
import com.example.tintint_jw.View.TeamInfo
import kotlinx.android.synthetic.main.fragment_matching_request.*
import kotlinx.android.synthetic.main.fragment_matching_request.view.*
import kotlinx.android.synthetic.main.fragment_search_team.view.*


class TeamInfoDetailFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_team_info_detail, null)

        val adapter= TeamInfoPagerAdapter(activity!!.supportFragmentManager)
        view.MatchingViewPager.adapter=adapter
        view.tab.setupWithViewPager(MatchingViewPager)


        return view
    }

}