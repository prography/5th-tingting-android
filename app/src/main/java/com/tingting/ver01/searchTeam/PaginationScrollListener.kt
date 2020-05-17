package com.tingting.ver01.searchTeam

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScrollListener(var layoutManager: LinearLayoutManager): RecyclerView.OnScrollListener(){

    abstract fun isLastPage():Boolean
    abstract fun isLoading():Boolean


    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount


        val firstPosition = layoutManager.findFirstVisibleItemPosition()


        if (!isLastPage() && !isLoading()) {

            if ((visibleItemCount + firstPosition >= totalItemCount) && (firstPosition >= 0)) {
                loadMoreItems()

            }
        }


    }
    abstract fun loadMoreItems()

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
    }
}