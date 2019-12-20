package com.example.tintint_jw.View

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.tintint_jw.R
import com.example.tintint_jw.TeamInfo.ProfileFragment
import kotlinx.android.synthetic.main.dialog_view.view.*
import kotlinx.android.synthetic.main.fragment_team_info.view.*
import kotlinx.android.synthetic.main.profile_detail_fragment.*
import kotlinx.android.synthetic.main.profile_detail_fragment.view.*
import kotlinx.android.synthetic.main.profile_fragment.view.*

class ProfileDetailFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.profile_detail_fragment, null)
        // move to detail profile fragment

       view.saveInfo.setOnClickListener(){
           val checkDialog = AlertDialog.Builder(activity)
           val dialogView = layoutInflater.inflate(R.layout.dialog_view, null)

           checkDialog.setView(dialogView)

           val check = checkDialog.show()
           val drawable = resources.getDrawable(R.drawable.dialog)

           Glide.with(newteamProfileImg).load(R.drawable.haein)
               .apply(RequestOptions.circleCropTransform()).into(newteamProfileImg)

           dialogView.dialogCancel.setOnClickListener{
               check.dismiss()
           }
           dialogView.dialogOK.setOnClickListener{
               activity!!.supportFragmentManager.beginTransaction().replace(R.id.mainFragment, ProfileFragment()).commit()
               check.dismiss()
           }

       }

        view.backButton.setOnClickListener(){
            activity!!.supportFragmentManager.popBackStack()
        }

        //logout

        view.logout.setOnClickListener(){
            val logOutDialog = AlertDialog.Builder(activity)
            val dialogView = layoutInflater.inflate(R.layout.dialog_logout,null)

            logOutDialog.setView(dialogView)
            val check = logOutDialog.show()

            dialogView.dialogCancel.setOnClickListener{
                check.dismiss()
            }
            dialogView.dialogOK.setOnClickListener{
                activity!!.finishAffinity()
            }

        }

        return view
    }

}