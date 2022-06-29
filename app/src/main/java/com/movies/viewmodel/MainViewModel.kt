package com.movies.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.movies.model.MainRepository
import com.movies.model.Movie
import com.movies.model.Search

import kotlinx.coroutines.*


class MainViewModel constructor(private val mainRepository: MainRepository) : ViewModel() {

    val errorMessage = MutableLiveData<String>()
    val movieList = MutableLiveData<List<Search>>()
    var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()

    fun getAllMovies() {

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {


            loading.postValue(true)

            val response = mainRepository.getAllMovies()


            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    movieList.postValue(response.body()?.Search)



                } else {

                    onError("Error : ${response.toString()} ")
                }
            }
        }

    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}