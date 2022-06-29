package com.movies.model

import com.google.gson.annotations.SerializedName


data class Search(
    @SerializedName("Title")

    val Title: String,

    @SerializedName("Poster")

    val Poster: String,

    @SerializedName("Type")
    val Type: String)


