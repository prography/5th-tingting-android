package com.example.tintint_jw.Matching

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.tintint_jw.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_matching_detail.*
import kotlinx.android.synthetic.main.fragment_matching_request.*
import kotlinx.android.synthetic.main.fragment_matching_request.MatchingViewPager
import kotlinx.android.synthetic.main.fragment_matching_request.view.*
import me.relex.circleindicator.CircleIndicator

class MatchingRequest : Fragment() {

    var indicator:LinearLayout ?= null
    var dotCount:Int?=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_matching_request, null)
        // 처리


        val adapter= PagerAdapter(activity!!.supportFragmentManager)
        view.MatchingViewPager.adapter = adapter
        (view.MatchingViewPager.adapter as PagerAdapter).notifyDataSetChanged()
        view.tab.setupWithViewPager(MatchingViewPager)

        // pager indicator
        dotCount = adapter.getCount()
        /*var viewPager:ViewPager = view.findViewById(R.id.MatchingViewPager)
        var circleIndicator:CircleIndicator = view.findViewById(R.id.indicator)
        circleIndicator.setViewPager(viewPager)*/

        view.backButton.setOnClickListener(){
            activity!!.supportFragmentManager.popBackStack()
        }



        return view
    }
}