package com.example.tintint_jw.View

import android.content.ContextWrapper
import android.os.Bundle
import android.view.LayoutInflater
import android.app.AlertDialog
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tintint_jw.R
import kotlinx.android.synthetic.main.activity_create_team2.view.*
import kotlinx.android.synthetic.main.dialog_view.view.*

class CreateTeam2Fragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.activity_create_team2, null)
        val contextThemeWrapper = ContextWrapper(activity).apply { R.style.AppTheme2 }

        view.back.setOnClickListener(){
            activity!!.supportFragmentManager.beginTransaction().remove(this).commit()
        }

        view.createteam2RegisterBtn.setOnClickListener(){
            val checkDialog = AlertDialog.Builder(activity)
            val dialogView = layoutInflater.inflate(R.layout.dialog_view, null)

            checkDialog.setView(dialogView)

            val check = checkDialog.show()
            val drawable = resources.getDrawable(R.drawable.dialog)
       //     check.window!!.setBackgroundDrawable(drawable)
            dialogView.dialogCancel.setOnClickListener{
                check.dismiss()

            }
            dialogView.dialogOK.setOnClickListener{
                activity!!.supportFragmentManager.beginTransaction().replace(R.id.mainFragment, TeamInfo()).commit()
                check.dismiss()
            }

        }

        return view


    }

    /*override fun onClick(v: View?) {
        if (v != null) {
            when(v.id){
                R.id.createteam2RegisterBtn->{
                    val checkDialog = AlertDialog.Builder(activity!!.applicationContext)
                    val dialogView = layoutInflater.inflate(R.layout.dialog_view, null)

                    checkDialog.setView(dialogView)
                    val check = checkDialog.show()
                    val drawable = resources.getDrawable(R.drawable.dialog)
                    check.window!!.setBackgroundDrawable(drawable)
                    dialogView.dialogCancel.setOnClickListener{
                        check.dismiss()

                    }
                    dialogView.dialogOK.setOnClickListener{

                    }
                }
            }
        }
    }*/
}