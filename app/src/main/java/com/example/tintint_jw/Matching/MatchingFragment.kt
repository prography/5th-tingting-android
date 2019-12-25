package com.example.tintint_jw.Matching

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tintint_jw.R
import kotlinx.android.synthetic.main.fragment_matching_main.*
import kotlinx.android.synthetic.main.fragment_matching_main.view.*


class MatchingFragment : Fragment() {

    val recyclerview = null
    var spinner : Spinner ?= null
    var teamList = arrayListOf<TeamData>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_matching_main, null)
        // 처리
        /*view.next.setOnClickListener(){

            activity!!.supportFragmentManager.beginTransaction().add(R.id.mainFragment,MatchingRequest()).commit()
        }*/

        // 임시 데이터
        /*teamList.add(TeamData(R.drawable.suzy2, R.drawable.suzy1, R.drawable.seulgi, R.drawable.iu,"한국대학교 4명/11월 29일/강남","안녕하세요! 같이 맥주나 한잔 고고"))
        teamList.add(TeamData(R.drawable.haein, "연기대학교 1명/12월 1일/신촌","신촌에서 잠깐 놀 사람?"))
        teamList.add(TeamData(R.drawable.suzy2, R.drawable.suzy1, R.drawable.seulgi, R.drawable.iu,"한국대학교 4명/11월 29일/강남","안녕하세요! 같이 맥주나 한잔 고고"))
        teamList.add(TeamData(R.drawable.suzy2, R.drawable.suzy1, R.drawable.seulgi, R.drawable.iu,"한국대학교 4명/11월 29일/강남","안녕하세요! 같이 맥주나 한잔 고고"))
        teamList.add(TeamData(R.drawable.suzy2, R.drawable.suzy1, R.drawable.seulgi, R.drawable.iu,"한국대학교 4명/11월 29일/강남","안녕하세요! 같이 맥주나 한잔 고고"))
        teamList.add(TeamData(R.drawable.suzy2, R.drawable.suzy1, R.drawable.seulgi, R.drawable.iu,"한국대학교 4명/11월 29일/강남","안녕하세요! 같이 맥주나 한잔 고고"))
        teamList.add(TeamData(R.drawable.suzy2, R.drawable.suzy1, R.drawable.seulgi, R.drawable.iu,"한국대학교 4명/11월 29일/강남","안녕하세요! 같이 맥주나 한잔 고고"))
        teamList.add(TeamData(R.drawable.suzy2, R.drawable.suzy1, R.drawable.seulgi, R.drawable.iu,"한국대학교 4명/11월 29일/강남","안녕하세요! 같이 맥주나 한잔 고고"))
        teamList.add(TeamData(R.drawable.suzy2, R.drawable.suzy1, R.drawable.seulgi, R.drawable.iu,"한국대학교 4명/11월 29일/강남","안녕하세요! 같이 맥주나 한잔 고고"))
*/

        teamList.add(
            TeamData(
                R.drawable.iu3,
                R.drawable.suzy2,
                "놀사람",
                "#강남 #8시 #칵테일 #좋아")
        )

        teamList.add(
            TeamData(
                R.drawable.naeun1,
                R.drawable.iu,
                R.drawable.seulgi,
                "3명컴",
                "#3:3 #홍대 #같이 #놀자 #심심해")
        )

        teamList.add(
            TeamData(R.drawable.suzy1,
                R.drawable.seulgi,
                R.drawable.iu,
                R.drawable.naeun1,
                "마셔마셔",
                "#홍대 #환영 #초저녁")
        )

        teamList.add(
            TeamData(R.drawable.naeun1,
                R.drawable.iu2,
                R.drawable.seulgi,
                "신촌고고",
                "#신촌 #7시 #소맥")
        )

        teamList.add(
            TeamData(R.drawable.seulgi,
                R.drawable.iu2,
                R.drawable.naeun1,
                R.drawable.suzy2,
                "건입거닙",
                "#건입 #아무때나")
        )
        val adapter = MatchingAdapter(activity!!.applicationContext, teamList)

        adapter.notifyDataSetChanged()

        adapter.matchingclick = object  : MatchingAdapter.MatchingClick{
            override fun Onclick(view: View, position: Int) {
                activity!!.supportFragmentManager.beginTransaction().addToBackStack(null).replace(R.id.mainFragment,MatchingDetail()).commit()
            }
        }


        view.searchMatching.adapter = adapter

        val layoutManager = LinearLayoutManager(this.context)
        view.searchMatching.layoutManager = layoutManager
        view.searchMatching.setHasFixedSize(true)

        // 팀 스피너
        //spinner = view.filter

        //val listOptions = arrayOf("체고체고", "귀요미드드들", "마셔마셔")
        /*val spinnerAdapter:FilterAdapter = FilterAdapter(context!!, listOptions)
        spinner?.adapter = spinnerAdapter*/

        // 스피너 아이템 이벤트
        spinner?.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener{

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                currentTeam.setText(parent!!.getItemAtPosition(position).toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }

        })

        return view
    }

}