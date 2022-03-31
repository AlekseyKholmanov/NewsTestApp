package com.example.newstestapp.orm

import com.example.newstestapp.models.database.toDTO
import com.example.newstestapp.models.database.toEntity
import com.example.newstestapp.models.domain.NewsDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteNewsDatabase(
    private val favoriteNewsDao: FavoriteNewsDao
) {

    suspend fun saveNews(newsDto: NewsDTO) {
        favoriteNewsDao.saveNews(newsDto.toEntity())
    }

    suspend fun deleteFromFavorite(id: Int) {
        favoriteNewsDao.deleteFromFavorite(id)
    }

    fun observeFavorite(): Flow<List<NewsDTO>> {
        return favoriteNewsDao.observeFavorite()
            .map {
                it.map { it.toDTO() }
            }
    }

}