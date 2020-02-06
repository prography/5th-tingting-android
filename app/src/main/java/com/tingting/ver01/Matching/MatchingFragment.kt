package com.tingting.ver01.Matching

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tingting.ver01.Model.Matching.ShowAllCandidateListResponse
import com.tingting.ver01.Model.ModelMatching
import com.tingting.ver01.Model.TeamDataCallback
import com.tingting.ver01.R
import com.tingting.ver01.SearchTeam.PaginationScrollListener
import com.tingting.ver01.SharedPreference.App
import com.tingting.ver01.View.MainActivity
import kotlinx.android.synthetic.main.fragment_matching_main.*
import kotlinx.android.synthetic.main.fragment_matching_main.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import android.widget.AdapterView.OnItemSelectedListener




class MatchingFragment : Fragment(), AdapterView.OnItemSelectedListener {
    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        loadTeamList(position)
    }

    val model : ModelMatching = ModelMatching(activity)
    val recyclerview = null
    var teamList = arrayListOf<TeamData>()
    var isLastPage = false
    var isLoading = false
    var myTeamId:Int = 0
    var currTeam:String = ""
    var isFirstSelected = true
    var myTeamNumber =0
    var listOptions : ArrayList<String> = ArrayList()
    lateinit var myTeam : List<ShowAllCandidateListResponse.Data.MyTeam>
    lateinit var matchingTeam: List<ShowAllCandidateListResponse.Data.Matching>

    lateinit var teamSpinner : Spinner
    lateinit var adapter : MatchingAdapter
    lateinit var spinnerAdapter:FilterAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_matching_main, null)


        adapter = activity?.let { MatchingAdapter(it, teamList as MutableList<TeamData>) }!!



        model.lookTeamList(App.prefs.myToken.toString(), currTeam, object : TeamDataCallback{
            override fun showAllCandidateList(data: ShowAllCandidateListResponse?) {
                matchingTeam = data!!.data.matchingList
                myTeam = data!!.data.myTeamList

                try {

                    teamList.clear()

                    myTeamNumber = myTeam.get(0).max_member_number
                    myTeamId = myTeam.get(0).id


                    val scope = CoroutineScope(Dispatchers.Main)
                    for(i in 0..myTeam.size-1) {
                        listOptions.add(myTeam.get(i).name)
                    }
                    /*Array<String>(myTeam.size,{i ->""})
                    */

                    runBlocking {
                        scope.launch {
                            for (i in 0..matchingTeam.size - 1){
                                if(matchingTeam.get(i).max_member_number==1 && myTeamNumber==1){
                                    teamList.add(TeamData(matchingTeam.get(i).membersInfo.get(0).thumbnail, matchingTeam.get(i).place, matchingTeam.get(i).name,matchingTeam.get(i).max_member_number,matchingTeam.get(i).id))

                                }else if (matchingTeam.get(i).max_member_number ==2&& myTeamNumber==2 ){
                                    teamList.add(TeamData(matchingTeam.get(i).membersInfo.get(0).thumbnail,matchingTeam.get(i).membersInfo.get(1).thumbnail, matchingTeam.get(i).place, matchingTeam.get(i).name,matchingTeam.get(i).max_member_number,matchingTeam.get(i).id))

                                }else if(matchingTeam.get(i).max_member_number==3 && myTeamNumber==3){
                                    teamList.add(TeamData(matchingTeam.get(i).membersInfo.get(0).thumbnail,matchingTeam.get(i).membersInfo.get(1).thumbnail,matchingTeam.get(i).membersInfo.get(2).thumbnail, matchingTeam.get(i).place, matchingTeam.get(i).name,matchingTeam.get(i).max_member_number,matchingTeam.get(i).id))

                                }else if (matchingTeam.get(i).max_member_number==4 && myTeamNumber==4){
                                    teamList.add(TeamData(matchingTeam.get(i).membersInfo.get(0).thumbnail,matchingTeam.get(i).membersInfo.get(1).thumbnail,matchingTeam.get(i).membersInfo.get(2).thumbnail,matchingTeam.get(i).membersInfo.get(3).thumbnail, matchingTeam.get(i).place, matchingTeam.get(i).name,matchingTeam.get(i).max_member_number,matchingTeam.get(i).id))
                                }

                            }
                            if(myTeam.size == 0) {
                                currentTeam.text = "소속된 팀이 없습니다."
                                currentTeamsub.visibility = View.GONE

                            }else{
                                currentTeam.text = myTeam.get(0).name
                            }

                        }

                    }
                    adapter.notifyDataSetChanged()

                }  catch (e : Exception){

                }
            }

        })
       // loadTeamList(0)

        //init data


           var adapter2  = activity?.applicationContext?.let { ArrayAdapter(it,R.layout.spinner_filter_dropdown,R.id.spinnerText,listOptions) } as SpinnerAdapter
        //  var adapter2 = activity?.applicationContext?.let{FilterAdapter(it,listOptions)}

        teamSpinner = view.filter
        spinnerAdapter = FilterAdapter(activity!!.applicationContext, listOptions)
        teamSpinner.adapter = adapter2
        teamSpinner!!.setOnItemSelectedListener(this)


       /* teamSpinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d("Spinner","noting")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Log.d("Spinnnnnnenenenne",position.toString())
            }
        }
*/

        adapter.notifyDataSetChanged()

        adapter.matchingclick = object  : MatchingAdapter.MatchingClick{

            override fun Onclick(view: View, position: Int) {
                //val intent = Intent(activity, com.example.tintint_jw.Matching.MatchingDetail::class.java)
                val intent = Intent(activity, MatchingApplyTeamInfo::class.java)
                Log.d("TeamIdCheck",teamList.get(position).teamID.toString())
                intent.putExtra("MatchingTeamId", teamList.get(position).teamID)
                intent.putExtra("MyTeamId", myTeamId)

                activity!!.startActivity(intent)

                //activity!!.supportFragmentManager.beginTransaction().addToBackStack(null).replace(R.id.mainFragment,MatchingDetail()).commit()
            }
        }

        view.searchMatching.adapter = adapter

        val layoutManager = LinearLayoutManager(this.context)
        view.searchMatching.layoutManager = layoutManager
        view.searchMatching.setHasFixedSize(true)



        // 스피너 아이템 이벤트

        /*teamSpinner.setOnItemClickListener(object: AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>?, p1: View?, position: Int, p3: Long) {

                currTeam = parent!!.getItemAtPosition(position).toString()
                Log.i("currentTeam", currTeam)

                if(isFirstSelected){
                    isFirstSelected = false
                    loadTeamList()
                    Log.i("loadTeamList","1")
                }

                currentTeam.setText(currTeam)

            }
        })*/



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

    fun loadTeamList(index :Int ){

        teamList.clear()
        listOptions.clear()

        model.lookTeamList(App.prefs.myToken.toString(), currTeam, object : TeamDataCallback{
            override fun showAllCandidateList(data: ShowAllCandidateListResponse?) {
                matchingTeam = data!!.data.matchingList
                myTeam = data!!.data.myTeamList

                try {

                    teamList.clear()

                    myTeamNumber = myTeam.get(index).max_member_number
                    myTeamId = myTeam.get(index).id

                    val scope = CoroutineScope(Dispatchers.Main)
                    for(i in 0..myTeam.size-1) {
                        listOptions.add(myTeam.get(i).name)
                    }
                    /*Array<String>(myTeam.size,{i ->""})
                    */

                    runBlocking {
                        scope.launch {
                            for (i in 0..matchingTeam.size - 1){

                                  if(matchingTeam.get(i).max_member_number==1 && myTeamNumber==1){
                                      teamList.add(TeamData(matchingTeam.get(i).membersInfo.get(0).thumbnail, matchingTeam.get(i).place, matchingTeam.get(i).name,matchingTeam.get(i).max_member_number,matchingTeam.get(i).id))

                                  }else if (matchingTeam.get(i).max_member_number ==2&& myTeamNumber==2 ){
                                      teamList.add(TeamData(matchingTeam.get(i).membersInfo.get(0).thumbnail,matchingTeam.get(i).membersInfo.get(1).thumbnail, matchingTeam.get(i).place, matchingTeam.get(i).name,matchingTeam.get(i).max_member_number,matchingTeam.get(i).id))

                                  }else if(matchingTeam.get(i).max_member_number==3 && myTeamNumber==3){
                                       teamList.add(TeamData(matchingTeam.get(i).membersInfo.get(0).thumbnail,matchingTeam.get(i).membersInfo.get(1).thumbnail,matchingTeam.get(i).membersInfo.get(2).thumbnail, matchingTeam.get(i).place, matchingTeam.get(i).name,matchingTeam.get(i).max_member_number,matchingTeam.get(i).id))

                                  }else if (matchingTeam.get(i).max_member_number==4 && myTeamNumber==4){
                                      teamList.add(TeamData(matchingTeam.get(i).membersInfo.get(0).thumbnail,matchingTeam.get(i).membersInfo.get(1).thumbnail,matchingTeam.get(i).membersInfo.get(2).thumbnail,matchingTeam.get(i).membersInfo.get(3).thumbnail, matchingTeam.get(i).place, matchingTeam.get(i).name,matchingTeam.get(i).max_member_number,matchingTeam.get(i).id))
                                  }

                            }
                            if(myTeam.size == 0) {
                                currentTeam.text = "소속된 팀이 없습니다."
                                currentTeamsub.visibility = View.GONE

                            }else{
                                currentTeam.text = myTeam.get(index).name
                            }

                            Log.d("myTeamNumber1", index.toString())
                            Log.d("myTeamNumber1", myTeamNumber.toString())
                            Log.d("myTeamNumber2", myTeam.get(index).id.toString())
                            Log.d("myTeamNumber3", myTeam.get(index).name.toString())

                        }

                    }
                    adapter.notifyDataSetChanged()

                    // 팀 스피너

                }  catch (e : Exception){

                }

            }

        })

    }

    override fun onResume() {
        super.onResume()
        Log.d("OnResuemMatching","OnResuemMatching")
        MainActivity.allowRefreshMatching=true
        MainActivity.allowRefreshSearch=false
        MainActivity.allowRefreshProfile=false
    }



}