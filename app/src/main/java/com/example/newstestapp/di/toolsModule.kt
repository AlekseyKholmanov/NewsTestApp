package com.example.newstestapp.di

import android.content.Context
import android.net.ConnectivityManager
import com.example.newstestapp.utils.Constants
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module

val toolsModule = module {
    single {
        androidApplication().applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    single(named(Constants.ChannelsName.GoogleQualifier.name)) {
        MutableStateFlow<GoogleSignInAccount?>(null)
    }
}