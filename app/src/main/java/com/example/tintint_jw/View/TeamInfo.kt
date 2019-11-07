package com.example.tintint_jw.View

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tintint_jw.R
import com.example.tintint_jw.TeamInfo.TeamInfoAdapter
import com.example.tintint_jw.TeamInfo.TeamInfoData
import com.example.tintint_jw.TeamInfo.TeamInfoDetailFragment
import com.example.tintint_jw.TeamInfo.TeamInfoRecyclerViewMargin
import kotlinx.android.synthetic.main.dialog_team_info.view.*
import kotlinx.android.synthetic.main.fragment_team_info.*
import kotlinx.android.synthetic.main.fragment_team_info.view.*

class TeamInfo : Fragment() {

    var teamlist = arrayListOf<TeamInfoData>()
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val view = inflater.inflate(R.layout.fragment_team_info, null)
            // 처리
            val size =resources.getDimensionPixelSize(R.dimen.wide_size)

            teamlist.add(TeamInfoData(R.drawable.iu, "팀장", "iu"))
            teamlist.add(TeamInfoData(R.drawable.iu, "팀장", "iu2"))


            view.teamJoin.setOnClickListener() {

                val builder = AlertDialog.Builder(activity)
                val dialogView = layoutInflater.inflate(R.layout.dialog_team_info, null)

                val mbuilder = builder.setView(dialogView).show()

                dialogView.ok.setOnClickListener(){
                    val intent = Intent(activity!!.applicationContext,MainActivity::class.java)
                    startActivity(intent)

                }

                dialogView.cancel.setOnClickListener(){
                    mbuilder.dismiss()
                }
            }


            val Adapter = TeamInfoAdapter(activity!!.applicationContext,teamlist){
                teamInfoData -> fragmentManager?.beginTransaction()
                ?.replace(R.id.mainFragment,TeamInfoDetailFragment())?.commit()
                // 프레그 먼트로 전환
            }



            val deco = TeamInfoRecyclerViewMargin(size)
            view.teamRecyclerView.addItemDecoration(deco)
            view.teamRecyclerView.adapter = Adapter
            val lm = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)
            view.teamRecyclerView.layoutManager = lm
            view.teamRecyclerView.setHasFixedSize(true)


            return view
        }
    }

