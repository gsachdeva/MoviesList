package com.movies.model



class MainRepository constructor(private val retrofitService: RetrofitService) {

    suspend fun getAllMovies() = retrofitService.getAllMovies()

}