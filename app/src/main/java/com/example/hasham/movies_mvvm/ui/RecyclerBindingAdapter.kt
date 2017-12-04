package com.example.hasham.movies_mvvm.ui

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hasham.movies_mvvm.data.models.Movie

import java.util.AbstractList
import java.util.ArrayList

/**
 * Developed by Hasham.Tahir on 10/17/2016.
 */

class RecyclerBindingAdapter<T>(private val holderLayout: Int, private val variableId: Int) : RecyclerView.Adapter<RecyclerBindingAdapter.BindingHolder>() {
    private var items: AbstractList<T> = ArrayList()
    private var onItemClickListener: OnItemClickListener<T>? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerBindingAdapter.BindingHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(holderLayout, parent, false)
        return BindingHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerBindingAdapter.BindingHolder, position: Int) {
        val item = items[position]
        holder.binding.root.setOnClickListener {
            if (onItemClickListener != null)
                onItemClickListener!!.onItemClick(position, item)
        }

        holder.binding.setVariable(variableId, item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener<T>) {
        this.onItemClickListener = onItemClickListener
    }

    fun updateList(list: AbstractList<T>) {
        this.items = list
        notifyDataSetChanged()
    }

    fun addItems(list: AbstractList<T>) {

        items.addAll(list)
        notifyDataSetChanged()
    }

    interface OnItemClickListener<T> {
        fun onItemClick(position: Int, item: T)
    }

    class BindingHolder(v: View) : RecyclerView.ViewHolder(v) {
        val binding: ViewDataBinding = DataBindingUtil.bind(v)

    }
}
