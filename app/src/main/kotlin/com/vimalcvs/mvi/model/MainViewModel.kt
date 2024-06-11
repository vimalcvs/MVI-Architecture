package com.vimalcvs.mvi.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vimalcvs.mvi.intent.MainIntent
import com.vimalcvs.mvi.intent.MainState
import com.vimalcvs.mvi.repository.RetrofitInstance
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import java.io.IOException

class MainViewModel : ViewModel() {

    private val _state = MutableLiveData<MainState>()
    val state: LiveData<MainState>
        get() = _state

    private val intentChannel = Channel<MainIntent>(Channel.UNLIMITED)
    val intents: SendChannel<MainIntent> = intentChannel

    init {
        handleIntents()
    }

    private fun handleIntents() {
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect { mainIntent ->
                when (mainIntent) {
                    is MainIntent.FetchPosts -> fetchPosts()
                }
            }
        }
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            _state.value = MainState.Loading
            _state.value = try {
                val posts = RetrofitInstance.api.getPosts()
                if (posts.isNotEmpty()) {
                    MainState.Posts(posts)
                } else {
                    MainState.Error("No data available")
                }
            } catch (e: IOException) {
                MainState.Error("No internet connection")
            } catch (e: Exception) {
                MainState.Error(e.localizedMessage ?: "An error occurred")
            }
        }
    }
}
