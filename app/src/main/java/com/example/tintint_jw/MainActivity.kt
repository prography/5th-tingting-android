package com.example.tintint_jw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setonClickListenr()

}

    fun setonClickListenr(){

        searchTeam.setOnClickListener(){
            supportFragmentManager.beginTransaction().replace(R.id.mainFragment,TeamSerachFragment()).commit()
        }

        matching.setOnClickListener(){
            supportFragmentManager.beginTransaction().replace(R.id.mainFragment,MatchingFragment()).commit()
        }

        profile.setOnClickListener(){
            supportFragmentManager.beginTransaction().replace(R.id.mainFragment,ProfielFragment()).commit()
        }
    }

}
