package com.example.newstestapp.orm

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newstestapp.models.database.NewsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteNewsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveNews(entity: NewsEntity)

    @Query("DELETE from FavoriteNewsTable WHERE id=:id")
    suspend fun deleteFromFavorite(id:Int)

    @Query("SELECT * FROM FavoriteNewsTable ORDER BY addDate DESC ")
    fun observeFavorite(): Flow<List<NewsEntity>>

}