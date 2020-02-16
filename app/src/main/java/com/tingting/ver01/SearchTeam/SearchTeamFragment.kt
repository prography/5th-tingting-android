package com.tingting.ver01.SearchTeam

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tingting.ver01.Model.Team.LookTeamList.TeamResponse
import com.tingting.ver01.Model.Team.ModelSearchTeam
import com.tingting.ver01.Model.TeamDataCallback
import com.tingting.ver01.R
import com.tingting.ver01.SearchTeam.MakeTeamPacakge.MTeam
import com.tingting.ver01.SharedPreference.App
import com.tingting.ver01.View.MainActivity
import kotlinx.android.synthetic.main.fragment_search_team.view.*
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
    lateinit var Adapter2 : SearchTeamAdapter
    lateinit var content : List<TeamResponse.Data.Team>
    var two:ArrayList<Int> = arrayListOf<Int>()
    var three:ArrayList<Int> = arrayListOf<Int>()
    var four:ArrayList<Int> = arrayListOf<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_search_team, null)
        view.memberAll.isSelected = true

        Adapter = SearchTeamAdapter(activity!!.applicationContext, searchList)
        Adapter2 = SearchTeamAdapter(activity!!.applicationContext, searchListDataset)
        view.searchTeamRecyclerView.adapter = Adapter2

        // 서버로 부터 데이터 셋이 왔을 때
        // 데이터 몇개를 불러올지, 갱신을 어떻게 할지 생각 필요.

        model.showTeamList(App.prefs.myToken.toString(), object : TeamDataCallback{

            override fun onResult(data: TeamResponse?, start: Int, end: Int) {
                Log.d("SearchTeamFragment",data.toString())
                try{
                    if(data!=null){
                        var a  = data.data.teamList.size

                        val scope = CoroutineScope(
                            Dispatchers.Main
                        )
                        runBlocking {
                            scope.launch {
                                for(i in  0..a-1){
                                    var b = data.data.teamList.get(i).teamMembersInfo
                                    content = data?.data.teamList
                                    when (data.data.teamList.get(i).max_member_number){
                                        1 -> searchListDataset.add(SearchTeamData("",content.get(i).place, content.get(i).name, content.get(i).max_member_number,content.get(i).id))
                                        2 -> searchListDataset.add(SearchTeamData("","",content.get(i).place,content.get(i).name, content.get(i).max_member_number,content.get(i).id))
                                        3 -> searchListDataset.add(SearchTeamData("","","",content.get(i).place,content.get(i).name, content.get(i).max_member_number,content.get(i).id))
                                        4 -> searchListDataset.add(SearchTeamData("","","","",content.get(i).place,content.get(i).name, content.get(i).max_member_number,content.get(i).id))
                                    }

                                    for(j in b.size-1 downTo 0){
                                        Log.d("SearchTeamFragmentImage","사진채인지")
                                        Log.d("SearchTeamFragmentImage",b.get(j).thumbnail)

                                        searchListDataset.get(i).changedata(j, b.get(j).thumbnail)
                                    }
                                }
                                Adapter2.notifyDataSetChanged()
                                Adapter.notifyDataSetChanged()
                                //처음 데이터 셋 시키는 코드

                                for(i in 0..searchListDataset.size-1){
                                    searchList.add(searchListDataset.get(i))
                                }

                                view!!.searchTeamRecyclerView.adapter = Adapter
                                Adapter.notifyDataSetChanged()
                                Adapter2.notifyDataSetChanged()
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


        view.memberAll.setOnClickListener {
            searchList.clear()

            for(i in 0..searchListDataset.size-1){
                    searchList.add(searchListDataset.get(i))
            }

            view.searchTeamRecyclerView.adapter = Adapter
            Adapter.notifyDataSetChanged()
        }

        view.member2.setOnClickListener {
            searchList.clear()

            for(i in 0..searchListDataset.size-1){
                if(searchListDataset.get(i).count==2){
                    searchList.add(searchListDataset.get(i))
                }
            }
            view.searchTeamRecyclerView.adapter = Adapter
            Adapter.notifyDataSetChanged()
        }

        view.member3.setOnClickListener {
            searchList.clear()
            for (i in 0..searchListDataset.size - 1) {
                if (searchListDataset.get(i).count == 3) {
                    searchList.add(searchListDataset.get(i))
                }
            }
            view.searchTeamRecyclerView.adapter = Adapter
            Adapter.notifyDataSetChanged()
        }
        view.member4.setOnClickListener {
            searchList.clear()
            for (i in 0..searchListDataset.size - 1) {
                if (searchListDataset.get(i).count == 4) {
                    searchList.add(searchListDataset.get(i))
                }
            }
            view.searchTeamRecyclerView.adapter = Adapter
            Adapter.notifyDataSetChanged()
        }

        view.searchTeamRecyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        view.searchTeamRecyclerView.setHasFixedSize(true)


        Adapter.itemClick = object : SearchTeamAdapter.ItemClick {
            override    fun onClick(view: View, position: Int) {
                //여기서 팀 정보 보내줘야함
                Log.d("SearchTeamInfonubmer",position.toString())
                var intent = Intent(activity,SearchTeamInfo::class.java)
                Log.d("SearchTeamInfonubmer",content.get(position).id.toString())
                intent.putExtra("teamBossId",searchList.get(position).index)
                startActivity(intent)

            }
        }
        var intent = Intent(activity,SearchTeamInfo::class.java)

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

    override fun onAttach(activity: Activity) {

        super.onAttach(activity)
    }

    override fun onDetach() {
        super.onDetach()

        }

    override fun onResume() {
        Log.d("OnResuemSearchTeam","OnResuemSearchTeam")

        MainActivity.allowRefreshSearch=true
        MainActivity.allowRefreshMatching=false
        MainActivity.allowRefreshProfile=false
        super.onResume()
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

