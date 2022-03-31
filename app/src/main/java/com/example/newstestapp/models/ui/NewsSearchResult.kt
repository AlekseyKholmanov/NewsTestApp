package com.example.newstestapp.models.ui

import com.example.newstestapp.models.domain.NewsDTO

data class NewsSearchResult(
    val news: List<NewsDTO>,
    val total: Int
)