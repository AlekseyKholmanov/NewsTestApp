package com.example.newstestapp.ui.adapters.fingerprints

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newstestapp.R
import com.example.newstestapp.databinding.ItemNewsCategoryBinding
import com.example.newstestapp.utils.Constants
import com.example.newstestapp.ui.adapters.BaseViewHolder
import com.example.newstestapp.ui.adapters.Item
import com.example.newstestapp.ui.adapters.ItemFingerprint

class NewsCategoryFingerprint(
    private val onCLick: (Constants.NewsCategory) -> Unit
) : ItemFingerprint<ItemNewsCategoryBinding, Constants.NewsCategory> {
    override fun isRelativeItem(item: Item): Boolean = item is Constants.NewsCategory
    override fun getLayoutId(): Int = R.layout.item_news_category

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<ItemNewsCategoryBinding, Constants.NewsCategory> {
        val binding = ItemNewsCategoryBinding.inflate(layoutInflater, parent, false)
        return NewsCategoryViewHolder(binding, onClick = onCLick)
    }

    override fun getDiffUtil(): DiffUtil.ItemCallback<Constants.NewsCategory> = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<Constants.NewsCategory>() {

        override fun areItemsTheSame(
            oldItem: Constants.NewsCategory,
            newItem: Constants.NewsCategory
        ) = oldItem.param == newItem.param

        override fun areContentsTheSame(
            oldItem: Constants.NewsCategory,
            newItem: Constants.NewsCategory
        ) = oldItem == newItem


    }
}


class NewsCategoryViewHolder(
    binding: ItemNewsCategoryBinding,
    val onClick: (Constants.NewsCategory) -> Unit
) : BaseViewHolder<ItemNewsCategoryBinding, Constants.NewsCategory>(binding) {

    init {
        binding.root.setOnClickListener {
            if (bindingAdapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener
            onClick(item)
        }
    }

    override fun onBind(item: Constants.NewsCategory) {
        super.onBind(item)
        with(binding) {
            categoryName.text = item.param
        }
    }

}