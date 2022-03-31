package com.example.newstestapp.ui.adapters.decorators

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class FeedHorizontalDividerItemDecoration(
    private val divider: Int,
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val oneSideHorizontalDivider = divider / 2

        with(outRect) {
            left = divider
            right = divider
            top = oneSideHorizontalDivider
            bottom = oneSideHorizontalDivider
        }
    }

}