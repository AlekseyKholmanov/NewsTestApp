package com.example.newstestapp.network

import com.example.newstestapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttp = original.url
        val url = originalHttp.newBuilder()
            .addQueryParameter("access_key", BuildConfig.MEDIATEK_KEY)
            .build()
        val requestBuilder = original.newBuilder()
            .url(url)
            .build()

        return chain.proceed(requestBuilder)
    }
}