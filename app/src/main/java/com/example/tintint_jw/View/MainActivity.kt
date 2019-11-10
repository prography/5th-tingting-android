package com.example.tintint_jw.View

import android.app.Notification
import android.app.NotificationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import com.example.tintint_jw.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //touch 구분해주기 위한 변수
        var m : Boolean = false;
        var s : Boolean = false;
        var p : Boolean = true;

        matching.setOnClickListener(){
            m=true;
            s=false;
            p=false;
            supportFragmentManager.beginTransaction().replace(R.id.mainFragment,TeamMatching()).commit()
            if(m){
                profile.setImageResource(R.drawable.user)
                profileText.setTextColor(resources.getColor(R.color.gray))

                searchTeam.setBackgroundResource(R.drawable.cupid)
                searchTeamText.setTextColor(resources.getColor(R.color.gray))

                matching.setImageResource(R.drawable.support_pink)
                matchingText.setTextColor(resources.getColor(R.color.tingtingMain))


            }
        }


        searchTeam.setOnClickListener(){
            s=true;
            m=false;
            p=false;
            supportFragmentManager.beginTransaction().replace(R.id.mainFragment,SearchTeam()).commit()
            if(s){
                profile.setImageResource(R.drawable.user)
                profileText.setTextColor(resources.getColor(R.color.gray))

                matching.setImageResource(R.drawable.support)
                matchingText.setTextColor(resources.getColor(R.color.gray))

                searchTeam.setImageResource(R.drawable.cupid_pink)
                searchTeamText.setTextColor(resources.getColor(R.color.tingtingMain))

            }
        }

        profile.setOnClickListener(){
            s=false;
            m=false;
            p=true;
            if(p){

                profile.setImageResource(R.drawable.user_pink)
                profileText.setTextColor(resources.getColor(R.color.tingtingMain))

                matching.setImageResource(R.drawable.support)
                matchingText.setTextColor(resources.getColor(R.color.gray))

                searchTeam.setImageResource(R.drawable.cupid)
                searchTeamText.setTextColor(resources.getColor(R.color.gray))
            }
        }
    }





}
