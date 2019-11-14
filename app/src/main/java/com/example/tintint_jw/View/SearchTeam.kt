package com.example.tintint_jw.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tintint_jw.R
import kotlinx.android.synthetic.main.fragment_search_team.view.*

class SearchTeam : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_search_team, null)

        view.makeTeamButton.setImageResource(R.drawable.plus)

        //using fragment
        view.makeTeamButton.setOnClickListener(){
            activity?.supportFragmentManager!!.beginTransaction().replace(R.id.mainFragment,TeamInfo()).commit()
        }


        return view
    }
    }

