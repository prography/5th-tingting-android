package com.example.tintint_jw.SearchTeam

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tintint_jw.Model.ModelSearchTeam
import com.example.tintint_jw.Model.Team.LookTeamList.TeamResponse
import com.example.tintint_jw.Model.TeamDataCallback
import com.example.tintint_jw.ProfileTeamInfo.ProfileTeamInfoData
import com.example.tintint_jw.R
import com.example.tintint_jw.SearchTeam.MakeTeamPacakge.MTeam
import com.example.tintint_jw.SharedPreference.App
import kotlinx.android.synthetic.main.fragment_search_team.view.*
import kotlinx.android.synthetic.main.profile_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SearchTeamFragment : Fragment() {

    var searchListDataset = arrayListOf<SearchTeamData>()
    var searchList = arrayListOf<SearchTeamData>()
    var isLoading = false
    var isLastPage: Boolean = false
    var model : ModelSearchTeam = ModelSearchTeam(activity)
    var size = 0
    var nsize = 0
    lateinit var Adapter: SearchTeamAdapter
    lateinit var content : List<TeamResponse.Data.Team>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search_team, null)
        view.memberAll.isSelected = true

        Adapter = SearchTeamAdapter(activity!!.applicationContext, searchList)

        // 서버로 부터 데이터 셋이 왔을 때
        // 데이터 몇개를 불러올지, 갱신을 어떻게 할지 생각 필요.


        model.showTeamList(App.prefs.myToken.toString(), object : TeamDataCallback{

            override fun onResult(data: TeamResponse?, start: Int, end: Int) {
               Log.d("SearchTeamFragment",data.toString())
                try{
                    if(data!=null){
                    var a  = data?.data?.teamList?.size as Int
                        val scope = CoroutineScope(
                            Dispatchers.Main
                        )
                        runBlocking {
                            scope.launch {
                                for(i in 0..a - 1){
                                    content = data?.data.teamList
                                    searchListDataset.add(SearchTeamData(R.drawable.woobin1, R.drawable.jongsuk1,content.get(i).name, content.get(i).max_member_number))
                                }
                            }
                        }

                    }
                }catch (e : Exception){
                    e.printStackTrace()
                }

            }

        })

        //1명 2명 3명 선택하는 버튼
        view.segmentation_button.setTintColor(
            resources.getColor(R.color.tingtingMain),
            resources.getColor(R.color.white)
        )

        //정보를 받아야 됨, 팀 만들기 버튼
        view.createTeamBtn.setOnClickListener {

            var intent = Intent(activity, MTeam::class.java)

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


        Adapter.itemClick = object : SearchTeamAdapter.ItemClick {
            override    fun onClick(view: View, position: Int) {
                //여기서 팀 정보 보내줘야함
                Log.d("SearchTeamInfonubmer",position.toString())

                var intent = Intent(activity,SearchTeamInfo::class.java)
                intent.putExtra("teamBossId",content.get(position).id)
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

            nsize = searchListDataset.size
            Adapter.notifyItemRangeChanged(size, nsize)
            Adapter.notifyDataSetChanged()
        },1)





    }


}

