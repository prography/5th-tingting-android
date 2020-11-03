package com.tingting.ver01.view.Main

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
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
import kotlinx.android.synthetic.main.activity_main_temp.*
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

        setContentView(R.layout.activity_main_temp)
        //touch 구분해주기 위한 변수


        //socketConnect()


        supportFragmentManager.beginTransaction().replace(R.id.mainFragment,
            MatchingFragment()
        ).commit()

        bottomNav.setOnNavigationItemSelectedListener (
            object : BottomNavigationView.OnNavigationItemSelectedListener{

                override fun onNavigationItemSelected(p0: MenuItem): Boolean {

                    when(p0.itemId){

                        R.id.matching->{
                            supportFragmentManager.beginTransaction().replace(R.id.mainFragment,
                                MatchingFragment()
                            ).commit()

                            return true
                        }

                        R.id.search->{
                            supportFragmentManager.beginTransaction().replace(R.id.mainFragment,
                                SearchTeamFragment()
                            ).commit()

                            return true
                        }

                        R.id.profile->{
                            supportFragmentManager.beginTransaction().replace(R.id.mainFragment,
                                ProfileFragment()
                            ).commit()

                            return true
                        }

                    }

                    return false
                }

            }
        )



        val index = intent.getIntExtra("notifiCode",99)

//        if(index ==99){
//            allowRefreshProfile = true
//            allowRefreshMatching = false
//            allowRefreshSearch = false
//            supportFragmentManager.beginTransaction().replace(R.id.mainFragment, ProfileFragment()).commit()
//        }else{
//            supportFragmentManager.beginTransaction().replace(R.id.mainFragment, SearchTeamFragment()).commit()
//        }




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


        }catch (e: URISyntaxException){

        }


    }

}
