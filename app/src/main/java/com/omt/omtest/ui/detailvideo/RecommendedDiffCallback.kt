package com.omt.omtest.ui.detailvideo

import androidx.recyclerview.widget.DiffUtil
import com.omt.omtest.domain.RecommendedVideo

class RecommendedDiffCallback : DiffUtil.ItemCallback<RecommendedVideo>() {
    override fun areItemsTheSame(oldItem: RecommendedVideo, newItem: RecommendedVideo) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: RecommendedVideo, newItem: RecommendedVideo) = oldItem == newItem
}