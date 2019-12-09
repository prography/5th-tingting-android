package com.example.tintint_jw.View

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tintint_jw.R
import com.example.tintint_jw.TeamInfo.*
import kotlinx.android.synthetic.main.dialog_view.view.*
import kotlinx.android.synthetic.main.fragment_search_team_info.view.*
import kotlinx.android.synthetic.main.fragment_team_info.view.*
import kotlinx.android.synthetic.main.fragment_team_info.view.EditTeamInfo
import kotlinx.android.synthetic.main.fragment_team_info.view.back
import kotlinx.android.synthetic.main.fragment_team_info.view.teamRecyclerView

class TeamInfo : Fragment() {

    var teamlist = arrayListOf<TeamInfoData>()
    var matchinglist = arrayListOf<MatchingData>()

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val view = inflater.inflate(R.layout.fragment_team_info, null)
            // 처리
            val size =resources.getDimensionPixelSize(R.dimen.wide_size)

            // back button event
            view.back.setOnClickListener(){
                activity!!.supportFragmentManager.popBackStack()
            }

            //Edit Team info button click

            view.EditTeamInfo.setOnClickListener(){
                activity!!.supportFragmentManager.beginTransaction().add(R.id.mainFragment,EditTeamInfoFragment()).commit()
            }

            // 팀장이면 background change하는 코드 추가.
            teamlist.add(TeamInfoData(R.drawable.iu, "팀장", "iu"))
            teamlist.add(TeamInfoData(R.drawable.iu, "팀장", "iu2"))






            val Adapter = TeamInfoAdapter(activity!!.applicationContext,teamlist){
                teamInfoData -> fragmentManager?.beginTransaction()
                ?.add(R.id.mainFragment,TeamInfoDetailFragment())?.commit()
            }

            matchinglist.add(MatchingData("1"))
            matchinglist.add(MatchingData("2"))

            val MatchingAdapter = MatchingAdapter(activity!!.applicationContext,matchinglist){
                it-> fragmentManager?.beginTransaction()
                ?.add(R.id.mainFragment,TeamInfoDetailFragment())?.commit()
            }

            //apply maring to adapter.
            val deco = TeamInfoRecyclerViewMargin(size)
            view.teamRecyclerView.addItemDecoration(deco)
            view.teamRecyclerView2.addItemDecoration(deco)

            view.teamRecyclerView.adapter = Adapter
            view.teamRecyclerView2.adapter = MatchingAdapter

            val lm = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)
            val lm2 = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
            view.teamRecyclerView.layoutManager = lm
            view.teamRecyclerView2.layoutManager = lm2
            view.teamRecyclerView.setHasFixedSize(true)
            view.teamRecyclerView2.setHasFixedSize(true)

            return view
        }
    }

