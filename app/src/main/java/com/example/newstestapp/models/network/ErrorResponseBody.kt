package com.example.newstestapp.models.network

import com.google.gson.annotations.SerializedName

class ErrorResponseBody(
    @SerializedName("code")
    val code: String,

    @SerializedName("message")
    val message: String

)