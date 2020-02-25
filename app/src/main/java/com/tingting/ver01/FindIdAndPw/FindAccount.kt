package com.tingting.ver01.FindIdAndPw

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.tingting.ver01.Model.ModelSignUp
import com.tingting.ver01.R
import com.tingting.ver01.View.AccountPagerAdapter
import kotlinx.android.synthetic.main.activity_find_id.*

class FindAccount : AppCompatActivity() {

    private var tabLayout: TabLayout? = null
    private var viewPager:ViewPager? = null

    var model:ModelSignUp = ModelSignUp(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_id)

        tabLayout = findViewById(R.id.tabLayout)
        tabLayout!!.addTab(tabLayout!!.newTab().setText("아이디 찾기"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("비밀번호 찾기"))
        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL

        viewPager = findViewById(R.id.pager)
        viewPager!!.adapter = AccountPagerAdapter(supportFragmentManager)

        tabLayout!!.setupWithViewPager(viewPager)
        tabLayout!!.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager!!.currentItem = tab!!.position
            }

        })

        back.setOnClickListener(){
            finish()
        }
    }
}
