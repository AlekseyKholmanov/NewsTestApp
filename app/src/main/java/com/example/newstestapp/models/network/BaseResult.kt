package com.example.newstestapp.models.network

class BaseResult<T>(
    val isSuccessful: Boolean = false,
    val message: String = "",
    val data: T? = null
)