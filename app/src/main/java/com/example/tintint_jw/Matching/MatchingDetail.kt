package com.example.tintint_jw.Matching


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tintint_jw.R
import kotlinx.android.synthetic.main.activity_matching_detail.*
import kotlinx.android.synthetic.main.fragment_matching_request.MatchingViewPager


class MatchingDetail : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matching_detail)

        val adapter = TeamInfoPagerAdapter(this.supportFragmentManager)
        adapter.notifyDataSetChanged()

        MatchingViewPager.adapter = adapter
        MatchingTab.setupWithViewPager(MatchingViewPager)

        backButtonMatching.setOnClickListener(){
            finish()
        }

        applyDate.setOnClickListener(){
            finish()
            Toast.makeText(this,"신청되었습니다.",Toast.LENGTH_SHORT).show()
        }
    }
}