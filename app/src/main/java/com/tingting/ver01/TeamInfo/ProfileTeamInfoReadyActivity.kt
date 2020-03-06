package com.tingting.ver01.TeamInfo

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tingting.ver01.BR
import com.tingting.ver01.R
import com.tingting.ver01.View.Main.MainActivity
import com.tingting.ver01.databinding.ActivitiyTeaminfo2Binding
import com.tingting.ver01.model.ModelMatching
import com.tingting.ver01.model.ModelTeam
import com.tingting.ver01.model.team.lookMyTeamInfoDetail.LookMyTeamInfoDetailResponse
import com.tingting.ver01.viewModel.ProfileTeamInfoViewModel
import kotlinx.android.synthetic.main.dialog_copy.view.*

class ProfileTeamInfoReadyActivity : AppCompatActivity() {

    val model: ModelTeam = ModelTeam(this)
    val matchingModel: ModelMatching = ModelMatching(Acontext = this)
    var myTeamId : Int = 0

    lateinit var info: LookMyTeamInfoDetailResponse
    lateinit var Adapter: TeamInfoAdapter
    lateinit var MatchingAdapter: MatchingAdapter
    lateinit var dataBinding : ActivitiyTeaminfo2Binding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context:Context = this
        var teamlist = arrayListOf<TeamInfoData>()
        var matchinglist = arrayListOf<MatchingData>()

        dataBinding = DataBindingUtil.setContentView(this ,R.layout.activitiy_teaminfo2);
        dataBinding.viewmodel = ViewModelProviders.of(this).get(ProfileTeamInfoViewModel::class.java)
        myTeamId = intent.getIntExtra("MyTeamId", 0)

        dataBinding.setLifecycleOwner(this)

        dataBinding.viewmodel?.fetchInfo(myTeamId)

        setObserver()

        supportFragmentManager.beginTransaction().replace(R.id.myTeamFragment, ProfileTeaminfoMatchingStatus()).commit()

        var matchingTeamId: Int
        var matchingId: Int

        var intent = Intent(this, TeamInfoProfileDetailActivity::class.java);
        intent.putExtra("MyTeamId", myTeamId)


        Adapter = TeamInfoAdapter(this, teamlist) { teamInfoData ->
            startActivity(intent)
        }

        dataBinding.openKakaoBtn.setOnClickListener() {
            kakaoOpenChatDialog()
        }

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
        clipboard.setPrimaryClip(clip)
        Log.i("clipboard", clip.toString())
    }

    fun setImage(item :LookMyTeamInfoDetailResponse?){
        //data를 사용 할 수 있도록 variable 사용.
        dataBinding.setVariable(BR.teaminfoItem, item)
        dataBinding.executePendingBindings()

        for(i in 0..item?.data?.teamMembers?.size!!){
            when(i){
                1->MainActivity.glide.setImage(this,MainActivity.glide.DecryptUrl(item.data.teamMembers?.get(0)?.thumbnail!!), dataBinding.teamMemberImg1)
                2->MainActivity.glide.setImage(this,MainActivity.glide.DecryptUrl(item.data.teamMembers?.get(1)?.thumbnail!!), dataBinding.teamMemberImg2)
                3->MainActivity.glide.setImage(this,MainActivity.glide.DecryptUrl(item.data.teamMembers?.get(2)?.thumbnail!!), dataBinding.teamMemberImg3)
                4->MainActivity.glide.setImage(this,MainActivity.glide.DecryptUrl(item.data.teamMembers?.get(3)?.thumbnail!!), dataBinding.teamMemberImg4)
            }
        }

        when(item?.data?.teamMembers?.size){
            2->{
                dataBinding.teamMemberImg3.visibility = View.GONE
                dataBinding.teamMemberImg4.visibility = View.GONE
            }
            3-> dataBinding.teamMemberImg4.visibility = View.GONE
        }

        //set infoText
        var infoText = item.data.teamInfo.max_member_number.toString() +" : " + item.data.teamInfo.max_member_number.toString()+" | " + item.data.teamInfo.place

        dataBinding.teamInfoExplain.setText(infoText)

    }

    fun kakaoOpenChatDialog(){

        if (applicationContext != null) {
            val chatAddressDialog = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.dialog_copy, null)

            chatAddressDialog.setView(dialogView)
            val show = chatAddressDialog.show()
            show.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            dialogView.dialogContext.text =dataBinding.viewmodel?.data?.value?.data?.teamInfo?.chat_address

            dialogView.close.setOnClickListener {
                show.dismiss()
            }

            dialogView.copyURL.setOnClickListener {

                copyText(dialogView.dialogContext.text.toString())
                Toast.makeText(this, "주소를 복사했습니다", Toast.LENGTH_LONG).show()

            }
        }
    }
}



