package com.omt.omtest.ui.fragments

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.omt.omtest.R
import com.omt.omtest.domain.Video
import com.omt.omtest.utils.imagePath
import com.omt.omtest.utils.loadUrl
import kotlinx.android.synthetic.main.item_video.view.*
import java.util.concurrent.TimeUnit

class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(video: Video, listener: VideoClickListener, isFavoriteList: Boolean) {
        itemView.apply {
            tv_videoID.text = context.getString(R.string.videoid).plus(video.id.toString())
            tv_videoDefinition.text = context.getString(R.string.videodefinition).plus(video.definition)
            tv_videoDescription.text = context.getString(R.string.videodescription).plus(video.description)
            tv_videoName.text = context.getString(R.string.videoname).plus(video.name)
            val duration = context.getString(R.string.videoduration)
                    .plus(TimeUnit.MILLISECONDS.toMinutes(video.duration).toString().plus(" min"))
            tv_videoDuration.text = duration
            tv_videoProvider.text = context.getString(R.string.videoprovider).plus(video.contentProvider)
            tv_videoYear.text = context.getString(R.string.videoyear).plus(video.year)
            video.attachments.randomOrNull()?.let {
                iv_thumbnail.loadUrl(it.value.imagePath())
            }

            if (isFavoriteList) {
                cb_favorite.visibility = View.GONE
            } else {
                cb_favorite.isChecked = video.isFavorite
                cb_favorite.setOnClickListener { listener.onFavoriteClick(video.id,video.externalId) }
            }
        }
    }

}