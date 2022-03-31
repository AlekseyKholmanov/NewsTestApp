package com.example.newstestapp.ui.adapters.fingerprints

import android.text.Html
import android.text.Html.FROM_HTML_MODE_COMPACT
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.newstestapp.R
import com.example.newstestapp.databinding.ItemNewsBinding
import com.example.newstestapp.models.ui.NewsItem
import com.example.newstestapp.ui.adapters.BaseViewHolder
import com.example.newstestapp.ui.adapters.Item
import com.example.newstestapp.ui.adapters.ItemFingerprint

class NewsFingerprint(
    private val showMore: (String) -> Unit,
    private val changeFavoriteState: (Int) -> Unit,
    private val showCategory: Boolean = false
) : ItemFingerprint<ItemNewsBinding, NewsItem> {
    override fun isRelativeItem(item: Item): Boolean = item is NewsItem
    override fun getLayoutId(): Int = R.layout.item_news

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<ItemNewsBinding, NewsItem> {

        val binding = ItemNewsBinding.inflate(layoutInflater, parent, false)
        return NewsFingerprintViewHolder(
            binding,
            showMore = showMore,
            changeFavoriteState = changeFavoriteState,
            showCategory = showCategory
        )
    }

    override fun getDiffUtil(): DiffUtil.ItemCallback<NewsItem> = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<NewsItem>() {

        override fun areItemsTheSame(
            oldItem: NewsItem,
            newItem: NewsItem
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: NewsItem,
            newItem: NewsItem
        ) = oldItem == newItem


        override fun getChangePayload(oldItem: NewsItem, newItem: NewsItem): Any? {
            if (oldItem.isFavorite != newItem.isFavorite) return newItem.isFavorite
            return super.getChangePayload(oldItem, newItem)
        }
    }
}


class NewsFingerprintViewHolder(
    binding: ItemNewsBinding,
    val showMore: (String) -> Unit,
    val changeFavoriteState: (Int) -> Unit,
    val showCategory: Boolean
) : BaseViewHolder<ItemNewsBinding, NewsItem>(binding) {

    init {
        binding.root.setOnClickListener {
            if (bindingAdapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener
        }
    }

    override fun onBind(item: NewsItem) {
        super.onBind(item)
        with(binding) {
            title.text = Html.fromHtml(item.title,FROM_HTML_MODE_COMPACT)
            description.text = Html.fromHtml(item.description,FROM_HTML_MODE_COMPACT)
            source.text = item.source
            with(favoriteLabel) {
                setChecked(item.isFavorite)
                setOnClickListener {
                    changeFavoriteState(item.id)
                }
            }
            image.load(item.image){
                crossfade(true)
                placeholder(R.drawable.img_no_image)
                error(R.drawable.img_no_image)
                transformations(RoundedCornersTransformation(8f,8f,8f,8f))
            }
            if(showCategory){
                category.visibility = View.VISIBLE
                category.text = item.category
            }

            showMore.setOnClickListener {
                showMore(item.url)
            }

        }
    }


    override fun onBind(item: NewsItem, payloads: List<Any>) {
        super.onBind(item, payloads)
        val isSaved = payloads.last() as Boolean
        binding.favoriteLabel.setChecked(isSaved)
    }

    private fun ImageView.setChecked(isChecked: Boolean) {
        val icon = when (isChecked) {
            true -> R.drawable.ic_favorite
            false -> R.drawable.ic_favorite_border
        }
        setImageResource(icon)
    }

}