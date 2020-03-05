package com.tingting.ver01.View.Main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tingting.ver01.R
import com.tingting.ver01.SearchTeam.MakeTeamPacakge.MTeam
import com.tingting.ver01.SearchTeam.PaginationScrollListener
import com.tingting.ver01.SearchTeam.SearchTeamAdapter
import com.tingting.ver01.SearchTeam.SearchTeamData
import com.tingting.ver01.View.GlideImage
import com.tingting.ver01.databinding.FragmentSearchTeamBinding
import com.tingting.ver01.model.team.lookTeamList.TeamResponse
import com.tingting.ver01.viewModel.SearchTeamFragmentViewModel
import kotlinx.android.synthetic.main.fragment_search_team.view.*


class SearchTeamFragment : Fragment() {

    var searchListDataset = arrayListOf<SearchTeamData>()
    var isLoading = false
    var isLastPage: Boolean = false
    var size = 0
    var nsize = 0

    lateinit var searchTeamAdapter: SearchTeamAdapter
    lateinit var content : List<TeamResponse.Data.Team>
    lateinit var dataBinding : FragmentSearchTeamBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dataBinding = FragmentSearchTeamBinding.inflate(inflater, container, false).apply {
            viewmodel = ViewModelProviders.of(this@SearchTeamFragment).get(SearchTeamFragmentViewModel::class.java)

            setLifecycleOwner(viewLifecycleOwner)

        }

        return dataBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataBinding.segmentationButton.setTintColor(
            resources.getColor(R.color.tingtingMain),
            resources.getColor(R.color.white)
        )

        dataBinding.memberAll.isSelected = true

        //팀만들기 Btn
        dataBinding.createTeamBtn.setOnClickListener {
            var intent = Intent(activity, MTeam::class.java)
            startActivity(intent)
        }

        //1명 2명 3명 선택하는 버튼
        dataBinding.memberAll.setOnClickListener {
            setObserver(0)
        }

        dataBinding.member2.setOnClickListener {
            setObserver(2)
        }

        dataBinding.member3.setOnClickListener {
            setObserver(3)
        }
        dataBinding.member4.setOnClickListener {
            setObserver(4)
        }

        dataBinding.searchTeamRecyclerView?.addOnScrollListener(object :
            PaginationScrollListener(LinearLayoutManager(activity)) {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                var visibleItemCount = dataBinding.searchTeamRecyclerView.layoutManager!!.childCount
                var totalItemCount = dataBinding.searchTeamRecyclerView.layoutManager!!.itemCount
                var first: LinearLayoutManager =
                    dataBinding.searchTeamRecyclerView.layoutManager as LinearLayoutManager
                var firstPosition = first.findFirstVisibleItemPosition()


                if (!isLoading && !isLastPage) {

                    if ((visibleItemCount + firstPosition >= totalItemCount) && (firstPosition >= 0)) {
                        dataBinding.searchSwipe.setRefreshing(true)
                        loadMoreItems()
                        dataBinding.searchSwipe.setRefreshing(false)
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
                return
            }

            override fun isLastPage(): Boolean {

                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                getMoreItem()
            }

        })

        //dataSetting
        dataBinding.viewmodel?.fetchTeamInfo()

        setupSearchTeamAdapter()
        setObserver(0)

    }


    private fun setObserver(index : Int){
        dataBinding.viewmodel?.teamLiveData?.observe(viewLifecycleOwner, Observer {
            searchTeamAdapter.updateData(it,index)
        })
    }

    fun setupSearchTeamAdapter(){
        val viewModel = dataBinding.viewmodel

        if (viewModel != null) {
            searchTeamAdapter = SearchTeamAdapter(SearchTeamFragmentViewModel() , activity!!.applicationContext)
            val layoutManager = LinearLayoutManager(activity)
            dataBinding.searchTeamRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            dataBinding.searchTeamRecyclerView.addItemDecoration(DividerItemDecoration(activity,layoutManager.orientation))
            dataBinding.searchTeamRecyclerView.adapter = searchTeamAdapter
            dataBinding.searchTeamRecyclerView.setHasFixedSize(true)
        }

    }


    override fun onAttach(activity: Activity) {

        super.onAttach(activity)
    }

    override fun onDetach() {
        super.onDetach()

        }

    override fun onResume() {

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
            searchTeamAdapter.notifyItemRangeChanged(size, nsize)
            searchTeamAdapter.notifyDataSetChanged()

        },1)

    }
//
//    companion object{
//
//        @BindingAdapter("searchTeamImgFirst")
//        @JvmStatic
//        fun fristTeamMemberImage( view:ImageView?, url : String?){
//            if(view!=null && url !=null){
//                MainActivity.glide.setImage(view.context,
//                    MainActivity.glide.DecryptUrl(url),view)
//            }else{
//                Glide.with(view!!.context).load(R.drawable.nullpicture).into(view)
//            }
//        }
//
//        @BindingAdapter("searchTeamImgSecond")
//        @JvmStatic
//        fun secondTeamMemberImage( view:ImageView?, url : String?){
//            if(view!=null && url !=null){
//                MainActivity.glide.setImage(view.context,
//                    MainActivity.glide.DecryptUrl(url),view)
//            }else{
//                Glide.with(view!!.context).load(R.drawable.nullpicture).into(view)
//            }
//        }
//
//        @BindingAdapter("searchTeamImgThird")
//        @JvmStatic
//        fun thirdTeamMemberImage( view:ImageView?, url : String?){
//            if(view!=null && url !=null){
//                MainActivity.glide.setImage(view.context,
//                    MainActivity.glide.DecryptUrl(url),view)
//            }else{
//                Glide.with(view!!.context).load(R.drawable.nullpicture).into(view)
//            }
//        }
//
//        @BindingAdapter("searchTeamImgFourth")
//        @JvmStatic
//        fun fourthTeamMemberImage( view:ImageView?, url : String?){
//            if(view!=null && url !=null){
//                MainActivity.glide.setImage(view.context,
//                    MainActivity.glide.DecryptUrl(url),view)
//            }else{
//                Glide.with(view!!.context).load(R.drawable.nullpicture).into(view)
//            }
//        }
//    }

}

