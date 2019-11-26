package com.example.tintint_jw.SearchTeam

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tintint_jw.R
import com.example.tintint_jw.View.CreateTeam2Fragment
import kotlinx.android.synthetic.main.fragment_search_team.view.*

class SearchTeam : Fragment() {

    var searchList = arrayListOf<SearchTeamData>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search_team, null)
        view.memberAll.isSelected = true

        // 임시 데이터
        searchList.add(SearchTeamData(R.drawable.iu3, "1:1/재밌게 놀아요 ~"))
        searchList.add(SearchTeamData(R.drawable.suzy1, R.drawable.naeun1, R.drawable.iu2, R.drawable.seulgi, "4:4/7시 홍대 환영"))
        searchList.add(SearchTeamData(R.drawable.seulgi,"1:1/맥주 한잔 해요 ^.^"))
        searchList.add(SearchTeamData(R.drawable.iu, R.drawable.seulgi, "2:2/건입 2명 !!"))
        searchList.add(SearchTeamData(R.drawable.suzy2, R.drawable.naeun1, R.drawable.iu, "3:3/강남 ㄱㄱ"))

        view.createTeamBtn.setOnClickListener{
            activity?.supportFragmentManager!!.beginTransaction().replace(R.id.mainFragment,
                CreateTeam2Fragment()
            ).commit()
        }
        view.memberAll.setOnClickListener{
            //Toast.makeText(context,"clicked all",Toast.LENGTH_SHORT).show()
            view.searchTeamRecyclerView.adapter = SearchTeamAdapter(activity!!.applicationContext, searchList)
            view.searchTeamRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
            view.searchTeamRecyclerView.setHasFixedSize(true)
        }

        view.member2.setOnClickListener{
            view.searchTeamRecyclerView.adapter = SearchTeamAdapter(activity!!.applicationContext, searchList)
            view.searchTeamRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
            view.searchTeamRecyclerView.setHasFixedSize(true)
        }

        view.member3.setOnClickListener{
            view.searchTeamRecyclerView.adapter = SearchTeamAdapter(activity!!.applicationContext, searchList)
            view.searchTeamRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
            view.searchTeamRecyclerView.setHasFixedSize(true)
        }
        view.member4.setOnClickListener{
            view.searchTeamRecyclerView.adapter = SearchTeamAdapter(activity!!.applicationContext, searchList)
            view.searchTeamRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
            view.searchTeamRecyclerView.setHasFixedSize(true)
        }
        return view
    }
}

