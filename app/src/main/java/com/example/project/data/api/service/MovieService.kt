package com.example.project.data.api.service

import com.example.project.data.model.MovieDetailResponse
import com.example.project.data.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface MovieService {
    @GET("top_rated")
    suspend fun getNowPlaying(@Header("Authorization") token: String): MovieResponse

    @GET("{movieId}")
    suspend fun getDetail(@Path("movieId") movieId: Int, @Header("Authorization") token: String): MovieDetailResponse
}