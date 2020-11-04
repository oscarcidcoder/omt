package com.omt.omtest.ui.detailvideo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.omt.omtest.R
import com.omt.omtest.domain.RecommendedVideo

/**
 * Adapter class for recyclerView Recommended
 */
class RecommendedAdapter : ListAdapter<RecommendedVideo, RecommendedViewHolder>(RecommendedDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendedViewHolder {
        return RecommendedViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_video, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecommendedViewHolder, position: Int) {
        getItem(position).let { video ->
            holder.bind(video)
        }
    }

}