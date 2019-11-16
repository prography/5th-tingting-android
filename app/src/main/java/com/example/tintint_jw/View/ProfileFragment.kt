package com.example.tintint_jw.TeamInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tintint_jw.R
import com.example.tintint_jw.View.ProfileDetailFragment
import kotlinx.android.synthetic.main.profile_fragment.view.*


class ProfileFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.profile_fragment, null)
        // move to detail profile fragment
        view.newteamEditProfileTV.setOnClickListener(){
            activity!!.supportFragmentManager.beginTransaction().
                replace(R.id.mainFragment,ProfileDetailFragment()).commit()
        }

        return view
    }

}