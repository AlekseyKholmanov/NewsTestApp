package com.example.newstestapp.di

import com.example.newstestapp.useCases.FavoriteNewsUseCase
import com.example.newstestapp.useCases.FetchNewsUseCase
import com.example.newstestapp.useCases.GetNetworkInfoUseCase
import org.koin.dsl.module

val useCasesModule = module{
    single {
        FetchNewsUseCase(api = get())
    }
    single{
        GetNetworkInfoUseCase(networkManager = get())
    }
    single {
        FavoriteNewsUseCase(favoriteDatabase = get())
    }
}