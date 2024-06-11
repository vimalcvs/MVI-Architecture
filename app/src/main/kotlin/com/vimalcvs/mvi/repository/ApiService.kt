package com.vimalcvs.mvi.repository

import com.vimalcvs.mvi.model.ModelPost
import retrofit2.http.GET

interface ApiService {
    @GET("/posts")
    suspend fun getPosts(): List<ModelPost>
}
