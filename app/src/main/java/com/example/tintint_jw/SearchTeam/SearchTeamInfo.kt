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

class SearchTeamInfo : Fragment() {

    var teamlist = arrayListOf<TeamInfoData>()
    var matchinglist = arrayListOf<MatchingData>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search_team_info, null)
        // 처리
        val size =resources.getDimensionPixelSize(R.dimen.wide_size)

        // back button event
        view.back.setOnClickListener(){
            activity!!.supportFragmentManager.beginTransaction().remove(this).commit()
        }

        //Edit Team info button click

        view.EditTeamInfo.setOnClickListener(){

        }

        // 팀장이면 background change하는 코드 추가.
        teamlist.add(TeamInfoData(R.drawable.iu, "팀장", "iu"))
        teamlist.add(TeamInfoData(R.drawable.iu, "팀장", "iu2"))

        view.teamJoin.setOnClickListener() {

            val builder = AlertDialog.Builder(activity)
            val dialogView = layoutInflater.inflate(R.layout.dialog_team_info, null)

            val mbuilder = builder.setView(dialogView).show()

            dialogView.dialogOK.setOnClickListener(){
                val intent = Intent(activity!!.applicationContext,MainActivity::class.java)
                startActivity(intent)

            }

            dialogView.dialogCancel.setOnClickListener(){
                mbuilder.dismiss()
            }
        }

        val Adapter = TeamInfoAdapter(activity!!.applicationContext,teamlist){
          //      teamInfoData -> fragmentManager?.beginTransaction()
          //  ?.add(R.id.mainFragment,TeamInfoDetailFragment())?.commit()
        }


        //apply maring to adapter.
        val deco = TeamInfoRecyclerViewMargin(size)
        view.teamRecyclerView.addItemDecoration(deco)

        view.teamRecyclerView.adapter = Adapter

        val lm = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)
        view.teamRecyclerView.layoutManager = lm
        view.teamRecyclerView.setHasFixedSize(true)

        return view
    }
}
