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
import com.kakao.kakaolink.v2.KakaoLinkResponse
import com.kakao.kakaolink.v2.KakaoLinkService
import com.kakao.message.template.ButtonObject
import com.kakao.message.template.ContentObject
import com.kakao.message.template.FeedTemplate
import com.kakao.message.template.LinkObject
import com.kakao.network.ErrorResult
import com.kakao.network.callback.ResponseCallback
import com.kakao.util.helper.log.Logger
import com.tingting.ver01.R
import com.tingting.ver01.databinding.FragmentSearchTeamBinding
import com.tingting.ver01.model.team.lookTeamList.TeamResponse
import com.tingting.ver01.searchTeam.MakeTeamPacakge.MTeam
import com.tingting.ver01.searchTeam.PaginationScrollListener
import com.tingting.ver01.searchTeam.SearchTeamAdapter
import com.tingting.ver01.viewModel.SearchTeamFragmentViewModel


class SearchTeamFragment : Fragment() {

    var isLoading = false
    var isLastPage = false
    var size = 0
    var nsize = 0
    var page = 1
    var checkMoreData = true;
    var currentTeamNumber = 0
    var first = true

    lateinit var searchTeamAdapter: SearchTeamAdapter
    lateinit var content : List<TeamResponse.Data.Team>
    lateinit var dataBinding : FragmentSearchTeamBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        checkMoreData = true
        dataBinding = FragmentSearchTeamBinding.inflate(inflater, container, false).apply {
            viewmodel = ViewModelProviders.of(this@SearchTeamFragment).get(SearchTeamFragmentViewModel::class.java)

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

        //팀만들기 Btn
        dataBinding.createTeamBtn.setOnClickListener {
            var intent = Intent(activity, MTeam::class.java)
            startActivity(intent)
        }

        //1명 2명 3명 선택하는 버튼
        dataBinding.memberAll.setOnClickListener {
            currentTeamNumber=0
            setObserver(currentTeamNumber)
        }

        dataBinding.member2.setOnClickListener {
            currentTeamNumber=2
            setObserver(currentTeamNumber)
        }

        dataBinding.member3.setOnClickListener {
            currentTeamNumber = 3
            setObserver(currentTeamNumber)
        }
        dataBinding.member4.setOnClickListener {
            currentTeamNumber = 4
            setObserver(currentTeamNumber)
        }

        //친구 초대하기
        dataBinding.inviteFriend.setOnClickListener {

            shareKakaoLink()
        }

        dataBinding.searchRecyclerViewRefresh.setOnRefreshListener {
            page = 1
            dataBinding.viewmodel?.refresh()

            setObserver(currentTeamNumber)
            checkMoreData = true
            isLastPage=false
            isLoading=false
            //refreh하게 되면 adapter 설정도 다시 해주어야함!
            dataBinding.searchTeamRecyclerView.adapter = searchTeamAdapter
            dataBinding.searchRecyclerViewRefresh.isRefreshing=false

        }



        dataBinding.searchTeamRecyclerView?.addOnScrollListener(object :
            PaginationScrollListener(LinearLayoutManager(activity)) {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                val visibleItemCount = dataBinding.searchTeamRecyclerView.layoutManager!!.childCount
                val totalItemCount = dataBinding.searchTeamRecyclerView.layoutManager!!.itemCount
                val first: LinearLayoutManager =
                    dataBinding.searchTeamRecyclerView.layoutManager as LinearLayoutManager
                val firstPosition = first.findFirstVisibleItemPosition()


                if (!isLoading && !isLastPage) {

                    if ((visibleItemCount + firstPosition >= totalItemCount) && (firstPosition >= 0)) {

                        loadMoreItems()

                    }
                }
                super.onScrolled(recyclerView, dx, dy)
                return
            }

            override fun isLastPage(): Boolean {
                Toast.makeText(activity?.applicationContext,"마지막 페이지 입니다",Toast.LENGTH_SHORT).show()
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                Log.d("loadInfoTest33","loadinfoTest")
                adddata()

            }

        })

        //dataSetting
        dataBinding.viewmodel?.fetchTeamInfo(5, page)

        setupSearchTeamAdapter()
        setObserver(0)

    }

    //chagne observer가 따로 필요함..!

    private fun setObserver(index : Int){
        dataBinding.viewmodel?.teamLiveData?.observe(viewLifecycleOwner, Observer {
            searchTeamAdapter?.updateData(it,index)
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

            dataBinding.searchTeamRecyclerView.setItemViewCacheSize(20)
            dataBinding.searchTeamRecyclerView.setRecycledViewPool(RecyclerView.RecycledViewPool())

            searchTeamAdapter.hasStableIds()
        }

    }


    override fun onResume() {
        super.onResume()
        MainActivity.allowRefreshSearch=true
        MainActivity.allowRefreshMatching=false
        MainActivity.allowRefreshProfile=false

    }


    private fun adddata() {
        isLoading = false
        size = searchTeamAdapter.itemCount

        page++
        if(first){
            Log.d("loadInfoTest44","loading")
            dataBinding.viewmodel?.addTeamInfo(5, page)
            first = false
        }
        first = true
        nsize = searchTeamAdapter.itemCount

        searchTeamAdapter.notifyItemRangeChanged(size, nsize)
        searchTeamAdapter.notifyDataSetChanged()
        searchTeamAdapter.noti()
    }

   private fun shareKakaoLink(){

        var showLink = FeedTemplate.newBuilder(
            ContentObject.newBuilder(
                "팅팅 다운로드하기!",
                "https://tingting-logo.s3.ap-northeast-2.amazonaws.com/tingting.png"
                ,
                LinkObject.newBuilder()
                    .setAndroidExecutionParams("https://play.google.com/store/apps/details?id=com.tingting.ver01")
                    .setIosExecutionParams("https://apps.apple.com/us/app/%ED%8C%85%ED%8C%85-%EB%8C%80%ED%95%99%EC%83%9D%EC%9D%84-%EC%9C%84%ED%95%9C-%EA%B2%80%EC%A6%9D%EB%90%9C-%EB%AF%B8%ED%8C%85-%EC%95%B1/id1493700519")
                    .setWebUrl("https://play.google.com/store/apps/details?id=com.tingting.ver01")
                    .setMobileWebUrl("https://play.google.com/store/apps/details?id=com.tingting.ver01").build()
            )
                .setImageHeight(200).setImageWidth(200)
                .setDescrption("친구가 초대했습니다.").build()
        ).addButton(
            ButtonObject(
                "다운로드하기", LinkObject.newBuilder()
                    .setAndroidExecutionParams("key1=value1")
                    .setIosExecutionParams("key1=value1")
                    .setWebUrl("https://play.google.com/store/apps/details?id=com.tingting.ver01")
                    .setMobileWebUrl("https://play.google.com/store/apps/details?id=com.tingting.ver01").build()
            )
        ).build()

        var serverCallbackArgs = HashMap<String, String>()

        KakaoLinkService.getInstance().sendDefault(
            activity,
            showLink,
            serverCallbackArgs,
            object : ResponseCallback<KakaoLinkResponse?>() {
                override fun onFailure(errorResult: ErrorResult) {
                    Logger.e(errorResult.toString())

                }

                override fun onSuccess(result: KakaoLinkResponse?) { // 템플릿 밸리데이션과 쿼터 체크가 성공적으로 끝남. 톡에서 정상적으로 보내졌는지 보장은 할 수 없다. 전송 성공 유무는 서버콜백 기능을 이용하여야 한다.

                }
            })
    }

}

