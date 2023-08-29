package com.example.project.data.helper

import com.example.project.data.configuration.RetrofitClient
import com.example.project.data.model.MovieDetailResponse
import com.example.project.data.model.MovieResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun fetchDetail(authorization: String, id: Int): MovieDetailResponse {
    return withContext(Dispatchers.IO) {
        try {
            val apiService = RetrofitClient.movieService
            val response = apiService.getDetail(id, authorization)
            response
        } catch (e: Exception) {
            // Handle error
            MovieDetailResponse(
                false,
                "",
                0,
                emptyList(),
                "",
                0,
                "",
                "",
                "",
                "",
                0.0,
                "",
                emptyList(),
                emptyList(),
                "",
                0,
                0,
                emptyList(),
                "",
                "",
                "",
                false,
                0.0,
                0
            )
        }
    }
}

suspend fun fetchDataTopRated(authorization: String): MovieResponse {
    return withContext(Dispatchers.IO) {
        try {
            val apiService = RetrofitClient.movieService
            val response = apiService.getTopRated(authorization)
            response
        } catch (e: Exception) {
            // Handle error
            MovieResponse(0, emptyList(), 0, 0)
        }
    }
}

suspend fun fetchDataPopular(authorization: String): MovieResponse {
    return withContext(Dispatchers.IO) {
        try {
            val apiService = RetrofitClient.movieService
            val response = apiService.getPopular(authorization)
            response
        } catch (e: Exception) {
            // Handle error
            MovieResponse(0, emptyList(), 0, 0)
        }
    }
}