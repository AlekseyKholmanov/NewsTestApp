package com.example.newstestapp.useCases

import com.example.newstestapp.models.domain.toItem
import com.example.newstestapp.models.ui.NewsItem
import com.example.newstestapp.models.ui.toDTO
import com.example.newstestapp.orm.FavoriteNewsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class FavoriteNewsUseCase(
    private val favoriteDatabase: FavoriteNewsDatabase
) {
    suspend fun saveTofavorites(newsItem: NewsItem) {
        favoriteDatabase.saveNews(newsItem.toDTO())
    }

    suspend fun deleteFromFavorite(id: Int) = withContext(Dispatchers.Default) {
        favoriteDatabase.deleteFromFavorite(id)
    }

    fun observeFavorite(): Flow<List<NewsItem>> {
        return favoriteDatabase.observeFavorite()
            .map {
                it.map { it.toItem(true) }
            }
    }
}