package com.vimalcvs.mvi.intent

sealed class MainIntent {
    data object FetchPosts : MainIntent()
}
