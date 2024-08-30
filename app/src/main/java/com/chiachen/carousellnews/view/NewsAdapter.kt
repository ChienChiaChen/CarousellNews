package com.chiachen.carousellnews.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.chiachen.carousellnews.databinding.ItemNewsBinding
import com.chiachen.presentation.model.NewsPresentationModel

class NewsAdapter : ListAdapter<NewsPresentationModel, NewsViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

private val DIFF_UTIL = object : DiffUtil.ItemCallback<NewsPresentationModel>() {
    override fun areItemsTheSame(oldItem: NewsPresentationModel, newItem: NewsPresentationModel) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: NewsPresentationModel,
        newItem: NewsPresentationModel
    ) = oldItem == newItem
}