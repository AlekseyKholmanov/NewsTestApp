package com.example.newstestapp.di

import com.example.newstestapp.network.ApiKeyInterceptor
import com.example.newstestapp.network.NewsApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


private const val CONNECT_TIMEOUT = 10L
private const val WRITE_TIMEOUT = 10L
private const val READ_TIMEOUT = 30L
private const val CALL_TIMEOUT = 60L
private const val BASE_URL = "http://api.mediastack.com/"

val networkModule = module {

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .callTimeout(CALL_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(ApiKeyInterceptor())
            .addInterceptor(HttpLoggingInterceptor())
            .build()
    }

    single {
        GsonBuilder()
            .setLenient()
            .create()
    }

    single {
        Retrofit
            .Builder()
            .client(get(clazz = OkHttpClient::class))
            .addConverterFactory(GsonConverterFactory.create(get(clazz = Gson::class)))
            .baseUrl(BASE_URL)
            .build()
            .create(NewsApi::class.java)
    }
}