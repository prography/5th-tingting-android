package com.example.tintint_jw.Matching

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tintint_jw.R
import kotlinx.android.synthetic.main.fragment_matching_viewpager.view.*

class SecondPagerFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_matching_viewpager, null)
        view.profileImage.setImageResource(R.drawable.iu2)
        view.profileName.setText("IU,28")
        view.profileHeight.setText("162cm")
        // 처리
        return view
    }
}