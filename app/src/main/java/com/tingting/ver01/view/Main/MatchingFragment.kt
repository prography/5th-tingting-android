package com.tingting.ver01.view.Main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tingting.ver01.BR
import com.tingting.ver01.R
import com.tingting.ver01.databinding.FragmentMatchingMainBinding
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

    lateinit var matchingAdapter : MatchingAdapter
    lateinit var teamSpinner : Spinner
    lateinit var dataBinding : FragmentMatchingMainBinding

    companion object{
        var myTeamId = 0
        var myTeamPosition = 0
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        dataBinding = FragmentMatchingMainBinding.inflate(inflater,container,false).apply {
            viewmodel = ViewModelProviders.of(this@MatchingFragment).get(MatchingFragmentViewModel::class.java)
           lifecycleOwner = viewLifecycleOwner

        }

        Log.d("executioinLoad","onCreate실행 !!")
        Log.d("executioinLoad", myTeamPosition.toString())
        //init data
        return dataBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.viewmodel?.fetchdata()
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
                    loadTeamList(position)
            }

        }


        dataBinding.searchMatching.addOnScrollListener(object:PaginationScrollListener(LinearLayoutManager(view.context)){
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


                if (!isLoading && !isLastPage) {

                    if ((visibleItemCount + firstPosition >= totalItemCount) && (firstPosition >= 0)) {
                        loadMoreItems()
                    }
                }
                super.onScrolled(recyclerView, dx, dy)

            }

            override fun loadMoreItems() {

            }
        })

    }

    fun setAdapter(){
        val viewModel = dataBinding.viewmodel

        if (viewModel != null) {
            matchingAdapter = MatchingAdapter(activity!!.applicationContext, dataBinding.viewmodel!!)
            val layoutManager = LinearLayoutManager(activity)
            dataBinding.searchMatching.addItemDecoration(DividerItemDecoration(activity,layoutManager.orientation))
            searchMatching.layoutManager = layoutManager
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

        setObserver(listOptionsData.get(myTeamPosition).maxNumber)
        myTeamId = listOptionsData.get(0).teamId

        var  adapter2  = ArrayAdapter(activity,R.layout.spinner_filter_dropdown,R.id.spinnerText,listOptions)
        teamSpinner.adapter = adapter2
        teamSpinner.setSelection(myTeamPosition)
    }

    fun setObserver(number : Int){
        dataBinding.viewmodel?.data?.observe(this, Observer {
            matchingAdapter.update(it,number)
        })
    }

    fun setObserverSpinner(){
        dataBinding.viewmodel?.data?.observe(this, Observer {
            if(!it.data.myTeamList.isEmpty()){
                setSpinner(it)
            }
        })
    }

    fun loadTeamList(position :Int ){
        myTeamId = listOptionsData.get(position).teamId
        myTeamPosition = position
        setObserver(listOptionsData.get(position).maxNumber)
    }


    override fun onResume() {
        super.onResume()
        Log.d("executioinLoad","onResume 실행!!")
        Log.d("executioinLoad", myTeamPosition.toString())
        MainActivity.allowRefreshMatching=true
        MainActivity.allowRefreshSearch=false
        MainActivity.allowRefreshProfile=false
    }

    override fun onStop() {
        super.onStop()
        Log.d("executioinLoad","onStop실행 !!")
        Log.d("executioinLoad", myTeamPosition.toString())
    }

    override fun onStart() {
        super.onStart()
        Log.d("executioinLoad","onStart실행 !!")
        Log.d("executioinLoad", myTeamPosition.toString())
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.d("executioinLoad","onDestory실행 !!")
        Log.d("executioinLoad", myTeamPosition.toString())
    }

}