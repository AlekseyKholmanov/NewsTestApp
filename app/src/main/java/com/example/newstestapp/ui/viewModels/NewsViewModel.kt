package com.example.newstestapp.ui.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newstestapp.models.domain.toItem
import com.example.newstestapp.models.ui.ErrorItem
import com.example.newstestapp.models.ui.FullPageLoadingItem
import com.example.newstestapp.models.ui.NewsItem
import com.example.newstestapp.models.ui.ShortLoadingItem
import com.example.newstestapp.ui.adapters.Item
import com.example.newstestapp.useCases.FavoriteNewsUseCase
import com.example.newstestapp.useCases.FetchNewsUseCase
import com.example.newstestapp.useCases.GetNetworkInfoUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NewsViewModel(
    private val fetchNewsUseCase: FetchNewsUseCase,
    private val getNetworkInfoUseCase: GetNetworkInfoUseCase,
    private val favoriteNewsUseCase: FavoriteNewsUseCase
) : ViewModel() {

    val userIntent = Channel<Wish>(Channel.CONFLATED)

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State>
        get() = _state

    val errorHandler = CoroutineExceptionHandler { _, exception ->
        println("CoroutineExceptionHandler got $exception")
        prepareError()
    }

    var limit = 0
    var category = ""
    var newsOffset: Int = 0

    init {
        handleIntent()
        favoriteNewsUseCase.observeFavorite()
            .map { it.map { it.id }.toHashSet() }
            .onEach { ids ->
                val currentNews = _state.value.news
                val new = currentNews.map { item ->
                    val newitem = if (item is NewsItem) {
                        item.copy(isFavorite = ids.contains(item.id))
                    } else {
                        item
                    }
                    newitem
                }
                _state.value = _state.value.copy(news = new, favorites = ids)

            }.launchIn(viewModelScope)
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeEach { wish ->
                when (wish) {
                    is Wish.FetchNews -> {
                        category = wish.category
                        prepareLoading()
                        viewModelScope.launch(errorHandler) {
                            val news = fetchNewsUseCase.fetchNews(wish.category, newsOffset)
                            val currentState = _state.value
                            if (news.isSuccessful) {
                                limit = news.data?.total ?: 0
                                val mappedNews = news.data?.news
                                    ?.map { dto ->
                                        dto.toItem(currentState.favorites.contains(dto.id))
                                    } ?: listOf()
                                val newNews =
                                    currentState.news.filterIsInstance<NewsItem>().toMutableList()
                                newNews.addAll(mappedNews)
                                val newState = _state.value.copy(news = newNews)
                                newsOffset += mappedNews.size
                                _state.value = newState
                            } else {
                                prepareError()
                            }
                        }
                    }
                    is Wish.ChangeFavoriteState -> {
                        val item = _state.value.news.filterIsInstance<NewsItem>()
                            .find { it.id == wish.id }
                        viewModelScope.launch {
                            if (item?.isFavorite == true) {
                                favoriteNewsUseCase.deleteFromFavorite(wish.id)
                            } else {
                                item?.let { favoriteNewsUseCase.saveTofavorites(it) }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun prepareLoading() {
        val currentNews = _state.value.news.filterIsInstance<NewsItem>().toMutableList()
        val loadingItem: Item = if (currentNews.size == 0) {
            FullPageLoadingItem()
        } else {
            ShortLoadingItem()
        }
        val news = mutableListOf<Item>().apply {
            addAll(currentNews)
            add(loadingItem)
        }
        _state.value = _state.value.copy(news = news)
    }

    private fun prepareError() {
        val currentNews =
            _state.value.news.toMutableList().filterIsInstance<NewsItem>().toMutableList()
        val news = mutableListOf<Item>().apply {
            addAll(currentNews)
            add(ErrorItem())
        }
        _state.value = _state.value.copy(news = news)
    }

    sealed class Wish {
        class FetchNews(val category: String) : Wish()
        class ChangeFavoriteState(val id: Int) : Wish()
    }

    data class State(
        val news: List<Item> = listOf(),
        val favorites: HashSet<Int> = hashSetOf()
    )
}
