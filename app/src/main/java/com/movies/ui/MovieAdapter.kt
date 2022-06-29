package com.movies.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.movies.databinding.AdapterMovieBinding
import com.movies.model.Search
import com.movies.util.ValidationUtil
import java.util.ArrayList

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MainViewHolder>(),Filterable {

    var searchList = mutableListOf<Search>()
     var searchFilterList=mutableListOf<Search>()
    var mRecyclerView: RecyclerView? = null
    var newList=mutableListOf<Search>()

    fun setMovies(search: List<Search>)
    {

        this.searchList = search.toMutableList()
        newList.addAll(searchList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder
    {

        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterMovieBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
    }


    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

        val movie = searchList[position]
        if (ValidationUtil.validateMovie(movie)) {
            holder.binding.name.text = movie.Title
            Glide.with(holder.itemView.context).load(movie.Poster).into(holder.binding.imageview)
        }
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    class MainViewHolder(val binding: AdapterMovieBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun getFilter(): Filter
    {
        return object : Filter()
        {
            override fun performFiltering(charSequence: CharSequence): FilterResults
            {

                val charString = charSequence.toString()
                if (charString.isEmpty())
                {
                    searchList.clear()
                    searchList.addAll(newList)


                } else {
                    val filteredList: MutableList<Search> = ArrayList()
                    for (movie in searchList)
                    {
                        if (movie.Title.lowercase().contains(charString.lowercase()))
                        {
                            filteredList.add(movie)
                        }
                    }
                    searchFilterList = filteredList

                }
                val filterResults = FilterResults()
               filterResults.values = searchFilterList

                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults)
            {

                searchList=filterResults.values as MutableList<Search>

                notifyDataSetChanged();



        }
    }

}}




