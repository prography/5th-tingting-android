package com.tingting.ver01.TeamInfo

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class TeamInfoRecyclerViewMargin(private val spaceWidth:Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State) {
        with(outRect) {
            right = spaceWidth

        }
    }
}