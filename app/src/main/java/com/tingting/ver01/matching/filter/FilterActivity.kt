package com.tingting.ver01.matching.filter

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jaygoo.widget.OnRangeChangedListener
import com.jaygoo.widget.RangeSeekBar
import com.tingting.ver01.R
import kotlinx.android.synthetic.main.activity_filter.*

class FilterActivity : AppCompatActivity() {
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)

        back.setOnClickListener {
            finish()
        }

        genderSegmentedGroup.setTintColor(resources.getColor(R.color.tingtingMain),resources.getColor(R.color.white))
        memberSegmentedGroup.setTintColor(resources.getColor(R.color.tingtingMain), resources.getColor(R.color.white))

        val ageRange: RangeSeekBar = findViewById(R.id.ageRange)
        ageRange.invalidate()
        ageRange.leftSeekBar.setIndicatorText("20세")
        ageRange.rightSeekBar.setIndicatorText("29세")
        ageRange.setProgress(20F, 29F)

        ageRange.setRange(20F, 29F)
        ageRange.setOnRangeChangedListener(object: OnRangeChangedListener{
            override fun onStartTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {
                ageMin.text = ""
                ageMax.text = ""
        }

            override fun onRangeChanged(
                view: RangeSeekBar?,
                leftValue: Float,
                rightValue: Float,
                isFromUser: Boolean
            ) {
                ageRange.leftSeekBar.setIndicatorText(leftValue.toInt().toString()+"세")
                ageRange.rightSeekBar.setIndicatorText(rightValue.toInt().toString()+"세")
                /*ageMin.setText(leftValue.toInt().toString())
                ageMax.setText(rightValue.toInt().toString())*/
            }

            override fun onStopTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {

                ageMin.text = ""
                ageMax.text = ""
            }

        })

    }
}