package com.tingting.ver01.Matching

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tingting.ver01.model.Matching.ShowMatchingTeamInfoResponse
import com.tingting.ver01.R
import kotlinx.android.synthetic.main.activity_matching_detail.*
import kotlinx.android.synthetic.main.activity_matching_request.MatchingViewPager

class MatchingRequest : AppCompatActivity() {


    lateinit var matchingdata : ShowMatchingTeamInfoResponse
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matching_request)

        val adapter = TeamInfoPagerAdapter(this.supportFragmentManager)

        adapter.notifyDataSetChanged()


        MatchingViewPager.adapter = adapter
        MatchingTab.setupWithViewPager(MatchingViewPager)



        backButtonMatching.setOnClickListener(){
            finish()
        }

        /*applyDate.setOnClickListener(){
            finish()
            Toast.makeText(this,"신청되었습니다.",Toast.LENGTH_SHORT).show()
        }*/
    }
    /*var indicator:LinearLayout ?= null
    var dotCount:Int?=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_matching_request, null)
        // 처리


        val adapter= PagerAdapter(activity!!.supportFragmentManager)
        view.MatchingViewPager.adapter = adapter
        (view.MatchingViewPager.adapter as PagerAdapter).notifyDataSetChanged()
        view.tab.setupWithViewPager(MatchingViewPager)

        // pager indicator
        dotCount = adapter.getCount()
        *//*var viewPager:ViewPager = view.findViewById(R.id.MatchingViewPager)
        var circleIndicator:CircleIndicator = view.findViewById(R.id.indicator)
        circleIndicator.setViewPager(viewPager)*//*

        view.backButton.setOnClickListener(){
            activity!!.supportFragmentManager.popBackStack()
        }



        return view}*/

}