package com.tingting.ver01.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.tingting.ver01.Matching.MatchingFragment
import com.tingting.ver01.R
import com.tingting.ver01.SearchTeam.SearchTeamFragment
import com.tingting.ver01.TeamInfo.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //touch 구분해주기 위한 변수
        var m : Boolean = false;
        var s : Boolean = false;
        var p : Boolean = true;


        supportFragmentManager.beginTransaction().replace(R.id.mainFragment,ProfileFragment()).commit()
            
        matchingLayout.setOnClickListener(){
            m=true;
            s=false;
            p=false;
            supportFragmentManager.beginTransaction().replace(R.id.mainFragment,MatchingFragment()).commit()

            if(m){
                profile.setImageResource(R.drawable.user)
                profileText.setTextColor(resources.getColor(R.color.gray))

                searchTeam.setImageResource(R.drawable.support)
                searchTeamText.setTextColor(resources.getColor(R.color.gray))

                matching.setImageResource(R.drawable.cupid_pink)
                matchingText.setTextColor(resources.getColor(R.color.tingtingMain))


            }
        }


        searchTeamLayout.setOnClickListener(){
            s=true;
            m=false;
            p=false;
            supportFragmentManager.beginTransaction().replace(R.id.mainFragment,
                SearchTeamFragment()
            ).commit()
            if(s){
                profile.setImageResource(R.drawable.user)
                profileText.setTextColor(resources.getColor(R.color.gray))

                matching.setImageResource(R.drawable.cupid)
                matchingText.setTextColor(resources.getColor(R.color.gray))

                searchTeam.setImageResource(R.drawable.support_pink)
                searchTeamText.setTextColor(resources.getColor(R.color.tingtingMain))

            }
        }

        profileLayout.setOnClickListener(){
            s=false;
            m=false;
            p=true;
            supportFragmentManager.beginTransaction().replace(R.id.mainFragment,ProfileFragment()).commit()
            if(p){

                profile.setImageResource(R.drawable.user_pink)
                profileText.setTextColor(resources.getColor(R.color.tingtingMain))

                matching.setImageResource(R.drawable.cupid)
                matchingText.setTextColor(resources.getColor(R.color.gray))

                searchTeam.setImageResource(R.drawable.support)
                searchTeamText.setTextColor(resources.getColor(R.color.gray))
            }
        }

    }

    private var doubleBackToExitPressedOnce = false;
    override fun onBackPressed() {

        if(supportFragmentManager.backStackEntryCount > 1){
            super.onBackPressed()
        }else{


        if (doubleBackToExitPressedOnce) {
            finishAffinity()
            return
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "두번 누르면 앱이 꺼집니다. ", Toast.LENGTH_SHORT).show()
        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
        }
    }



    //shared null value setting


}
