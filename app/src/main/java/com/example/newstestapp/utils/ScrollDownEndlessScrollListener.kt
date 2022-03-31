package com.example.newstestapp.utils

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Scroll listener to trigger loading more when scrolling down
 *
 */
abstract class ScrollDownEndlessScrollListener : RecyclerView.OnScrollListener() {

    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private var visibleThreshold = 2

    // True if we are still waiting for the last set of data to load.
    var loadingAfter = false

    // This happens many times a second during a scroll, so be wary of the code you place here.
    // We are given a few useful parameters to help us work out if we need to load some more data,
    // but first we check if we are waiting for the previous load to finish.
    override fun onScrolled(view: RecyclerView, dx: Int, dy: Int) {

        val totalItemCount = (view.layoutManager as LinearLayoutManager).itemCount
        val lastVisibleItemPosition =
            (view.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
        if (!loadingAfter && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
            loadAfter()
            loadingAfter = true
        }
    }

    abstract fun loadAfter()

}