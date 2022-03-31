package com.example.newstestapp.orm

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newstestapp.models.database.NewsEntity

@Database(
    entities = [
        NewsEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteDao():FavoriteNewsDao
}
