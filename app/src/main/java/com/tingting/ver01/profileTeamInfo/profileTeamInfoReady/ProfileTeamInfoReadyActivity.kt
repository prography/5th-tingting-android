package com.tingting.ver01.teamInfo

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.tingting.ver01.BR
import com.tingting.ver01.R
import com.tingting.ver01.view.Main.MainActivity
import com.tingting.ver01.databinding.ActivitiyTeaminfo2Binding
import com.tingting.ver01.model.ModelMatching
import com.tingting.ver01.model.ModelTeam
import com.tingting.ver01.model.team.lookMyTeamInfoDetail.LookMyTeamInfoDetailResponse
import com.tingting.ver01.profileTeamInfo.profileTeamInfoReady.ChatWebViewActivity
import com.tingting.ver01.profileTeamInfo.profileTeamInfoReady.ProfileTeamInfoPagerAdapter
import com.tingting.ver01.searchTeam.SearchTeamInfoDetailActivity
import com.tingting.ver01.view.Auth.FindIdAndPw.AccountPagerAdapter
import com.tingting.ver01.viewModel.ProfileTeamInfoViewModel
import kotlinx.android.synthetic.main.dialog_copy.view.*

class ProfileTeamInfoReadyActivity : AppCompatActivity() {

    val model: ModelTeam = ModelTeam(this)
    var myTeamId : Int = 0

    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    lateinit var info: LookMyTeamInfoDetailResponse
    lateinit var Adapter: TeamInfoAdapter
    lateinit var dataBinding : ActivitiyTeaminfo2Binding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var teamlist = arrayListOf<TeamInfoData>()


        dataBinding = DataBindingUtil.setContentView(this ,R.layout.activitiy_teaminfo2)

        dataBinding.viewmodel = ViewModelProviders.of(this).get(ProfileTeamInfoViewModel::class.java)
        myTeamId = intent.getIntExtra("MyTeamId", 0)

        dataBinding.lifecycleOwner = this

        dataBinding.viewmodel?.fetchInfo(myTeamId)

        setObserver()

        // supportFragmentManager.beginTransaction().replace(R.id.myTeamFragment,ProfileTeamInfoMatchingStatusFragment()).commit()


        var intent = Intent(this, SearchTeamInfoDetailActivity::class.java)
        intent.putExtra("MyTeamId", myTeamId)


        Adapter = TeamInfoAdapter(this, teamlist) { teamInfoData ->
            startActivity(intent)
        }

        dataBinding.openKakaoBtn.setOnClickListener {
            kakaoOpenChatDialog(0)
        }

        dataBinding.backBtn.setOnClickListener {
            finish()
        }

        dataBinding.kakaoChatAddress.setOnClickListener {
            kakaoOpenChatDialog(1)
        }

        //setChatAddressVisibility()

        tabLayout = dataBinding.tabLayout
        tabLayout.addTab(tabLayout.newTab().setText("매칭 현황"))
        tabLayout.addTab(tabLayout.newTab().setText("매칭 기록"))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        viewPager = dataBinding.pager
        viewPager.adapter =  ProfileTeamInfoPagerAdapter(supportFragmentManager,myTeamId)

        tabLayout.setupWithViewPager(viewPager)


        tabLayout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {
                Log.d("currentPosition22", tab!!.position.toString())
                if(tab.position!=viewPager.currentItem){
                    viewPager.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                Log.d("currentPosition", tab!!.position.toString())
                viewPager.currentItem = tab.position
            }

        })

    }

    //data를 사용하려면 observer, variable
    fun setObserver(){
        dataBinding.viewmodel?.data?.observe(this, Observer {
            setImage(it)
        }) }


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
       // clipboard.primaryClip = clip
        Log.i("clipboard", clip.toString())
    }

    fun setImage(item :LookMyTeamInfoDetailResponse?){
        //data를 사용 할 수 있도록 variable 사용.
        dataBinding.setVariable(BR.teaminfoItem, item)
        dataBinding.executePendingBindings()

        for(i in 0..item?.data?.teamMembers?.size!!){
            when(i){
                1->MainActivity.glide.setImage(this,MainActivity.glide.DecryptUrl(
                    item.data.teamMembers.get(
                        0
                    ).thumbnail
                ), dataBinding.teamMemberImg1)
                2->MainActivity.glide.setImage(this,MainActivity.glide.DecryptUrl(
                    item.data.teamMembers.get(
                        1
                    ).thumbnail
                ), dataBinding.teamMemberImg2)
                3->MainActivity.glide.setImage(this,MainActivity.glide.DecryptUrl(
                    item.data.teamMembers.get(
                        2
                    ).thumbnail
                ), dataBinding.teamMemberImg3)
                4->MainActivity.glide.setImage(this,MainActivity.glide.DecryptUrl(
                    item.data.teamMembers.get(
                        3
                    ).thumbnail
                ), dataBinding.teamMemberImg4)
            }
        }

        when(item.data.teamMembers.size){
            2->{
                dataBinding.teamMemberImg3.visibility = View.GONE
                dataBinding.teamMemberImg4.visibility = View.GONE
            }
            3-> dataBinding.teamMemberImg4.visibility = View.GONE
        }

        //set infoText
        var infoText = item.data.teamInfo.max_member_number.toString() +" : " + item.data.teamInfo.max_member_number.toString()+" | " + item.data.teamInfo.place

        dataBinding.teamInfoExplain.text = infoText

    }

    fun kakaoOpenChatDialog(i:Int){

        if (applicationContext != null) {
            val chatAddressDialog = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.dialog_copy, null)
            var otherTeamAddress :  String? =""
            chatAddressDialog.setView(dialogView)
            val show = chatAddressDialog.show()
            show.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            if(i==0){
                dialogView.dialogContext.text =dataBinding.viewmodel?.data?.value?.data?.teamInfo?.chat_address
            }else{
                if(dataBinding.viewmodel?.data?.value?.data?.teamMatchings?.isEmpty()!!){
                    dialogView.dialogContext.text = "상대 채팅방 주소가 없습니다."
                }else{
                    for(j in 0..dataBinding.viewmodel?.data?.value?.data?.teamMatchings?.size!!-1){
                        if(dataBinding.viewmodel?.data?.value?.data?.teamMatchings?.get(j)?.is_matched!!){
                            otherTeamAddress = dataBinding.viewmodel?.data?.value?.data?.teamMatchings?.get(j)?.sendTeam?.chat_address
                            dialogView.dialogContext.text = otherTeamAddress
                        }
                    }

                }
            }


            dialogView.close.setOnClickListener {
                show.dismiss()
            }

            dialogView.copyURL.setOnClickListener {

                copyText(dialogView.dialogContext.text.toString())
                Toast.makeText(this, "주소를 복사했습니다", Toast.LENGTH_LONG).show()

            }

            dialogView.partInChat.setOnClickListener{
                var intent = Intent(applicationContext, ChatWebViewActivity::class.java)
                intent.putExtra("chatUrl",otherTeamAddress)
                startActivity(intent)
            }
        }
    }


    fun setChatAddressVisibility(){

        var chekcVisible = false
        val data =dataBinding.viewmodel?.data?.value?.data?.teamMatchings
        for( i in 0..data?.size!!-1){
            if(data.get(i).is_matched){
                chekcVisible = true
                break;
            }
        }

        if(chekcVisible){
            dataBinding.kakaoChatAddress.visibility = View.VISIBLE
        }else{
            dataBinding.kakaoChatAddress.visibility = View.GONE
        }
    }





}



