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

class EditTeamInfoFragment : Fragment() {

    var teamlist = arrayListOf<TeamInfoData>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_applying_team_info, null)
        // 처리
        // back button event
        view.back.setOnClickListener(){
            activity!!.supportFragmentManager.beginTransaction().remove(this).commit()
        }

        //Edit Team info button click


        return view
    }
}

