package com.example.tintint_jw.Matching


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tintint_jw.Matching.TeamInfoPagerAdapter
import com.example.tintint_jw.R
import kotlinx.android.synthetic.main.fragment_matching_detail.*
import kotlinx.android.synthetic.main.fragment_matching_detail.view.*
import kotlinx.android.synthetic.main.fragment_matching_request.*
import kotlinx.android.synthetic.main.fragment_matching_request.MatchingViewPager
import kotlinx.android.synthetic.main.fragment_matching_request.view.MatchingViewPager
import kotlinx.android.synthetic.main.fragment_matching_request.view.tab
import kotlinx.android.synthetic.main.fragment_team_info_detail.view.*
import kotlinx.android.synthetic.main.fragment_team_info_detail.view.backButton


class MatchingDetail : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_matching_detail, null)

        val adapter= TeamInfoPagerAdapter(activity!!.supportFragmentManager)

        view.MatchingViewPager.adapter=adapter
        view.tab.setupWithViewPager(MatchingViewPager)
        adapter.notifyDataSetChanged()
        view.backButton.setOnClickListener(){
            activity!!.supportFragmentManager.beginTransaction().remove(this).commit()
        }
        view.applyDate.setOnClickListener(){
            activity!!.supportFragmentManager.beginTransaction().remove(this).commit()
        }



        return view
    }

}