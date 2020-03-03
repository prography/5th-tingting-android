package com.tingting.ver01.Matching


import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.tingting.ver01.model.Matching.ShowMatchingTeamInfoResponse
import com.tingting.ver01.model.ModelMatching
import com.tingting.ver01.model.profile.GetTeammberProfileResponse
import com.tingting.ver01.model.profile.ModelProfile
import com.tingting.ver01.model.ProfileCallBack
import com.tingting.ver01.model.TeamDataCallback
import com.tingting.ver01.R
import kotlinx.android.synthetic.main.activity_matching_detail.*
import kotlinx.android.synthetic.main.activity_matching_request.MatchingViewPager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.util.*


class MatchingDetail : AppCompatActivity(){

    val model : ModelMatching = ModelMatching(Acontext = this)
    val pModel : ModelProfile = ModelProfile(contextA = this)

    lateinit var matchingdata : ShowMatchingTeamInfoResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matching_detail)

        setSupportActionBar(toolbar)

        val adapter = TeamInfoPagerAdapter(this.supportFragmentManager)
        MatchingViewPager.adapter = adapter
        MatchingTab.setupWithViewPager(MatchingViewPager)


        var matchingId = intent.getIntExtra("MatchingTeamId",0)
        var myTeamId = intent.getIntExtra("MyTeamId", 0)
        //init screen

        model.lookMatchingTeam(matchingId, myTeamId, object : TeamDataCallback{
            override fun LookMatchingTeamInfo(data: ShowMatchingTeamInfoResponse) {
                runBlocking {
                    for(i in 0..data.data.teamInfo.max_member_number-1){
                        pModel.getTeammemberInfo(
                            data!!.data.teamMembers.get(i).id,
                            object : ProfileCallBack {
                                override fun onTeammemberProfileSuccess(data: GetTeammberProfileResponse) {
                                    var scope = CoroutineScope(Dispatchers.Main)

                                    adapter.addItem(
                                        FirstPagerFragment(
                                            data.data.userInfo.thumbnail,
                                            data.data.userInfo.name,
                                            calAge(data.data.userInfo.birth),
                                            data.data.userInfo.height.toString(),
                                            data.data.userInfo.schoolName
                                        )
                                    )
                                    //Log.d("viewPager추가",calAge(data.data.userInfo.birth).toString())
                                    adapter.notifyDataSetChanged()
                                }
                            })
                    }
                    adapter.notifyDataSetChanged()

                }

            }
        })


        backButtonMatching.setOnClickListener {
            finish()
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val menuInflater:MenuInflater = menuInflater
        menuInflater.inflate(R.menu.additional_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            //R.id.block->
            R.id.report -> {
                val reportDialog = AlertDialog.Builder(this)
                val dialogView = layoutInflater.inflate(R.layout.dialog_report, null)

                reportDialog.setView(dialogView)
                reportDialog.show()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    fun calAge(age : String) : String{
        var year = Calendar.getInstance().get(Calendar.YEAR)
        var b = age.substring(0,4);
        var peopleAge = year - b.toInt()+1

        return peopleAge.toString()
    }
}