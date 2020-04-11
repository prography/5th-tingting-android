package com.tingting.ver01.profileTeamInfo.profileTeamInfoNotReady

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.tingting.ver01.R
import com.tingting.ver01.databinding.ActivityProfileNotReadyBinding
import com.tingting.ver01.model.ModelTeam
import com.tingting.ver01.profileTeamInfo.profileTeamInfoReady.ChatWebViewActivity
import com.tingting.ver01.teamInfo.ProfileTeamInfoNotReadyRecyclerViewAdapter
import com.tingting.ver01.teamInfo.TeamInfoRecyclerViewMargin
import com.tingting.ver01.viewModel.ProfileTeamInfoViewModel
import kotlinx.android.synthetic.main.activity_profile_not_ready.*
import kotlinx.android.synthetic.main.dialog_team_destruct.view.*

class ProfileTeamInfoNotReadyActivity : AppCompatActivity() {

    lateinit var dataBinding : ActivityProfileNotReadyBinding
    lateinit var notReadyAdapter : ProfileTeamInfoNotReadyRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_not_ready)

        var myTeamId = intent.getIntExtra("MyTeamId", 0)

        dataBinding = DataBindingUtil.setContentView(this,R.layout.activity_profile_not_ready)
        dataBinding.viewmodel = ViewModelProviders.of(this).get(ProfileTeamInfoViewModel::class.java)

        dataBinding.lifecycleOwner = this

        dataBinding.backBtn.setOnClickListener {
            finish()
        }

        dataBinding.copyBtn.setOnClickListener {
            copyText(dataBinding.kakaoUrl.text.toString())
            Toast.makeText(applicationContext,"주소가 복사되었습니다.",Toast.LENGTH_LONG).show()
        }

        dataBinding.teamLeave.setOnClickListener {

            var builder = AlertDialog.Builder(this)
            var view = layoutInflater.inflate(R.layout.dialog_team_destruct,null,false)

            var mbuilder = builder.setView(view).show()

            view.dialogOK.setOnClickListener {

                ModelTeam.getInstance().teamLeave(myTeamId) { isSuccess: Boolean, response: Int? ->
                    when(response){
                        200-> {
                            mbuilder.dismiss()
                            finish()
                            Toast.makeText(this,"팀 나가기에 성공하였습니다. ",Toast.LENGTH_LONG).show()
                        }
                        400 -> Toast.makeText(this,"이미 매칭 된 팀, 나가기 불가",Toast.LENGTH_LONG).show()
                        403 ->Toast.makeText(this,"나가고자 하는 팀에 속해있지 않음",Toast.LENGTH_LONG).show()
                        500 ->Toast.makeText(this,"팀 삭제 오류",Toast.LENGTH_LONG).show()
                    }
                }

            }

            view.dialogCancel.setOnClickListener {
                mbuilder.dismiss()
            }

        }

        dataBinding.participateChatRoom.setOnClickListener(){
            var intent = Intent(applicationContext, ChatWebViewActivity::class.java)
            intent.putExtra("chatUrl",dataBinding.kakaoUrl.text.toString())
            startActivity(intent)
        }

        //data를 넣어주기 위해서!!!

        dataBinding.viewmodel?.fetchInfo(myTeamId)

        setAdapter()
        setObserver()
    }

    fun setObserver(){
        dataBinding.viewmodel?.data?.observe(this, Observer {
            notReadyAdapter.updateData(it)
        })
    }


    fun setAdapter(){
        var data = dataBinding.viewmodel


        if(data!=null){
            val deco = TeamInfoRecyclerViewMargin(10)
            teamMemberRecyclerView.addItemDecoration(deco)

            notReadyAdapter = ProfileTeamInfoNotReadyRecyclerViewAdapter(dataBinding.viewmodel!!,this)
            val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            teamMemberRecyclerView.layoutManager = layoutManager
            teamMemberRecyclerView.adapter = notReadyAdapter
        }
    }

    fun copyText(v:String){
        copyToClipboard(v)
    }

    fun copyToClipboard(text:String){
        val clipboard: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip: ClipData = ClipData.newPlainText("copy text", text)
      //  clipboard.primaryClip = clip
        Log.i("clipboard", clip.toString())
    }


}
