package com.example.tintint_jw.Matching

import android.icu.lang.UCharacter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.ViewCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tintint_jw.R
import com.example.tintint_jw.SearchTeam.PaginationScrollListener
import kotlinx.android.synthetic.main.fragment_matching_main.*
import kotlinx.android.synthetic.main.fragment_matching_main.view.*
import kotlinx.android.synthetic.main.fragment_search_team.view.*


class MatchingFragment : Fragment() {

    val recyclerview = null
    var teamList = arrayListOf<TeamData>()
    var isLastPage: Boolean = false
    var isLoading:Boolean = false;
    lateinit var adapter : MatchingAdapter

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
        teamList.add(TeamData(R.drawable.suzy2, R.drawable.suzy1, R.drawable.seulgi, R.drawable.iu,"한국대학교 4명/11월 29일/강남","안녕하세요! 같이 맥주나 한잔 고고"))
        teamList.add(TeamData(R.drawable.suzy2, R.drawable.suzy1, R.drawable.seulgi, R.drawable.iu,"한국대학교 4명/11월 29일/강남","안녕하세요! 같이 맥주나 한잔 고고"))

        var linear : LinearLayoutManager = LinearLayoutManager(this.context)
        Log.d("linear",linear.toString())


        adapter = MatchingAdapter(activity!!.applicationContext, teamList)

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

        view.searchMatching.addOnScrollListener(object :PaginationScrollListener(LinearLayoutManager(view.context)){

            override fun isLastPage(): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun isLoading(): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                super.onScrolled(recyclerView, dx, dy)


            }

            override fun loadMoreItems() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })






        return view
    }



fun getMoreItems(){
    adapter.addData(teamList)
    isLoading = false

}




}