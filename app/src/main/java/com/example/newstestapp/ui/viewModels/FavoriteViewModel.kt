package com.example.newstestapp.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newstestapp.models.ui.EmptyFavoriteItem
import com.example.newstestapp.models.ui.GoogleSignInItem
import com.example.newstestapp.ui.adapters.Item
import com.example.newstestapp.useCases.FavoriteNewsUseCase
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val favoriteNewsUseCase: FavoriteNewsUseCase,
    private val googleSignInInfoChannel: MutableStateFlow<GoogleSignInAccount?>
) : ViewModel() {

    val userIntent = Channel<Wish>(Channel.UNLIMITED)

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State>
        get() = _state

    init {

        handleIntent()
        val favoriteFlow = favoriteNewsUseCase.observeFavorite()

        googleSignInInfoChannel.combine(favoriteFlow) { googleInfo, news ->
            if (googleInfo == null) {
                listOf(GoogleSignInItem())
            } else {
                news.ifEmpty { listOf(EmptyFavoriteItem()) }
            }
        }.onEach {
            _state.value = _state.value.copy(news = it)
        }.launchIn(viewModelScope)
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeEach {
                when (it) {
                    is Wish.RemoveFromFavorite -> {
                        viewModelScope.launch {
                            favoriteNewsUseCase.deleteFromFavorite(it.id)
                        }
                    }
                }
            }
        }
    }

    sealed class Wish {
        class RemoveFromFavorite(val id: Int) : Wish()
    }

    data class State(
        val news: List<Item> = listOf()
    )
}

