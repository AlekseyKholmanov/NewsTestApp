package com.example.newstestapp.di

import com.example.newstestapp.ui.viewModels.FavoriteViewModel
import com.example.newstestapp.ui.viewModels.NewsViewModel
import com.example.newstestapp.utils.Constants
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel {
        NewsViewModel(
            fetchNewsUseCase = get(),
            getNetworkInfoUseCase = get(),
            favoriteNewsUseCase = get()
        )
    }
    viewModel{
        FavoriteViewModel(
            favoriteNewsUseCase = get(),
            googleSignInInfoChannel = get(qualifier = named(Constants.ChannelsName.GoogleQualifier.name))
        )
    }
}