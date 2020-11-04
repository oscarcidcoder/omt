package com.omt.omtest.ui.fragments

import androidx.recyclerview.widget.DiffUtil
import com.omt.omtest.domain.Video

class VideoDiffCallback : DiffUtil.ItemCallback<Video>() {
    override fun areItemsTheSame(oldItem: Video, newItem: Video) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
        return oldItem == newItem && oldItem.isFavorite == newItem.isFavorite
    }
}