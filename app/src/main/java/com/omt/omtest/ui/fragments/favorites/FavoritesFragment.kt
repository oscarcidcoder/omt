package com.omt.omtest.ui.fragments.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.omt.omtest.R
import com.omt.omtest.domain.Video
import com.omt.omtest.ui.MainSharedViewModel
import com.omt.omtest.ui.SearchHelper
import com.omt.omtest.ui.detailvideo.DetailVideoActivity
import com.omt.omtest.ui.fragments.VideoAdapter
import com.omt.omtest.ui.fragments.VideoClickListener
import com.omt.omtest.utils.observe
import kotlinx.android.synthetic.main.fragment_videos.view.*


/**
 * Favorite fragment videos
 */
class FavoritesFragment : Fragment(), VideoClickListener, SearchHelper {

    private val viewModel: MainSharedViewModel by activityViewModels()
    private val adapter: VideoAdapter by lazy { VideoAdapter(this, true) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewRoot = inflater.inflate(R.layout.fragment_videos, container, false)
        viewRoot.tv_nameFrame.text = getString(R.string.nameFrameFav)

        observe(viewModel.getFavoritesVideos, {
            viewRoot.pb_loading.visibility = View.GONE
            adapter.submitList(it as List<Video>?)
        })

        return viewRoot
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observe(viewModel.updateAdapterData) {
            it?.second?.let { pos -> if (pos != -1) adapter.notifyItemChanged(pos) }
        }

    }

    private fun setupRecyclerView() {
        view?.rv_videos?.layoutManager = LinearLayoutManager(requireContext())
        view?.rv_videos?.setHasFixedSize(true)
        view?.rv_videos?.adapter = adapter
    }

    companion object {

        const val VIEW_FRAGMENT = "VideosFavorites_Fragment"

        @JvmStatic
        fun newInstance(): Fragment = FavoritesFragment()

    }

    override fun onVideoClick(video: Video, position: Int) {
        viewModel.setVideoClickPosition(video.id,position)
        DetailVideoActivity.callDetail(requireContext(),video.externalId, video.isFavorite)
    }

    override fun onFavoriteClick(id:Int, externalID: String) {
        // NA
    }

    override fun doSearch(search: String?) {
        adapter.filter.filter(search)
    }
}