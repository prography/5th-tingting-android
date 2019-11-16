package com.example.tintint_jw.Matching

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tintint_jw.R
import kotlinx.android.synthetic.main.fragment_matching_main.view.*


class MatchingFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_matching_main, null)
        // 처리
        view.next.setOnClickListener(){

            activity!!.supportFragmentManager.beginTransaction().replace(R.id.mainFragment,MatchingRequest()).commit()
        }
        return view
    }
}