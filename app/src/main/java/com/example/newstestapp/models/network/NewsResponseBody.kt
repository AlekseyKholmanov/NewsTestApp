package com.example.newstestapp.models.network

import com.google.gson.annotations.SerializedName

class NewsResponseBody(

    @SerializedName("error")
    val error: ErrorResponseBody?,

    @SerializedName("pagination")
    val pagination: PaginationResponseBody?,

    @SerializedName("data")
    val data: List<NewsDataResponseBody>?
)