package com.example.tintint_jw.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import com.example.tintint_jw.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        matching.setOnClickListener(){

            supportFragmentManager.beginTransaction().replace(R.id.mainFragment,TeamMatching()).commit()
        }


        searchTeam.setOnClickListener(){
            supportFragmentManager.beginTransaction().replace(R.id.mainFragment,SearchTeam()).commit()
        }

        profile.setOnClickListener(){
            //supportFragmentManager.beginTransaction().replace(R.id.mainFragment,).commit()
        }
    }
}
