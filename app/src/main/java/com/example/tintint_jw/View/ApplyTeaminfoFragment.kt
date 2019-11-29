package com.example.tintint_jw.View

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tintint_jw.R
import com.example.tintint_jw.TeamInfo.TeamInfoAdapter
import com.example.tintint_jw.TeamInfo.TeamInfoData
import com.example.tintint_jw.TeamInfo.TeamInfoDetailFragment
import com.example.tintint_jw.TeamInfo.TeamInfoRecyclerViewMargin
import kotlinx.android.synthetic.main.dialog_view.view.*
import kotlinx.android.synthetic.main.fragment_applying_team_info.view.*
import kotlinx.android.synthetic.main.fragment_team_info.view.*
import kotlinx.android.synthetic.main.fragment_team_info.view.back
import kotlinx.android.synthetic.main.fragment_team_info.view.teamRecyclerView

class ApplyTeaminfoFragment : Fragment() {

    var teamlist = arrayListOf<TeamInfoData>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_applying_team_info, null)
        // 처리
        val size =resources.getDimensionPixelSize(R.dimen.wide_size)

        // back button event
        view.back.setOnClickListener(){
            activity!!.supportFragmentManager.beginTransaction().remove(this).commit()
        }

        //Edit Team info button click

        // 팀장이면 background change하는 코드 추가.
        teamlist.add(TeamInfoData(R.drawable.haein, "팀장", "iu"))
        teamlist.add(TeamInfoData(R.drawable.haein, "팀원", "iu2"))


        view.acceptBtn.setOnClickListener() {
            Toast.makeText(activity,"수락 되었습니다.",Toast.LENGTH_LONG).show()
            activity!!.supportFragmentManager.beginTransaction().remove(this).commit()
            //이 아이템 데이터를 삭제 하는 쿼리 수행.
        }

        view.rejectBtn.setOnClickListener(){
            Toast.makeText(activity,"거절 되었습니다.",Toast.LENGTH_LONG).show()
            activity!!.supportFragmentManager.beginTransaction().remove(this).commit()
            //이 아이템 데이터를 삭제 하는 쿼리 수행.
        }

        val Adapter = TeamInfoAdapter(activity!!.applicationContext,teamlist){
                teamInfoData -> fragmentManager?.beginTransaction()
            ?.add(R.id.mainFragment,TeamInfoDetailFragment())?.commit()
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

