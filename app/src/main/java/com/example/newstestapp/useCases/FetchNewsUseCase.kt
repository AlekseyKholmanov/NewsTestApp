package com.example.newstestapp.useCases

import com.example.newstestapp.models.network.BaseResult
import com.example.newstestapp.models.network.toDTOs
import com.example.newstestapp.models.ui.NewsSearchResult
import com.example.newstestapp.network.NewsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class FetchNewsUseCase(private val api: NewsApi) {

    suspend fun fetchNews(category: String, offset: Int = 0): BaseResult<NewsSearchResult> =
        withContext(Dispatchers.IO) {
            val response = api.getNews(category.lowercase(), offset = offset)
            val data = response.body()?.data
            val limit = response.body()?.pagination?.total ?: 0
            return@withContext if (response.isSuccessful && data != null) {
                val dtos = data.toDTOs()
                BaseResult(data = NewsSearchResult(news = dtos, total = limit), isSuccessful = true)
            } else {
                val responseStr = response.errorBody()?.string()
                val jObjError = JSONObject(responseStr)
                val jObjMeta = jObjError.getJSONObject("error")
                BaseResult(message = jObjMeta.toString())
            }
        }
}