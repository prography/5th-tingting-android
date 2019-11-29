package com.example.tintint_jw.Matching

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tintint_jw.R
import kotlinx.android.synthetic.main.fragment_matching_main.view.*


class MatchingFragment : Fragment() {

    val recyclerview = null
    var teamList = arrayListOf<TeamData>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_matching_main, null)
        // 처리
        /*view.next.setOnClickListener(){

            activity!!.supportFragmentManager.beginTransaction().add(R.id.mainFragment,MatchingRequest()).commit()
        }*/

        // 임시 데이터
        teamList.add(TeamData(R.drawable.suzy2, R.drawable.suzy1, R.drawable.seulgi, R.drawable.iu,"한국대학교 4명/11월 29일/강남","안녕하세요! 같이 맥주나 한잔 고고"))
        teamList.add(TeamData(R.drawable.haein, "연기대학교 1명/12월 1일/신촌","신촌에서 잠깐 놀 사람?"))

        val adapter = MatchingAdapter(activity!!.applicationContext, teamList)
        adapter.notifyDataSetChanged()
        view.searchMatching.adapter = adapter

        val layoutManager = LinearLayoutManager(this.context)
        view.searchMatching.layoutManager = layoutManager
        view.searchMatching.setHasFixedSize(true)

        return view
    }

    private fun onItemClick(view: View) {
        activity!!.supportFragmentManager.beginTransaction().add(R.id.mainFragment,MatchingRequest()).commit()
    }
}