package com.example.tintint_jw.Matching


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tintint_jw.Model.Matching.ShowMatchingTeamInfoResponse
import com.example.tintint_jw.Model.ModelMatching
import com.example.tintint_jw.Model.TeamDataCallback
import com.example.tintint_jw.R
import kotlinx.android.synthetic.main.activity_matching_detail.*
import kotlinx.android.synthetic.main.activity_matching_request.MatchingViewPager


class MatchingDetail : AppCompatActivity(){

    val model : ModelMatching = ModelMatching(Acontext = this)
    lateinit var matchingdata : ShowMatchingTeamInfoResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matching_detail)

        val adapter = TeamInfoPagerAdapter(this.supportFragmentManager)

        adapter.notifyDataSetChanged()

        var matchingId = intent.getIntExtra("MatchingTeamId",0)
        var myTeamId = intent.getIntExtra("MyTeamId", 0)
        //init screen

        model.lookMatchingTeam(matchingId, myTeamId, object : TeamDataCallback{
            override fun LookMatchingTeamInfo(data: ShowMatchingTeamInfoResponse) {

            }
        })

        MatchingViewPager.adapter = adapter
        MatchingTab.setupWithViewPager(MatchingViewPager)

        backButtonMatching.setOnClickListener{
            finish()
        }

    }
}