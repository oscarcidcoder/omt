package com.omt.omtest.ui.detailvideo

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.omt.omtest.R
import com.omt.omtest.domain.RecommendedVideo
import com.omt.omtest.utils.imagePath
import com.omt.omtest.utils.loadUrl
import kotlinx.android.synthetic.main.item_video.view.*
import java.text.SimpleDateFormat
import java.util.*

class RecommendedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(video: RecommendedVideo) {
        itemView.apply {
            tv_videoID.text = context.getString(R.string.videoid).plus(video.id.toString())
            tv_videoDescription.visibility = View.GONE
            tv_videoName.text = context.getString(R.string.videoname).plus(video.name)
            tv_videoDuration.text = context.getString(R.string.rating).plus(video.rating)
            tv_videoYear.text = context.getString(R.string.ratingCount).plus(video.ratersCount)

            val timeEnd = video.availabilities.getOrNull(0)?.endTime
            if (timeEnd != null) {
                val date = Date(timeEnd)
                val formatter = SimpleDateFormat("d MMM yyyy", Locale.getDefault())
                tv_videoDefinition.text = context.getString(R.string.end).plus(formatter.format(date))
            } else
                tv_videoDefinition.visibility = View.GONE

            tv_videoProvider.text = context.getString(R.string.genre)
                    .plus(video.genres.randomOrNull()?.name ?: context.getString(android.R.string.unknownName))

            video.images.randomOrNull()?.let {
                iv_thumbnail.loadUrl(it.value.imagePath())
            }

            cb_favorite.visibility = View.GONE
        }
    }

}