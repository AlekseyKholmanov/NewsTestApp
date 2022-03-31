package com.example.newstestapp.models.network

import com.example.newstestapp.models.domain.NewsDTO
import com.google.gson.annotations.SerializedName

class NewsDataResponseBody(

    @SerializedName("author")
    val author: String?,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("url")
    val url: String,

    @SerializedName("source")
    val source: String,

    @SerializedName("image")
    val image: String?,

    @SerializedName("category")
    val category: String,

    @SerializedName("language")
    val language: String,

    @SerializedName("country")
    val country: String,

    @SerializedName("published_at")
    val published_at: String,
)

fun NewsDataResponseBody.toDTO(): NewsDTO {
    return NewsDTO(
        author = author,
        title = title,
        description = description,
        url = url,
        source = source,
        image = image,
        id = url.hashCode(),
        catgory = category
    )
}

fun List<NewsDataResponseBody>.toDTOs(): List<NewsDTO> {
    return this.map { it.toDTO() }
}