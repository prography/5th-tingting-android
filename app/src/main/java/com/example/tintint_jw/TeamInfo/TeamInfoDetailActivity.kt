package com.example.tintint_jw.TeamInfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tintint_jw.Matching.TeamInfoPagerAdapter
import com.example.tintint_jw.R
import kotlinx.android.synthetic.main.activity_matching_request.*
import kotlinx.android.synthetic.main.activity_matching_request.MatchingViewPager
import kotlinx.android.synthetic.main.activity_matching_request.view.*
import kotlinx.android.synthetic.main.activity_team_info_detail.*

class TeamInfoDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_info_detail)

        val adapter= TeamInfoPagerAdapter(this!!.supportFragmentManager)

        adapter.notifyDataSetChanged()

        MatchingViewPager.adapter=adapter
        tab.setupWithViewPager(MatchingViewPager)

        backButton.setOnClickListener(){
           finish()
        }

    }
}
