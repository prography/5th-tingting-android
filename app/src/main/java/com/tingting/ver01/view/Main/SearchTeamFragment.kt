package com.tingting.ver01.view.Main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kakao.sdk.link.LinkClient
import com.kakao.sdk.template.Button
import com.kakao.sdk.template.Content
import com.kakao.sdk.template.FeedTemplate
import com.kakao.sdk.template.Link
import com.tingting.ver01.R
import com.tingting.ver01.databinding.FragmentSearchTeamBinding
import com.tingting.ver01.model.team.lookTeamList.TeamResponse
import com.tingting.ver01.searchTeam.MakeTeamPacakge.MTeam
import com.tingting.ver01.searchTeam.PaginationScrollListener
import com.tingting.ver01.searchTeam.SearchTeamAdapter
import com.tingting.ver01.viewModel.SearchTeamFragmentViewModel
import java.nio.file.attribute.AclEntry.newBuilder


class SearchTeamFragment : Fragment() {


    var size = 0
    var nsize = 0
    var page = 1
    var checkMoreData = true;

    var first = true
    lateinit var layoutManager: LinearLayoutManager
    lateinit var searchTeamAdapter: SearchTeamAdapter
    lateinit var content: List<TeamResponse.Data.Team>
    lateinit var dataBinding: FragmentSearchTeamBinding

    companion object{
        var isLoading = false
        var isLastPage = false
        var currentTeamNumber = 0
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        checkMoreData = true
        dataBinding = FragmentSearchTeamBinding.inflate(inflater, container, false).apply {
            viewmodel = ViewModelProviders.of(this@SearchTeamFragment)
                .get(SearchTeamFragmentViewModel::class.java)

            lifecycleOwner = viewLifecycleOwner

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

        //어뎁터를 새로 만들기 -->

        //팀만들기 Btn
        dataBinding.createTeamBtn.setOnClickListener {
            var intent = Intent(activity, MTeam::class.java)
            startActivity(intent)
        }

        //1명 2명 3명 선택하는 버튼
        dataBinding.memberAll.setOnClickListener {
            currentTeamNumber = 0
            searchTeamAdapter.classifyNum(currentTeamNumber)
            searchTeamAdapter.notifyDataSetChanged()
        }

        dataBinding.member2.setOnClickListener {
            currentTeamNumber = 2
            searchTeamAdapter.classifyNum(currentTeamNumber)
        }

        dataBinding.member3.setOnClickListener {
            currentTeamNumber = 3
            searchTeamAdapter.classifyNum(currentTeamNumber)

        }
        dataBinding.member4.setOnClickListener {
            currentTeamNumber = 4
            searchTeamAdapter.classifyNum(currentTeamNumber)

        }

        //친구 초대하기
        dataBinding.inviteFriend.setOnClickListener {

           shareKakaoLink()
        }

        dataBinding.searchRecyclerViewRefresh.setOnRefreshListener {
            page = 1
            searchTeamAdapter.refresh()
            dataBinding.viewmodel?.refresh()

            setObserver(currentTeamNumber)

            checkMoreData = true
            isLastPage = false
            isLoading = false
            first = true

            //refreh하게 되면 adapter 설정도 다시 해주어야함!
            dataBinding.searchTeamRecyclerView.adapter = searchTeamAdapter
            dataBinding.searchRecyclerViewRefresh.isRefreshing = false

        }


        dataBinding.viewmodel?.fetchTeamInfo(8, page)
        layoutManager = LinearLayoutManager(activity)

        setupSearchTeamAdapter()
        setObserver(0)

       searchTeamAdapter.notifyDataSetChanged()

        dataBinding.searchTeamRecyclerView?.addOnScrollListener(object :
            PaginationScrollListener(layoutManager) {

            override fun isLastPage(): Boolean {

                return isLastPage
            }

            override fun isLoading(): Boolean {

                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true

                  adddata()
            }

        })

        //dataSetting


    }

    //chagne observer가 따로 필요함..!

    private fun setObserver(index: Int) {
        dataBinding.viewmodel?.teamLiveData?.observe(viewLifecycleOwner, Observer {
            searchTeamAdapter?.updateData(it, index)
        })
    }



    fun setupSearchTeamAdapter() {
        val viewModel = dataBinding.viewmodel

        if (viewModel != null) {
            searchTeamAdapter =
                SearchTeamAdapter(SearchTeamFragmentViewModel(), activity!!.applicationContext)
            dataBinding.searchTeamRecyclerView.layoutManager = layoutManager
            dataBinding.searchTeamRecyclerView.addItemDecoration(
                DividerItemDecoration(
                    activity,
                    layoutManager.orientation
                )
            )

            dataBinding.searchTeamRecyclerView.adapter = searchTeamAdapter

            dataBinding.searchTeamRecyclerView.setHasFixedSize(true)
            dataBinding.searchTeamRecyclerView.setItemViewCacheSize(0)
            dataBinding.searchTeamRecyclerView.setRecycledViewPool(RecyclerView.RecycledViewPool())
            searchTeamAdapter.hasStableIds()

        }
        searchTeamAdapter.notifyDataSetChanged()

    }


    override fun onResume() {
        super.onResume()
        MainActivity.allowRefreshSearch = true
        MainActivity.allowRefreshMatching = false
        MainActivity.allowRefreshProfile = false

    }


    private fun adddata() {
        // isLoading = false
        size = searchTeamAdapter.itemCount

        page++

        dataBinding.viewmodel?.addTeamInfo(5, page)

        nsize = searchTeamAdapter.itemCount



    }

    private fun shareKakaoLink() {

        var showLink = FeedTemplate(
            content = Content(
                "팅팅 다운로드하기!",
                "https://tingting-logo.s3.ap-northeast-2.amazonaws.com/tingting.png"
                ,
                link = Link(
                    webUrl ="https://play.google.com/store/apps/details?id=com.tingting.ver01",
                    mobileWebUrl = "https://play.google.com/store/apps/details?id=com.tingting.ver01"
                )

            ),
            buttons = listOf(
                Button(
                    "다운로드하기",

                    link = Link(
                        webUrl ="https://play.google.com/store/apps/details?id=com.tingting.ver01",
                        mobileWebUrl = "https://play.google.com/store/apps/details?id=com.tingting.ver01"
                    )
                )
            )

        )

        LinkClient.instance.defaultTemplate(requireContext(), showLink) { linkResult, error ->
            if (error != null) {
                Log.e("kakaoLinkTestFail", "카카오링크 보내기 실패", error)
            }
            else if (linkResult != null) {
                Log.d("kakaoLinkTestSuccess", "카카오링크 보내기 성공 ${linkResult.intent}")
                startActivity(linkResult.intent)
            }
        }

    }

}

