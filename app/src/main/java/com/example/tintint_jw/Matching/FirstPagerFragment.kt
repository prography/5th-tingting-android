package com.example.tintint_jw.Matching

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.tintint_jw.R
import kotlinx.android.synthetic.main.fragment_matching_viewpager.view.*

class FirstPagerFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_matching_viewpager, null)
        view.profileImage.setImageResource(R.drawable.iu)
        view.findViewById<TextView>(R.id.profileName).text = "IU,27"
        view.findViewById<TextView>(R.id.profileHeight).text = "161cm"
        // 처리
        return view
    }
     fun title(): String{

        return "IU,27"
    }
}