package com.tingting.ver01.view.Main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tingting.ver01.R
import com.tingting.ver01.socket.SocketListener
import com.tingting.ver01.view.GlideImage
import com.tingting.ver01.teamInfo.ProfileFragment
import com.tingting.ver01.view.Auth.LoginActivity
import com.tingting.ver01.view.Main.MainActivity.Companion.msocket
import com.tingting.ver01.viewModel.ProfileFragmentViewModel
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URISyntaxException


class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    companion object{
    var allowRefreshProfile = false
    var allowRefreshSearch = true
    var allowRefreshMatching = false
     var gender = -1

     var glide = GlideImage()

     var msocket = IO.socket("http://13.209.77.221");


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //touch 구분해주기 위한 변수


        var m  = false
        var s  = true
        var p  = false

        allowRefreshProfile = false

        socketConnect()

        var index = intent.getIntExtra("notifiCode",99)

        if(index ==99){
            allowRefreshProfile = true
            allowRefreshMatching = false
            allowRefreshSearch = false
            supportFragmentManager.beginTransaction().replace(R.id.mainFragment, ProfileFragment()).commit()
        }else{
            supportFragmentManager.beginTransaction().replace(R.id.mainFragment, SearchTeamFragment()).commit()
        }



        matchingLayout.setOnClickListener {
            m=true
            s=false
            p=false

            supportFragmentManager.beginTransaction().replace(R.id.mainFragment,
                MatchingFragment()
            ).commit()

            if(m){
                 colorMatchingIcon()
            }
        }


        searchTeamLayout.setOnClickListener {
            s=true
            m=false
            p=false
            supportFragmentManager.beginTransaction().replace(R.id.mainFragment,
                SearchTeamFragment()
            ).commit()
            if(s){
           colorSearchIcon()

            }
        }

        profileLayout.setOnClickListener {
            s=false
            m=false
            p=true
            supportFragmentManager.beginTransaction().replace(R.id.mainFragment,ProfileFragment()).commit()
            if(p){

            colorProfileIcon()
            }
        }

    }

    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {

        if(supportFragmentManager.backStackEntryCount > 1){
            super.onBackPressed()
        }else{


        if (doubleBackToExitPressedOnce) {
            finishAffinity()
            return
        }
        this.doubleBackToExitPressedOnce = true
            Toast.makeText(this, "두번 누르면 앱이 꺼집니다. ", Toast.LENGTH_SHORT).show()
        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
        }
    }



    override fun onResume() {
        super.onResume()
        Log.d("OnResuemMain","OnResuemMain")
        Log.d("OnResuemMain", allowRefreshProfile.toString())

        Log.d("OnResuemMain", allowRefreshSearch.toString())
        Log.d("OnResuemMain", allowRefreshMatching.toString())


        if(allowRefreshProfile){
            colorProfileIcon()
            supportFragmentManager.beginTransaction().replace(R.id.mainFragment, ProfileFragment()).commit()
            allowRefreshProfile = false
         }
        if(allowRefreshSearch){
            colorSearchIcon()
            supportFragmentManager.beginTransaction().replace(R.id.mainFragment, SearchTeamFragment()).commit()
            allowRefreshSearch = false
        }

        if(allowRefreshMatching){
            colorMatchingIcon()
            supportFragmentManager.beginTransaction().replace(R.id.mainFragment, MatchingFragment()).commit()
            allowRefreshMatching = false
        }

    }

    fun colorSearchIcon(){
        profile.setImageResource(R.drawable.user)
        profileText.setTextColor(resources.getColor(R.color.gray))

        matching.setImageResource(R.drawable.cupid)
        matchingText.setTextColor(resources.getColor(R.color.gray))

        searchTeam.setImageResource(R.drawable.support_pink)
        searchTeamText.setTextColor(resources.getColor(R.color.tingtingMain))
    }

    fun colorMatchingIcon(){
        profile.setImageResource(R.drawable.user)
        profileText.setTextColor(resources.getColor(R.color.gray))

        searchTeam.setImageResource(R.drawable.support)
        searchTeamText.setTextColor(resources.getColor(R.color.gray))

        matching.setImageResource(R.drawable.cupid_pink)
        matchingText.setTextColor(resources.getColor(R.color.tingtingMain))
    }

    fun colorProfileIcon(){
        profile.setImageResource(R.drawable.user_pink)
        profileText.setTextColor(resources.getColor(R.color.tingtingMain))

        matching.setImageResource(R.drawable.cupid)
        matchingText.setTextColor(resources.getColor(R.color.gray))

        searchTeam.setImageResource(R.drawable.support)
        searchTeamText.setTextColor(resources.getColor(R.color.gray))

    }


    override fun onNothingSelected(parent: AdapterView<*>?) {
        Log.d("spinnerCheck","qweqwe")

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Log.d("spinnerCheck",position.toString())
    }

    fun socketConnect(){

        try{
            //socket 생성 및 연결 // 주소 들어가야 함.!

            var so = SocketListener()

            msocket.connect()
            msocket.on(Socket.EVENT_CONNECT,so.onConnect)
            Log.d("socketConnect","connect1")


//            var data   = JsonObject()
//            data.addProperty("userId" ,"1")
//            msocket.emit("enroll", data)

        }catch (e: URISyntaxException){

        }


    }

}
