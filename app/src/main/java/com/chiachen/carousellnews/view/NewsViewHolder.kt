package com.chiachen.carousellnews.view

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.chiachen.carousellnews.databinding.ItemNewsBinding
import com.chiachen.carousellnews.utils.DateFormat
import com.chiachen.presentation.model.NewsPresentationModel


class NewsViewHolder(
    private val binding: ItemNewsBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: NewsPresentationModel) {
        binding.banner
        binding.txtTitle.text = item.title
        binding.txtDescription.text = item.description
        binding.txtCreatedAt.text = DateFormat.format(itemView.context, item.timeCreated)
        binding.banner.load(item.bannerUrl)
    }
}
