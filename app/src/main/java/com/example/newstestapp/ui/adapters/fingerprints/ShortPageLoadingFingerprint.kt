package com.example.newstestapp.ui.adapters.fingerprints

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.newstestapp.R
import com.example.newstestapp.databinding.ItemLoadingFullBinding
import com.example.newstestapp.databinding.ItemLoadingShortBinding
import com.example.newstestapp.models.ui.FullPageLoadingItem
import com.example.newstestapp.models.ui.ShortLoadingItem
import com.example.newstestapp.ui.adapters.BaseViewHolder
import com.example.newstestapp.ui.adapters.Item
import com.example.newstestapp.ui.adapters.ItemFingerprint

class ShortPageLoadingFingerprint() : ItemFingerprint<ItemLoadingShortBinding, ShortLoadingItem> {
    override fun isRelativeItem(item: Item): Boolean = item is ShortLoadingItem
    override fun getLayoutId(): Int = R.layout.item_loading_short

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<ItemLoadingShortBinding, ShortLoadingItem> {
        val binding = ItemLoadingShortBinding.inflate(layoutInflater, parent, false)
        return ShortPageViewHolder(binding)
    }

    override fun getDiffUtil(): DiffUtil.ItemCallback<ShortLoadingItem> = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<ShortLoadingItem>() {

        override fun areItemsTheSame(
            oldItem: ShortLoadingItem,
            newItem: ShortLoadingItem
        ) = true

        override fun areContentsTheSame(
            oldItem: ShortLoadingItem,
            newItem: ShortLoadingItem
        ) = true
    }
}


class ShortPageViewHolder(
    binding: ItemLoadingShortBinding,
) : BaseViewHolder<ItemLoadingShortBinding, ShortLoadingItem>(binding) {}