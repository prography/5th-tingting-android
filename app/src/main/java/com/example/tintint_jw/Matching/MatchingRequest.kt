package com.example.tintint_jw.Matching

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.tintint_jw.R
import kotlinx.android.synthetic.main.fragment_matching_request.*
import kotlinx.android.synthetic.main.fragment_matching_request.view.*

class MatchingRequest : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_matching_request, null)
        // 처리
        val adapter= PagerAdapter(activity!!.supportFragmentManager)
        view.MatchingViewPager.adapter=adapter
        view.tab.setupWithViewPager(MatchingViewPager)

        return view
    }
}