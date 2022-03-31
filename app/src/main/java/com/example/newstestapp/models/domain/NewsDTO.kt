package com.example.newstestapp.models.domain

import com.example.newstestapp.models.ui.NewsItem

data class NewsDTO(

    val author: String?,

    val title: String,

    val description: String,

    val url: String,

    val source: String,

    val image: String?,

    val catgory: String,

    val id: Int
)

fun NewsDTO.toItem(isFavorite: Boolean): NewsItem {
    return NewsItem(
        author = author,
        title = title,
        description = description,
        url = url,
        source = source,
        image = image,
        isFavorite = isFavorite,
        id = id,
        category = catgory
    )
}
