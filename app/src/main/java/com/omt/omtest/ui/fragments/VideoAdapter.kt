package com.omt.omtest.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.omt.omtest.R
import com.omt.omtest.domain.Video

/**
 * Adapter class for recyclerView Videos
 */
class VideoAdapter constructor(private val listener: VideoClickListener) : ListAdapter<Video, VideoViewHolder>(VideoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return VideoViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_video, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        getItem(position).let { video ->
            holder.bind(video)
            holder.itemView.setOnClickListener { listener.onVideoClick(video.externalId) }
        }
    }

}

/**
 * Interface to propagate click events
 */
interface VideoClickListener {
    fun onVideoClick(externalID: String)
}