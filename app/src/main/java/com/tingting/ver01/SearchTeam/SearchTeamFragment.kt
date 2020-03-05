package com.tingting.ver01.SearchTeam

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.shapes.OvalShape
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alimuzaffar.lib.pin.PinEntryEditText
import com.tingting.ver01.Model.CodeCallBack
import com.tingting.ver01.Model.ModelTeam
import com.tingting.ver01.Model.Team.LookTeamList.TeamResponse
import com.tingting.ver01.Model.Team.ModelSearchTeam
import com.tingting.ver01.Model.TeamDataCallback
import com.tingting.ver01.R
import com.tingting.ver01.SearchTeam.MakeTeamPacakge.MTeam
import com.tingting.ver01.SharedPreference.App
import com.tingting.ver01.View.MainActivity
import kotlinx.android.synthetic.main.dialog_team_password.view.*
import kotlinx.android.synthetic.main.fragment_search_team.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig
import uk.co.deanwild.materialshowcaseview.ShowcaseTooltip


class SearchTeamFragment : Fragment() {

    var searchListDataset = arrayListOf<SearchTeamData>()
    var searchList = arrayListOf<SearchTeamData>()
    var isLoading = false
    var isLastPage: Boolean = false
    var model : ModelSearchTeam = ModelSearchTeam(activity)

    var size = 0
    var nsize = 0
    lateinit var searchTeamByMem:LinearLayout
    lateinit var makeTeam:Button
    lateinit var reset:Button
    lateinit var Adapter: SearchTeamAdapter
    lateinit var Adapter2 : SearchTeamAdapter
    lateinit var content : List<TeamResponse.Data.Team>
    var two:ArrayList<Int> = arrayListOf<Int>()
    var three:ArrayList<Int> = arrayListOf<Int>()
    var four:ArrayList<Int> = arrayListOf<Int>()
    val SHOWCASE_ID:String = "tooltip_searchTeam"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_search_team, null)
        view.memberAll.isSelected = true

        Adapter = SearchTeamAdapter(activity!!.applicationContext, searchList)
        Adapter2 = SearchTeamAdapter(activity!!.applicationContext, searchListDataset)
        view.searchTeamRecyclerView.adapter = Adapter2

        searchTeamByMem = view.findViewById(R.id.searchTeambyMem)
        makeTeam = view.findViewById(R.id.createTeamBtn)
        reset = view.findViewById(R.id.reset)
        // 서버로 부터 데이터 셋이 왔을 때
        // 데이터 몇개를 불러올지, 갱신을 어떻게 할지 생각 필요.

        model.showTeamList(App.prefs.myToken.toString(), object : TeamDataCallback{

            override fun onResult(data: TeamResponse?, start: Int, end: Int) {
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
                                        2 -> searchListDataset.add(SearchTeamData("","",content.get(i).place,content.get(i).name, content.get(i).max_member_number,content.get(i).id,content.get(i).hasPassword))
                                        3 -> searchListDataset.add(SearchTeamData("","","",content.get(i).place,content.get(i).name, content.get(i).max_member_number,content.get(i).id,content.get(i).hasPassword))
                                        4 -> searchListDataset.add(SearchTeamData("","","","",content.get(i).place,content.get(i).name, content.get(i).max_member_number,content.get(i).id,content.get(i).hasPassword))
                                    }

                                    for(j in b.size-1 downTo 0){


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
            override fun onClick(view: View, position: Int) {
                // 여기서 팀 정보 보내줘야함
                // 팀 비밀번호 없는 경우
                var modelTeam : ModelTeam = ModelTeam(activity!!)
                if(!searchList.get(position).hasPassword){
                    var intent = Intent(activity,SearchTeamInfo::class.java)

                    intent.putExtra("teamBossId",searchList.get(position).index)
                    startActivity(intent)
                }
                // 팀 비밀번호 있는 경우
                else{
                    val entryDialog = AlertDialog.Builder(activity)
                    var dialogView = layoutInflater.inflate(R.layout.dialog_team_password, null)

                    entryDialog.setView(dialogView)

                    val entry = entryDialog.show()
                    val pinEntry:PinEntryEditText = dialogView.pin_entry
                    if(pinEntry!=null){
                        pinEntry.setOnPinEnteredListener(object :PinEntryEditText.OnPinEnteredListener{
                            override fun onPinEntered(str: CharSequence?) {
                                modelTeam.JoinTeam(App.prefs.myToken.toString(), searchList.get(position).index, pinEntry.text.toString(), object :CodeCallBack{
                                    override fun onSuccess(code: String, value: String) {
                                        if(code.equals("201")){
                                            Toast.makeText(activity!!.applicationContext, "합류에 성공했습니다.", Toast.LENGTH_LONG).show()
                                        }else if(code.equals("403")){
                                            Toast.makeText(activity!!.applicationContext, "비밀번호가 틀렸습니다.", Toast.LENGTH_LONG).show()

                                        }else if(code.equals("404")){
                                            Toast.makeText(activity!!.applicationContext, "합류할 수 있는 팀이 존재하지 않습니다.", Toast.LENGTH_LONG).show()

                                        }else if(code.equals("500")){
                                            Toast.makeText(activity!!.applicationContext, "합류에 실패하였습니다.", Toast.LENGTH_LONG).show()

                                        }else{
                                            Toast.makeText(activity!!.applicationContext, "일시적인 서버 오류입니다.", Toast.LENGTH_LONG).show()

                                        }
                                    }
                                })
                            }

                        })
                    }

                    dialogView.dialogCancel.setOnClickListener {
                        entry.cancel()
                    }
                }
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
        presentShowcaseView()
        /*reset.setOnClickListener {
            MaterialShowcaseView.resetSingleUse(activity, SHOWCASE_ID)

        }*/

        return view
    }

    override fun onAttach(activity: Activity) {

        super.onAttach(activity)
    }

    override fun onDetach() {
        super.onDetach()

        }

    fun presentShowcaseView(){
        val showcaseConfig:ShowcaseConfig = ShowcaseConfig()
        showcaseConfig.delay = 1000

        val sequence:MaterialShowcaseSequence = MaterialShowcaseSequence(activity, SHOWCASE_ID)
        var tooltip:ShowcaseTooltip = ShowcaseTooltip.build(activity)
            .corner(30)
            .textColor(Color.parseColor("#4E4E4E"))
            .text("팀원을 찾고 있는 <b>팀에 합류</b>하세요.")

        sequence.addSequenceItem(
            MaterialShowcaseView.Builder(activity)
                .setTarget(searchTeamByMem)
                .setToolTip(tooltip)
                .withRectangleShape()
                .setTooltipMargin(30)
                .setShapePadding(10)
                .setDismissOnTouch(true)
                .setMaskColour(resources.getColor(R.color.tooltip_mask))
                .build()
        )

        val tooltip2:ShowcaseTooltip = ShowcaseTooltip.build(activity)
            .corner(10)
            .textColor(Color.parseColor("#4E4E4E"))
            .text("마음에 드는 팀이 없다면 팀을 만들어보세요.")

        sequence.addSequenceItem(
            MaterialShowcaseView.Builder(activity)
                .setTarget(makeTeam)
                .setShape(object : uk.co.deanwild.materialshowcaseview.shape.OvalShape(){
                    override fun setAdjustToTarget(adjustToTarget: Boolean) {
                    }
                })
                .setToolTip(tooltip2)
                .setTooltipMargin(30)
                .setShapePadding(10)
                .setDismissOnTouch(true)
                .setMaskColour(resources.getColor(R.color.tooltip_mask))
                .build()
        )
        sequence.start()

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
            Adapter.notifyItemRangeChanged(size, nsize)
            Adapter.notifyDataSetChanged()

        },1)

    }

}

