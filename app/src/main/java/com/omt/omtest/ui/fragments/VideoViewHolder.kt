package com.omt.omtest.ui.fragments

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.omt.omtest.domain.Video
import com.omt.omtest.utils.imagePath
import com.omt.omtest.utils.loadUrl
import kotlinx.android.synthetic.main.item_video.view.*
import java.util.concurrent.TimeUnit

class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(video: Video) {
        itemView.apply {
            tv_videoID.text = video.id.toString()
            tv_videoDefinition.text = video.definition
            tv_videoDescription.text = video.description
            tv_videoName.text = video.name
            tv_videoDuration.text = TimeUnit.MILLISECONDS.toMinutes(video.duration).toString()
            tv_videoProvider.text = video.contentProvider
            tv_videoYear.text = video.year.toString()
            video.attachments.firstOrNull()?.let {
                iv_thumbnail.loadUrl(it.value.imagePath())
            }
        }
    }

}