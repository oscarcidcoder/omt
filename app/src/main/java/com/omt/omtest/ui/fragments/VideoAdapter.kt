package com.omt.omtest.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.ListAdapter
import com.omt.omtest.R
import com.omt.omtest.domain.Video

/**
 * Adapter class for recyclerView Videos
 */
class VideoAdapter constructor(private val listener: VideoClickListener,
                               private val isFavorites: Boolean = false) :
        ListAdapter<Video, VideoViewHolder>(VideoDiffCallback()), Filterable {

    private var unfilteredList: MutableList<Video>? = mutableListOf()

    override fun submitList(list: List<Video>?) {
        unfilteredList = list?.toMutableList()
        super.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return VideoViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_video, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        getItem(position).let { video ->
            holder.bind(video,listener,isFavorites)
            holder.itemView.setOnClickListener { listener.onVideoClick(video, position) }
        }
    }

    private fun sendData(list: List<Video>?) {
        super.submitList(list)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filter = constraint.toString().toLowerCase()
                val listFiltered = mutableListOf<Video>()

                if (filter.isNotEmpty()) {
                    val ver = unfilteredList?.filter { it.name.toLowerCase().contains(filter) }
                    if (ver != null) {
                        listFiltered.addAll(ver)
                    }
                } else {
                    unfilteredList?.let { listFiltered.addAll(it) }
                }

                val filterResults = FilterResults()
                filterResults.values = listFiltered
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                sendData(results?.values as List<Video>?)
            }
        }
    }

}

/**
 * Interface to propagate click events
 */
interface VideoClickListener {
    fun onVideoClick(video: Video, position: Int)
    fun onFavoriteClick(id:Int, externalID: String)
}