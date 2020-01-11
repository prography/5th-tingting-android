package com.example.tintint_jw.SearchTeam

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tintint_jw.MakeTeam.MakeTeam
import com.example.tintint_jw.R
import com.example.tintint_jw.TeamInfo.TeamInfoDetailActivity
import kotlinx.android.synthetic.main.fragment_search_team.view.*

class SearchTeamFragment : Fragment() {

    var searchListDataset = arrayListOf<SearchTeamData>()
    var searchList = arrayListOf<SearchTeamData>()
    var isLoading = false
    var isLastPage: Boolean = false
    private lateinit var mHandler: Handler
    private lateinit var mRunnable:Runnable
    var size = 0
    var nsize = 0
    lateinit var Adapter: SearchTeamAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search_team, null)
        view.memberAll.isSelected = true

        Adapter = SearchTeamAdapter(activity!!.applicationContext, searchList)
        mHandler = Handler()

        // 서버로 부터 데이터 셋이 왔을 때
        // 데이터 몇개를 불러올지, 갱신을 어떻게 할지 생각 필요.
        searchListDataset.add(
            SearchTeamData(
                R.drawable.seungho,
                R.drawable.jongsuk1,
                R.drawable.woobin1,
                R.drawable.gray_fill,
                "7시 홍대 환영",
                4
            )
        )


        searchListDataset.add(SearchTeamData(R.drawable.woobin1, R.drawable.jongsuk1, R.drawable.gray_fill, "달리즈아", 3))
        searchListDataset.add(SearchTeamData(R.drawable.seungho, R.drawable.gray_fill, "건입 2명 !!", 2))
        searchListDataset.add(
            SearchTeamData(
                R.drawable.jongsuk1,
                R.drawable.gyunsang,
                R.drawable.gray_fill,
                "강남 ㄱㄱ",
                3
            )
        )
        searchListDataset.add(SearchTeamData(R.drawable.woobin1, R.drawable.jongsuk1, R.drawable.gray_fill, R.drawable.gray_fill,"건입거닙!!", 4))
        searchListDataset.add(SearchTeamData(R.drawable.gyunsang, R.drawable.gray_fill, "재밌게 놀아요 ~", 2))
        searchListDataset.add(SearchTeamData(R.drawable.seungho, R.drawable.gray_fill, R.drawable.gray_fill,"강남이면 컴컴", 3))
        searchListDataset.add(SearchTeamData(R.drawable.jongsuk1, R.drawable.gray_fill,"홍대 근처 사시는 분", 2))
        searchListDataset.add(SearchTeamData(R.drawable.gyunsang, R.drawable.gray_fill, "소주 잘먹", 2))
        searchListDataset.add(SearchTeamData(R.drawable.seungho, R.drawable.gray_fill, R.drawable.gray_fill,"심심해여", 3))




        //1명 2명 3명 선택하는 버튼
        view.segmentation_button.setTintColor(
            resources.getColor(R.color.tingtingMain),
            resources.getColor(R.color.white)
        )

        //정보를 받아야 됨, 팀 만들기 버튼
        view.createTeamBtn.setOnClickListener {

            var intent = Intent(activity,
                MakeTeam::class.java)

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


        //새로고침 기능

         view.searchSwipe.setOnRefreshListener {
             var swipe = Runnable {
              //데이터 불러오는 코드
                 Toast.makeText(view.context,"데이터를 불러왔습니다.",Toast.LENGTH_LONG).show()
             }
                  view.searchSwipe.isRefreshing = false

         }

        // loading 함수 기능.
      view.searchTeamRecyclerView?.addOnScrollListener(object : PaginationScrollListener(LinearLayoutManager(view.context)){

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                var visibleItemCount = view.searchTeamRecyclerView.layoutManager!!.childCount
                var totalItemCount = view.searchTeamRecyclerView.layoutManager!!.itemCount
                var first : LinearLayoutManager = view.searchTeamRecyclerView.layoutManager as LinearLayoutManager
                var firstPosition = first.findFirstVisibleItemPosition()

                Log.d("visibleItemCount",visibleItemCount.toString())
                Log.d("totalItemCount",totalItemCount.toString())
                Log.d("firstPosition",firstPosition.toString())
                if (!isLoading && !isLastPage) {

                    if ((visibleItemCount + firstPosition >= totalItemCount) && (firstPosition >= 0)) {
                        view.searchSwipe.setRefreshing(true)
                        loadMoreItems()
                        view.searchSwipe.setRefreshing(false)
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
                return
            }

            override fun isLastPage(): Boolean {

                return isLastPage
            }

            override fun isLoading() : Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                getMoreItem()
            }

        })

        return view
    }

fun getMoreItem(){
    isLoading = false
    adddata()
}

    fun adddata(){
        size = searchListDataset.size

        view!!.searchTeamRecyclerView?.postDelayed(Runnable {

            searchListDataset.add(SearchTeamData( R.drawable.gray_fill, R.drawable.gray_fill,"새로 추가 된 데이터", 2))
            searchListDataset.add(SearchTeamData( R.drawable.gray_fill, R.drawable.gray_fill,"새로 추가 된 데이터", 2))
            searchListDataset.add(SearchTeamData( R.drawable.gray_fill, R.drawable.gray_fill,"새로 추가 된 데이터", 2))
            searchListDataset.add(SearchTeamData( R.drawable.gray_fill, R.drawable.gray_fill,"새로 추가 된 데이터", 2))
            searchListDataset.add(SearchTeamData( R.drawable.gray_fill, R.drawable.gray_fill,"새로 추가 된 데이터", 2))
            Log.d("data","adddata실행됨")

            nsize = searchListDataset.size
            Adapter.notifyItemRangeChanged(size, nsize)
            Adapter.notifyDataSetChanged()
        },1)


       /* val job = GlobalScope.launch(Dispatchers.Main) {


        }*/



    }


}

