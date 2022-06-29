package com.movies.util

import com.movies.model.Movie
import com.movies.model.Search

object ValidationUtil {

        fun validateMovie(movie: Search) : Boolean {
            if (movie.Type.isNotEmpty()) {
                return true
            }
            return false
        }
}