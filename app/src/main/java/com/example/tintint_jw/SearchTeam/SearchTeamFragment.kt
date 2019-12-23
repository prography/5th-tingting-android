package com.example.tintint_jw.SearchTeam

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tintint_jw.MakeTeam.MakeTeam
import com.example.tintint_jw.R
import com.example.tintint_jw.TeamInfo.TeamInfoDetailActivity
import kotlinx.android.synthetic.main.fragment_search_team.view.*

class SearchTeamFragment : Fragment() {

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
        searchListDataset.add(
            SearchTeamData(
                R.drawable.seungho,
                R.drawable.jongsuk1,
                R.drawable.woobin1,
                R.drawable.gray_fill,
                "4:4/7시 홍대 환영",
                4
            )
        )
        searchListDataset.add(SearchTeamData(R.drawable.woobin1, R.drawable.jongsuk1, R.drawable.gray_fill, "3:3/달리즈아", 3))
        searchListDataset.add(SearchTeamData(R.drawable.seungho, R.drawable.gray_fill, "2:2/건입 2명 !!", 2))
        searchListDataset.add(
            SearchTeamData(
                R.drawable.jongsuk1,
                R.drawable.gyunsang,
                R.drawable.gray_fill,
                "3:3/강남 ㄱㄱ",
                3
            )
        )
        searchListDataset.add(SearchTeamData(R.drawable.woobin1, R.drawable.jongsuk1, R.drawable.gray_fill, R.drawable.gray_fill,"4:4/건입거닙!!", 4))
        searchListDataset.add(SearchTeamData(R.drawable.gyunsang, R.drawable.gray_fill, "2:2/재밌게 놀아요 ~", 2))
        searchListDataset.add(SearchTeamData(R.drawable.seungho, R.drawable.gray_fill, R.drawable.gray_fill,"3:3/강남이면 컴컴", 3))
        searchListDataset.add(SearchTeamData(R.drawable.jongsuk1, R.drawable.gray_fill,"2:2/홍대 근처 사시는 분", 2))
        searchListDataset.add(SearchTeamData(R.drawable.gyunsang, R.drawable.gray_fill, "2:2/소주 잘먹", 2))
        searchListDataset.add(SearchTeamData(R.drawable.seungho, R.drawable.gray_fill, R.drawable.gray_fill,"3:3/심심해여", 3))

        view.segmentation_button.setTintColor(
            resources.getColor(R.color.tingtingMain),
            resources.getColor(R.color.white)
        )

        //정보를 받아야 됨
        view.createTeamBtn.setOnClickListener {

            var intent = Intent(activity,MakeTeam::class.java)

           startActivity(intent)

        }

        view.searchTeamRecyclerView.adapter =
            SearchTeamAdapter(activity!!.applicationContext, searchListDataset)

        view.memberAll.setOnClickListener {
            searchList.clear()

            for(i in 0..searchListDataset.size-1){
                searchList.add(searchListDataset.get(i))
            }

            view.searchTeamRecyclerView.adapter = Adapter
        }

        view.member2.setOnClickListener {
            searchList.clear()
            for(i in 0..searchListDataset.size-1){
                if(searchListDataset.get(i).count==2){
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

        var intent = Intent(activity,TeamInfoDetailActivity::class.java)

        Adapter.itemClick = object : SearchTeamAdapter.ItemClick {
            override    fun onClick(view: View, position: Int) {
                startActivity(intent)
            }
        }
        return view
    }

}

