package com.example.newstestapp.ui.adapters.fingerprints

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.newstestapp.R
import com.example.newstestapp.databinding.ItemLoadingFullBinding
import com.example.newstestapp.models.ui.FullPageLoadingItem
import com.example.newstestapp.ui.adapters.BaseViewHolder
import com.example.newstestapp.ui.adapters.Item
import com.example.newstestapp.ui.adapters.ItemFingerprint

class FullPageLoadingFingerprint() : ItemFingerprint<ItemLoadingFullBinding, FullPageLoadingItem> {
    override fun isRelativeItem(item: Item): Boolean = item is FullPageLoadingItem
    override fun getLayoutId(): Int = R.layout.item_loading_full

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<ItemLoadingFullBinding, FullPageLoadingItem> {
        val binding = ItemLoadingFullBinding.inflate(layoutInflater, parent, false)
        return FullPageViewHolder(binding)
    }

    override fun getDiffUtil(): DiffUtil.ItemCallback<FullPageLoadingItem> = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<FullPageLoadingItem>() {

        override fun areItemsTheSame(
            oldItem: FullPageLoadingItem,
            newItem: FullPageLoadingItem
        ) = true

        override fun areContentsTheSame(
            oldItem: FullPageLoadingItem,
            newItem: FullPageLoadingItem
        ) = true
    }
}


class FullPageViewHolder(
    binding: ItemLoadingFullBinding,
) : BaseViewHolder<ItemLoadingFullBinding, FullPageLoadingItem>(binding) {}