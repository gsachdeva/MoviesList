package com.movies.model

import com.google.gson.annotations.SerializedName


data class Movie(
    @SerializedName("Search")
    val Search: List<Search>)

