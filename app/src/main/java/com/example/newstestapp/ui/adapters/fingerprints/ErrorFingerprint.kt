package com.example.newstestapp.ui.adapters.fingerprints

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.newstestapp.R
import com.example.newstestapp.databinding.ItemErrorBinding
import com.example.newstestapp.models.ui.ErrorItem
import com.example.newstestapp.models.ui.ShortLoadingItem
import com.example.newstestapp.ui.adapters.BaseViewHolder
import com.example.newstestapp.ui.adapters.Item
import com.example.newstestapp.ui.adapters.ItemFingerprint

class ErrorFingerprint(
    private val fetchAgain: () -> Unit
) : ItemFingerprint<ItemErrorBinding, ErrorItem> {
    override fun isRelativeItem(item: Item): Boolean = item is ErrorItem
    override fun getLayoutId(): Int = R.layout.item_error

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<ItemErrorBinding, ErrorItem> {
        val binding = ItemErrorBinding.inflate(layoutInflater, parent, false)
        return ErrorViewHolder(
            fetchAgain = fetchAgain,
            binding = binding
        )
    }

    override fun getDiffUtil(): DiffUtil.ItemCallback<ErrorItem> = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<ErrorItem>() {

        override fun areItemsTheSame(
            oldItem: ErrorItem,
            newItem: ErrorItem
        ) = true

        override fun areContentsTheSame(
            oldItem: ErrorItem,
            newItem: ErrorItem
        ) = true
    }
}


class ErrorViewHolder(
    private val fetchAgain: () -> Unit,
    binding: ItemErrorBinding,
) : BaseViewHolder<ItemErrorBinding, ErrorItem>(binding) {
    init {
        binding.tryAgainButton.setOnClickListener {
            fetchAgain.invoke()
        }
    }
}