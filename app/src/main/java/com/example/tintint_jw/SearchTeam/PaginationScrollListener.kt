package com.example.tintint_jw.SearchTeam

import android.text.BoringLayout
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_search_team.view.*
import kotlin.math.abs

abstract class PaginationScrollListener


(var layoutManager: LinearLayoutManager): RecyclerView.OnScrollListener(){

    abstract fun isLastPage():Boolean
    abstract fun isLoading():Boolean

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount


    }
    abstract fun loadMoreItems()


}