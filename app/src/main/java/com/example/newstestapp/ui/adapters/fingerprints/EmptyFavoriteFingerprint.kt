package com.example.newstestapp.ui.adapters.fingerprints

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.newstestapp.R
import com.example.newstestapp.databinding.ItemFavoriteEmptyBinding
import com.example.newstestapp.models.ui.EmptyFavoriteItem
import com.example.newstestapp.ui.adapters.BaseViewHolder
import com.example.newstestapp.ui.adapters.Item
import com.example.newstestapp.ui.adapters.ItemFingerprint

class EmptyDFavoriteFingerprint() : ItemFingerprint<ItemFavoriteEmptyBinding, EmptyFavoriteItem> {
    override fun isRelativeItem(item: Item): Boolean = item is EmptyFavoriteItem
    override fun getLayoutId(): Int = R.layout.item_favorite_empty

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<ItemFavoriteEmptyBinding, EmptyFavoriteItem> {
        val binding = ItemFavoriteEmptyBinding.inflate(layoutInflater, parent, false)
        return EmptyFavoriteViewHolder(binding)
    }

    override fun getDiffUtil(): DiffUtil.ItemCallback<EmptyFavoriteItem> = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<EmptyFavoriteItem>() {

        override fun areItemsTheSame(
            oldItem: EmptyFavoriteItem,
            newItem: EmptyFavoriteItem
        ) = true

        override fun areContentsTheSame(
            oldItem: EmptyFavoriteItem,
            newItem: EmptyFavoriteItem
        ) = true
    }
}


class EmptyFavoriteViewHolder(
    binding: ItemFavoriteEmptyBinding,
) : BaseViewHolder<ItemFavoriteEmptyBinding, EmptyFavoriteItem>(binding) {}