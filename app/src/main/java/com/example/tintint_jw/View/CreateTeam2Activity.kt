package com.example.tintint_jw.View

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.tintint_jw.R
import kotlinx.android.synthetic.main.activity_create_team2.*
import kotlinx.android.synthetic.main.dialog_view.view.*

class CreateTeam2Activity : AppCompatActivity(), View.OnClickListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_team2)

        createteam2RegisterBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when(v.id){
                R.id.createteam2RegisterBtn->{
                    val checkDialog = AlertDialog.Builder(this)
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
    }
}