package com.example.hasham.movies_mvvm.util

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hasham.movies_mvvm.BR
import com.example.hasham.movies_mvvm.R
import com.example.hasham.movies_mvvm.data.models.Movie
import com.example.hasham.movies_mvvm.ui.movies.MovieViewModel

/**
 * Created by Khurram on 22-Nov-17.
 */
class RecyclerCustomAdapter() : RecyclerView.Adapter<RecyclerCustomAdapter.BindingHolder>() {

    var movieList: ArrayList<Movie> = ArrayList()


    fun addMovies(movies: List<Movie>?) {

        movieList.addAll(movies as List<Movie>)

        //    notifyItemRangeInserted(0, movieList.size)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerCustomAdapter.BindingHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_movie, parent, false)
        return RecyclerCustomAdapter.BindingHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerCustomAdapter.BindingHolder, position: Int) {
        val item = movieList[position]

        holder.binding.setVariable(BR.movie, item)
    }

    override fun getItemCount(): Int {
        return if (false) 0 else movieList.size
    }

    class BindingHolder(v: View) : RecyclerView.ViewHolder(v) {
        val binding: ViewDataBinding = DataBindingUtil.bind(v)
    }
}