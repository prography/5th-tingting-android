package com.example.tintint_jw.SearchTeam

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tintint_jw.R
import com.example.tintint_jw.TeamInfo.TeamInfoDetailFragment
import com.example.tintint_jw.View.CreateTeam2Fragment
import com.example.tintint_jw.View.TeamInfo
import info.hoang8f.android.segmented.SegmentedGroup
import kotlinx.android.synthetic.main.fragment_search_team.view.*

class SearchTeam : Fragment() {

    var searchListDataset = arrayListOf<SearchTeamData>()
    var searchList = arrayListOf<SearchTeamData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search_team, null)
        view.memberAll.isSelected = true
        var Adapter = SearchTeamAdapter(activity!!.applicationContext, searchList)
        // 서버로 부터 데이터 셋이 왔을 때
        // 데이터 몇개를 불러올지, 갱신을 어떻게 할지 생각 필요.
        searchListDataset.add(SearchTeamData(R.drawable.iu3, "1:1/재밌게 놀아요 ~", 1))
        searchListDataset.add(
            SearchTeamData(
                R.drawable.suzy1,
                R.drawable.naeun1,
                R.drawable.iu2,
                R.drawable.seulgi,
                "4:4/7시 홍대 환영",
                4
            )
        )
        searchListDataset.add(SearchTeamData(R.drawable.seulgi, "1:1/맥주 한잔 해요 ^.^", 1))
        searchListDataset.add(SearchTeamData(R.drawable.iu, R.drawable.seulgi, "2:2/건입 2명 !!", 2))
        searchListDataset.add(
            SearchTeamData(
                R.drawable.suzy2,
                R.drawable.naeun1,
                R.drawable.iu,
                "3:3/강남 ㄱㄱ",
                3
            )
        )
        searchListDataset.add(SearchTeamData(R.drawable.iu, R.drawable.seulgi, "2:2/건입 2명 !!", 2))
        searchListDataset.add(SearchTeamData(R.drawable.iu3, "1:1/재밌게 놀아요 ~", 1))
        searchListDataset.add(SearchTeamData(R.drawable.seulgi, "1:1/맥주 한잔 해요 ^.^", 1))
        searchListDataset.add(SearchTeamData(R.drawable.seulgi, "1:1/맥주 한잔 해요 ^.^", 1))
        searchListDataset.add(SearchTeamData(R.drawable.seulgi, "1:1/맥주 한잔 해요 ^.^", 1))

        view.segmentation_button.setTintColor(
            resources.getColor(R.color.tingtingMain),
            resources.getColor(R.color.white)
        )

        view.createTeamBtn.setOnClickListener {
            activity?.supportFragmentManager!!.beginTransaction().add(
                R.id.mainFragment,
                CreateTeam2Fragment()
            ).commit()

        }

        view.searchTeamRecyclerView.adapter =
            SearchTeamAdapter(activity!!.applicationContext, searchListDataset)

        view.memberAll.setOnClickListener {
            searchList.clear()
            view.searchTeamRecyclerView.adapter =
                SearchTeamAdapter(activity!!.applicationContext, searchListDataset)
            SearchTeamAdapter(activity!!.applicationContext, searchListDataset).itemClick =
                object : SearchTeamAdapter.ItemClick {
                    override fun onClick(view: View, position: Int) {
                        activity!!.supportFragmentManager.beginTransaction()
                            .add(R.id.mainFragment, TeamInfoDetailFragment()).commit()
                        Log.d("SearchTeam", "N명 로그")
                    }
                }
            Log.d("SearchTeam", "N명 로그")
        }

        view.member2.setOnClickListener {
            searchList.clear()
            for (i in 0..searchListDataset.size - 1) {
                if (searchListDataset.get(i).count == 2) {
                    searchList.add(searchListDataset.get(i))
                }
            }
            view.searchTeamRecyclerView.adapter = Adapter
        }

        view.member3.setOnClickListener {
            searchList.clear()
            for (i in 0..searchListDataset.size - 1) {
                if (searchListDataset.get(i).count == 3) {
                    searchList.add(searchListDataset.get(i))
                }
            }
            view.searchTeamRecyclerView.adapter = Adapter
        }
        view.member4.setOnClickListener {
            searchList.clear()
            for (i in 0..searchListDataset.size - 1) {
                if (searchListDataset.get(i).count == 4) {
                    searchList.add(searchListDataset.get(i))
                }
            }
            view.searchTeamRecyclerView.adapter = Adapter
        }

        view.searchTeamRecyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        view.searchTeamRecyclerView.setHasFixedSize(true)

        Adapter.notifyDataSetChanged()

        Adapter.itemClick = object : SearchTeamAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                activity!!.supportFragmentManager.beginTransaction()
                    .add(R.id.mainFragment, TeamInfoDetailFragment()).commit()
            }
        }
        return view
    }

}

