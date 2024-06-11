package com.vimalcvs.mvi.intent

import com.vimalcvs.mvi.model.ModelPost

sealed class MainState {
    data object Idle : MainState()
    data object Loading : MainState()
    data class Posts(val modelPosts: List<ModelPost>) : MainState()
    data class NoData(val message: String) : MainState()
    data class Error(val error: String) : MainState()
}
