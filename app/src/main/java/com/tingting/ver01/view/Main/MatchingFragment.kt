package com.tingting.ver01.view.Main

import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tingting.ver01.BR
import com.tingting.ver01.R
import com.tingting.ver01.databinding.FragmentMatchingMainBinding
import com.tingting.ver01.databinding.FragmentMatchingMainBindingImpl
import com.tingting.ver01.matching.MatchingAdapter
import com.tingting.ver01.matching.MatchingDropDownDataclass
import com.tingting.ver01.model.matching.ShowAllCandidateListResponse
import com.tingting.ver01.searchTeam.PaginationScrollListener
import com.tingting.ver01.viewModel.MatchingFragmentViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_matching_main.*
import kotlinx.android.synthetic.main.fragment_matching_main.view.*


class MatchingFragment : Fragment() {


    var isLastPage = false
    var isLoading = false
    var listOptions : ArrayList<String> = ArrayList()
    var listOptionsData : ArrayList<MatchingDropDownDataclass> = ArrayList()
    var limit = 5
    var page = 1
    var first =true
    var size = 0
    var nsize = 0


    lateinit var matchingAdapter : MatchingAdapter
    lateinit var teamSpinner : Spinner
    lateinit var dataBinding : FragmentMatchingMainBinding
    lateinit var layoutManager : LinearLayoutManager
    companion object{
        var myTeamId = 0
        var myTeamPosition = 0
        var currentTeamIndex = 0
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        dataBinding = FragmentMatchingMainBinding.inflate(inflater,container,false).apply {
            viewmodel = ViewModelProviders.of(this@MatchingFragment).get(MatchingFragmentViewModel::class.java)
           lifecycleOwner = viewLifecycleOwner

        }

        myTeamPosition = 0;


        //init data
        return dataBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.viewmodel?.fetchdata(limit, page)

        setAdapter()
        setObserverSpinner()



        dataBinding.goMeeting.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction().replace(R.id.mainFragment,
                SearchTeamFragment()).commit()

            activity!!.searchTeam.setImageResource(R.drawable.support_pink)
            activity!!.searchTeamText.setTextColor(resources.getColor(R.color.tingtingMain))
            activity!!.matching.setImageResource(R.drawable.cupid)
            activity!!.matchingText.setTextColor(resources.getColor(R.color.gray))

        }

        teamSpinner = dataBinding.filter



        teamSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    currentTeamIndex = position
                    loadTeamList(currentTeamIndex)
            }

        }




        dataBinding.searchMatching.addOnScrollListener(object:PaginationScrollListener(layoutManager){
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

            }

            override fun loadMoreItems() {
                isLoading = true

                size = matchingAdapter.itemCount

                page++

                dataBinding.viewmodel?.addData(5, page)

                nsize = matchingAdapter.itemCount

                matchingAdapter.notifyItemRangeChanged(size, nsize)
                matchingAdapter.notifyDataSetChanged()

            }

        })


        dataBinding.refreshMatchingAdapter.setOnRefreshListener {
            matchingAdapter.refresh()
            dataBinding.viewmodel?.refresh()
            isLastPage=false
            isLoading=false
            page = 1
            dataBinding.searchMatching.adapter = matchingAdapter
            dataBinding.refreshMatchingAdapter.isRefreshing = false
        }

    }

    fun setAdapter(){
        val viewModel = dataBinding.viewmodel

        if (viewModel != null) {
            matchingAdapter = MatchingAdapter(activity!!.applicationContext, dataBinding.viewmodel!!)
            layoutManager = LinearLayoutManager(activity)
            dataBinding.searchMatching.layoutManager = layoutManager
            dataBinding.searchMatching.addItemDecoration(DividerItemDecoration(activity,layoutManager.orientation))
            dataBinding.searchMatching.setHasFixedSize(true)
            dataBinding.searchMatching.setItemViewCacheSize(20)
            dataBinding.searchMatching.setRecycledViewPool(RecyclerView.RecycledViewPool())

            searchMatching.adapter = matchingAdapter


        }
    }

    fun setSpinner(item : ShowAllCandidateListResponse){

        dataBinding.setVariable(BR.matchingData, item)
        dataBinding.executePendingBindings()

        for(i in 0..item.data.myTeamList.size-1){
            Log.d("listData",item.data.myTeamList.get(i).name)
            listOptions.add(item.data.myTeamList.get(i).name+" 으로 신청하기")
            listOptionsData.add(
                MatchingDropDownDataclass(
                    item.data.myTeamList.get(i).name,
                    item.data.myTeamList.get(i).max_member_number,
                    item.data.myTeamList.get(i).id
                )
            )
        }


         listOptions.add(0,"소속 팀을 선택해주세요")


      //  addDataObserver(listOptionsData.get(myTeamPosition).maxNumber)
      //  setObserver(listOptionsData.get(myTeamPosition).maxNumber)

        myTeamId = listOptionsData.get(myTeamPosition).teamId
        val  adapter2  = ArrayAdapter(activity?.applicationContext!!,R.layout.spinner_filter_dropdown,R.id.spinnerText,listOptions)
        teamSpinner.adapter = adapter2

    }


    fun setObserver(number : Int){
        dataBinding.viewmodel?.arrayData?.observe(viewLifecycleOwner, Observer {
            Log.d("addData55","addData55")
            matchingAdapter.update(it,number)
        })
    }


    fun setObserverSpinner(){

        dataBinding.viewmodel?.data?.observe(viewLifecycleOwner, Observer {
            if(!it.data.myTeamList.isEmpty()){
                setSpinner(it)
            }
        })
    }

    fun loadTeamList(position :Int ){
        if(position!=0){
            teamText.text = listOptionsData.get(position-1).name+"으로 신청하기"
            teamText.setTextColor(ContextCompat.getColor(activity?.applicationContext!!, R.color.white))
            filter.setBackgroundResource(R.drawable.bg_spinner_selected)
            teamSelectedBackground.setBackgroundResource(R.drawable.round_edge_pink)

            myTeamId = listOptionsData.get(position-1).teamId
            myTeamPosition = position
            matchingAdapter.dataclear()

            setObserver(listOptionsData.get(position-1).maxNumber)


          //  addDataObserver(listOptionsData.get(position-1).maxNumber)
          //  matchingAdapter.addData(listOptionsData.get(position-1).maxNumber)
            matchingAdapter.notifyDataSetChanged()

        }else{
            teamText.text = "소속 팀을 선택해주세요"
            teamText.setTextColor(ContextCompat.getColor(activity?.applicationContext!!, R.color.tingtingMain))
            filter.setBackgroundResource(R.drawable.bg_spinner)
            teamSelectedBackground.setBackgroundResource(R.drawable.round_edge_pink_nofill)
        }


    }


    override fun onResume() {
        super.onResume()
        Log.d("executioinLoad","onResume 실행!!")
        Log.d("executioinLoad", myTeamPosition.toString())
        MainActivity.allowRefreshMatching=true
        MainActivity.allowRefreshSearch=false
        MainActivity.allowRefreshProfile=false
    }

}