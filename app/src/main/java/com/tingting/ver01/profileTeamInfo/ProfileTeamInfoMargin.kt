package com.tingting.ver01.teamInfo

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ProfileTeamInfoMargin (private val spaceHeight:Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State) {
        with(outRect) {
            bottom = spaceHeight
        }
    }
}