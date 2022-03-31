package com.example.newstestapp.models.ui

import com.example.newstestapp.models.domain.NewsDTO
import com.example.newstestapp.ui.adapters.Item

data class NewsItem(

    val author: String?,

    val title: String,

    val description: String,

    val url: String,

    val source: String,

    val image: String?,

    val category: String,

    val id: Int,

    val isFavorite: Boolean
) : Item

fun NewsItem.toDTO():NewsDTO{
    return NewsDTO(
        author = author,
        title = title,
        description = description,
        url = url,
        source = source,
        image = image,
        id = id,
        catgory = category
    )
}