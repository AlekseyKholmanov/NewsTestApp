package com.example.newstestapp.di

import androidx.room.Room
import com.example.newstestapp.orm.AppDatabase
import com.example.newstestapp.orm.FavoriteNewsDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dbModule = module {
    val DATABASE_NAME = "planner_database"

    single {
        Room.databaseBuilder(androidApplication(), AppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    single {
        get<AppDatabase>().favoriteDao()
    }

    single {
        FavoriteNewsDatabase(
            favoriteNewsDao = get()
        )
    }
}
