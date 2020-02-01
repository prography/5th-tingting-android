package com.tingting.ver01.TeamInfo

import LookMyTeamInfoDetailResponse
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tingting.ver01.ApplyTeamInfo.ApplyTeamInfoActivity
import com.tingting.ver01.Model.ModelMatching
import com.tingting.ver01.Model.ModelTeam
import com.tingting.ver01.Model.Profile.LookMyTeamInfoProfileResponse
import com.tingting.ver01.Model.TeamDataCallback
import com.tingting.ver01.R
import com.tingting.ver01.SearchTeam.MakeTeamPacakge.ReviseTeam
import com.tingting.ver01.SharedPreference.App
import kotlinx.android.synthetic.main.activity_apply_team_info.back
import kotlinx.android.synthetic.main.activity_team_info.*
import kotlinx.android.synthetic.main.dialog_copy.view.*
import kotlinx.android.synthetic.main.dialog_view.view.*
import kotlinx.android.synthetic.main.dialog_view.view.dialogContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TeamInfoActivity : AppCompatActivity() {

    val model: ModelTeam = ModelTeam(this)
    val matchingModel: ModelMatching = ModelMatching(Acontext = this)

    lateinit var info: LookMyTeamInfoDetailResponse
    lateinit var Adapter: TeamInfoAdapter
    lateinit var MatchingAdapter: MatchingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_info)

        val context:Context = this
        var teamlist = arrayListOf<TeamInfoData>()
        var matchinglist = arrayListOf<MatchingData>()

        var myTeamId = intent.getIntExtra("MyTeamId", 0)
        Log.i("myTeamId", myTeamId.toString())
        var matchingTeamId: Int
        var matchingId: Int


        var intent = Intent(this, TeamInfoProfileDetailActivity::class.java);
        intent.putExtra("MyTeamId", myTeamId)

        Adapter = TeamInfoAdapter(this, teamlist) { teamInfoData ->

            startActivity(intent)
        }



        MatchingAdapter = MatchingAdapter(this, matchinglist)

        val intentapply: Intent = Intent(this, ApplyTeamInfoActivity::class.java)
        intentapply.putExtra("myTeamId", myTeamId)


        MatchingAdapter.click = object : MatchingAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                model.LookMyTeamInfo(myTeamId, object : TeamDataCallback {
                    override fun LookMyTeaminfoList(data: LookMyTeamInfoDetailResponse) {

                        matchingTeamId = data.data.teamMatchings.get(position).sendTeam.id
                        matchingId = data.data.teamMatchings.get(position).id
                        Log.i("matchingTeamId", matchingTeamId.toString())
                        intentapply.putExtra("matchingTeamId", matchingTeamId)
                        intentapply.putExtra("matchingId", matchingId)

                        startActivity(intentapply)

                    }

                })

            }
        }

        MatchingAdapter.seeChat = object : MatchingAdapter.ItemClick{
            override fun onClick(view: View, position: Int) {

                model.LookMyTeamInfo(myTeamId, object : TeamDataCallback{
                    override fun LookMyTeaminfoList(data: LookMyTeamInfoDetailResponse) {
                        if(applicationContext!=null){
                            val chatAddressDialog = AlertDialog.Builder(context)
                            val dialogView = layoutInflater.inflate(R.layout.dialog_copy, null)

                            chatAddressDialog.setView(dialogView)
                            val show = chatAddressDialog.show()
                            show.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                            dialogView.dialogContext.text = data.data.teamMatchings.get(position).sendTeam.chat_address

                            dialogView.close.setOnClickListener {
                                show.dismiss()
                            }

                            dialogView.copyURL.setOnClickListener {

                                copyText(dialogView.dialogContext.text.toString())
                                Toast.makeText(context, "주소를 복사했습니다", Toast.LENGTH_LONG).show()

                            }
                        }

                    }
                })
            }
        }

        //init screen data
        model.LookMyTeamInfopPofile(myTeamId, object : TeamDataCallback {
            override fun LookMyTeamInfoListProfile(data: LookMyTeamInfoProfileResponse) {
                model.LookMyTeamInfo(myTeamId, object : TeamDataCallback {
                    override fun LookMyTeaminfoList(data: LookMyTeamInfoDetailResponse) {
                        info = data
                        Log.i("myName", App.prefs.myname)

                        var scope = CoroutineScope(Dispatchers.Main)

                        runBlocking {
                            scope.launch {
                                //이름 값 설정
                                teamName.setText(info.data.teamInfo.name)
                                //성별 값 설정
                                if (info.data.teamInfo.gender == 0) {
                                    genderInfo.setText("남자")
                                } else {
                                    genderInfo.setText("여자")
                                }

                                //지역 설정
                                regionInfo.setText(info.data.teamInfo.place)

                                //N:N 부분 설정
                                numberInfo.setText(info.data.teamInfo.max_member_number.toString() + ":" + info.data.teamInfo.max_member_number)
                                //팀 설명 설정
                                TeamInfoExplain.setText(info.data.teamInfo.intro)

                            }
                        }

                                val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
                                runBlocking {
                                    coroutineScope.launch {
                                            if (info.data.teamMembers.get(0).name.equals(App.prefs.myname)) {
                                                App.prefs.myPersonalId = info.data.teamMembers.get(0).id.toString()
                                                Log.i("myPersonalIdSize",info.data.teamMembers.size.toString() )
                                            }
                                            for (i in info.data.teamMembers.size - 1 downTo 0) {
                                                //owner 와 팀원 ID가 같으면 팀장 아니면 팀원.
                                                if (info.data.teamInfo.owner_id == info.data.teamMembers.get(i).id) {
                                                    teamlist.add(
                                                        TeamInfoData(
                                                            info.data.teamMembers.get(i).thumbnail,
                                                            "0",
                                                            info.data.teamMembers.get(i).name
                                                        )
                                                    )

                                                } else {
                                                    teamlist.add(
                                                        TeamInfoData(
                                                            info.data.teamMembers.get(i).thumbnail,
                                                            "1",
                                                            info.data.teamMembers.get(i).name
                                                        )
                                                    )
                                                }
                                            }
                                            for (i in 0..info.data.teamMatchings.size - 1) {
                                                //이름 추가
                                                var name = info.data.teamMatchings.get(i).sendTeam.name + "/" +
                                                            info.data.teamMatchings.get(i).sendTeam.max_member_number + "/" +
                                                            info.data.teamMatchings.get(i).sendTeam.place
                                                matchinglist.add(MatchingData(info.data.teamMatchings.get(i).accepter_number.toString(), name, info.data.teamMatchings.get(i).is_matched))

                                            }
                                            noMatching(matchinglist, textNoMatching)

                                            Adapter.notifyDataSetChanged()

                                        Adapter.notifyDataSetChanged()
                                        MatchingAdapter.notifyDataSetChanged()
                                    }

                                }

                    }
                })
            }
        })

        // 처리
        val size = resources.getDimensionPixelSize(R.dimen.wide_size)

        // back button event
        back.setOnClickListener() {
            finish()
        }

        // 팀 떠나기
        TeamLeave.setOnClickListener() {
            val logoutDialog = AlertDialog.Builder(this@TeamInfoActivity)
            val dialogView = layoutInflater.inflate(R.layout.dialog_team_destruct, null)

            logoutDialog.setView(dialogView)
            val check = logoutDialog.show()

            dialogView.dialogCancel.setOnClickListener() {
                check.dismiss()
            }

            dialogView.dialogOK.setOnClickListener() {
                model.TeamLeave(info.data.teamInfo.id)
                finish()
                check.dismiss()
            }

        }


        //값 수정하기.
        EditTeamInfo.setOnClickListener() {

            val intent = Intent(this@TeamInfoActivity, ReviseTeam::class.java)
            //intent.putExtra("teamBossId", teamBossId)
            intent.putExtra("teamId", myTeamId)
            startActivity(intent)
        }

        // 팀장이면 background change하는 코드 추가.

        //apply maring to adapter.
        val deco = TeamInfoRecyclerViewMargin(size)
        teamRecyclerView1.addItemDecoration(deco)
        teamRecyclerView2.addItemDecoration(deco)

        teamRecyclerView1.adapter = Adapter
        teamRecyclerView2.adapter = MatchingAdapter

        val lm = LinearLayoutManager(
            this@TeamInfoActivity,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        val lm2 = LinearLayoutManager(
            this@TeamInfoActivity,
            LinearLayoutManager.VERTICAL,
            false
        )
        teamRecyclerView1.layoutManager = lm
        teamRecyclerView2.layoutManager = lm2
        teamRecyclerView1.setHasFixedSize(true)
        teamRecyclerView2.setHasFixedSize(true)


    }

    fun noMatching(ssize: List<Any>, view:TextView){
        if(ssize.size!=0){
            view.visibility = View.INVISIBLE
        }
    }

    fun copyText(v:String){
        copyToClipboard(v)
    }

    fun copyToClipboard(text:String){
        val clipboard:ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip:ClipData = ClipData.newPlainText("copy text", text)
        clipboard.setPrimaryClip(clip)
        Log.i("clipboard", clip.toString())
    }
}

