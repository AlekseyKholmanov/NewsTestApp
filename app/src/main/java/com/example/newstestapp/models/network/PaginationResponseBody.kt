package com.example.newstestapp.models.network

import com.google.gson.annotations.SerializedName

class PaginationResponseBody(
    @SerializedName("limit")
    val limit: Int,

    @SerializedName("offset")
    val offset: Int,

    @SerializedName("count")
    val count: Int,

    @SerializedName("total")
    val total: Int,
)