package com.tingting.ver01.matching.matchingTeamMemberProfileDetail


import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.tingting.ver01.BR
import com.tingting.ver01.R
import com.tingting.ver01.databinding.ActivityMatchingDetailBinding
import com.tingting.ver01.model.team.lookIndivisualTeam.IndivisualTeamResponse
import com.tingting.ver01.viewModel.TeamInfoActivityViewModel
import kotlinx.android.synthetic.main.activity_matching_detail.*


class MatchingDetail : AppCompatActivity(){

    lateinit var dataBinding : ActivityMatchingDetailBinding
    lateinit var matchingMemberPagerAdapter: TeamMemberProfilePagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_matching_detail)
        dataBinding.viewmodel = ViewModelProviders.of(this).get(TeamInfoActivityViewModel::class.java)
        var matchingId = intent.getIntExtra("MatchingTeamId",0)

        dataBinding.viewmodel?.fetchData(matchingId)

        dataBinding.backButton.bringToFront()
        dataBinding.sueBtn.bringToFront()

        dataBinding.sueBtn.setOnClickListener {
            val popup = PopupMenu(this, it)
            val inflater : MenuInflater = popup.menuInflater
            inflater.inflate(R.menu.additional_menu, popup.menu)
            popup.show()
        }


        dataBinding.backButton.setOnClickListener {
            finish()
        }

        observer()

       // setSupportActionBar(toolbar)

    }

    fun observer(){
        dataBinding.viewmodel?.teamData?.observe(this, androidx.lifecycle.Observer {
                setAdapter(it)
        })
    }

    fun setAdapter(item: IndivisualTeamResponse) {


        dataBinding.setVariable(BR.teaminfoItem,item)
        dataBinding.executePendingBindings()

        matchingMemberPagerAdapter =
                        TeamMemberProfilePagerAdapter(supportFragmentManager, item)

        matchingViewPager.adapter = matchingMemberPagerAdapter

        matchingTab.setupWithViewPager(matchingViewPager)

        matchingViewPager.currentItem = 0

        matchingMemberPagerAdapter.notifyDataSetChanged()


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

}