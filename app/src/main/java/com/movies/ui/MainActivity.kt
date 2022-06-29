package com.movies.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.movies.databinding.ActivityMainBinding
import com.movies.model.MainRepository
import com.movies.model.RetrofitService
import com.movies.model.Search
import com.movies.viewmodel.MainViewModel
import com.movies.viewmodel.MyViewModelFactory
class MainActivity : AppCompatActivity()
{
    lateinit var viewModel: MainViewModel
    private val adapter = MovieAdapter()
    lateinit var binding: ActivityMainBinding

    var newSearchList = mutableListOf<Search>()
    var searchList = mutableListOf<Search>()

    override fun onCreate(savedInstanceState: Bundle?)

    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val retrofitService = RetrofitService.getInstance()
        val mainRepository = MainRepository(retrofitService)
        binding.recyclerview.adapter = adapter



        binding.searchView.isActivated = true;
        binding.searchView.queryHint = "Type your movie name here";
        binding.searchView.onActionViewExpanded();
        binding.searchView.isSubmitButtonEnabled
        binding.searchView.isIconified=false;
        binding.searchView.clearFocus()



        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                adapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                adapter.filter.filter(newText)
                return false
            }
        })


        viewModel = ViewModelProvider(this, MyViewModelFactory(mainRepository)).get(MainViewModel::class.java)

        viewModel.movieList.observe(this) {
            adapter.setMovies(it)
            binding.progressDialog.visibility = View.GONE
        }



        viewModel.errorMessage.observe(this) {
            binding.progressDialog.visibility = View.GONE
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.loading.observe(this, Observer {
            if (it) {
                binding.progressDialog.visibility = View.VISIBLE
            } else {
                binding.progressDialog.visibility = View.GONE
            }
        })

        viewModel.getAllMovies()

    }

}


