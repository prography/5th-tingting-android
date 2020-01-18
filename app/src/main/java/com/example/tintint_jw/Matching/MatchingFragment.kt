package com.example.tintint_jw.Matching

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tintint_jw.Model.Matching.ShowAllCandidateListResponse
import com.example.tintint_jw.Model.ModelMatching
import com.example.tintint_jw.Model.TeamDataCallback
import com.example.tintint_jw.ProfileTeamInfo.ProfileTeamInfoData
import com.example.tintint_jw.R
import com.example.tintint_jw.SearchTeam.PaginationScrollListener
import com.example.tintint_jw.SharedPreference.App
import kotlinx.android.synthetic.main.fragment_matching_main.*
import kotlinx.android.synthetic.main.fragment_matching_main.view.*
import kotlinx.android.synthetic.main.fragment_search_team.*
import kotlinx.android.synthetic.main.fragment_search_team.view.*
import kotlinx.android.synthetic.main.profile_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class MatchingFragment : Fragment() {

    val model : ModelMatching = ModelMatching(activity)
    val recyclerview = null
    var teamList = arrayListOf<TeamData>()
    var isLastPage = false
    var isLoading = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_matching_main, null)

        val adapter = MatchingAdapter(activity!!.applicationContext, teamList)

        //init data



        model.lookTeamList(App.prefs.myToken.toString(),5, object : TeamDataCallback{
            override fun showAllCandidateList(data: ShowAllCandidateListResponse?) {
                val d = data!!.data.matchingList

              if(d.size!=0){
                  try {

                      val scope = CoroutineScope(Dispatchers.Main)

                      runBlocking {
                          scope.launch {
                              for (i in 0..d.size - 1) {
                                  teamList.add(TeamData(1, "서울", d.get(0).name))
                              }
                          }
                      }
                      adapter.notifyDataSetChanged()
                  }  catch (e : Exception){

                  }
              }
            }

        })





        // 필터
        view.addFilter.setOnClickListener(){
            val intent = Intent(activity, FilterActivity::class.java)
            activity!!.startActivity(intent)
        }



        adapter.notifyDataSetChanged()

        adapter.matchingclick = object  : MatchingAdapter.MatchingClick{

            override fun Onclick(view: View, position: Int) {

                val intent = Intent(activity, com.example.tintint_jw.Matching.MatchingDetail::class.java)
                activity!!.startActivity(intent)

                //activity!!.supportFragmentManager.beginTransaction().addToBackStack(null).replace(R.id.mainFragment,MatchingDetail()).commit()
            }
        }





        view.searchMatching.adapter = adapter

        val layoutManager = LinearLayoutManager(this.context)
        view.searchMatching.layoutManager = layoutManager
        view.searchMatching.setHasFixedSize(true)

        // 팀 스피너
        var spinner = view.filter

        val listOptions = arrayOf("불금불금", "귀요미드드들", "마셔마셔")


        val spinnerAdapter:FilterAdapter = FilterAdapter(context!!, listOptions)
        spinner?.adapter = spinnerAdapter

        // 스피너 아이템 이벤트
        spinner?.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener{

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                currentTeam.setText(parent!!.getItemAtPosition(position).toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }

        })

        view.searchMatching.addOnScrollListener(object:PaginationScrollListener(LinearLayoutManager(view.context)){
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                var visibleItemCount = view.searchMatching.layoutManager!!.childCount
                var totalItemCount = view.searchMatching.layoutManager!!.itemCount
                var first : LinearLayoutManager = view.searchMatching.layoutManager as LinearLayoutManager
                var firstPosition = first.findFirstVisibleItemPosition()

                Log.d("visibleItemCount",visibleItemCount.toString())
                Log.d("totalItemCount",totalItemCount.toString())
                Log.d("firstPosition",firstPosition.toString())

                if (!isLoading && !isLastPage) {

                    if ((visibleItemCount + firstPosition >= totalItemCount) && (firstPosition >= 0)) {
                        loadMoreItems()
                    }
                }
                super.onScrolled(recyclerView, dx, dy)

            }

            override fun loadMoreItems() {

            }
        }

        )


        view.matchingSwipe.setOnRefreshListener{

        var update = Runnable {
            //데이터 셋을 불러옴
            Toast.makeText(view.context,"데이터를 불러왔습니다.", Toast.LENGTH_LONG).show()
        }
            matchingSwipe.isRefreshing = false
        }


        return view
    }

}