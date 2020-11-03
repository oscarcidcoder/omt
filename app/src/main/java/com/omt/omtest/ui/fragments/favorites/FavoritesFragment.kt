package com.omt.omtest.ui.fragments.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.omt.omtest.R
import com.omt.omtest.ui.MainSharedViewModel
import com.omt.omtest.ui.fragments.VideoAdapter
import com.omt.omtest.ui.fragments.VideoClickListener
import com.omt.omtest.utils.observe
import kotlinx.android.synthetic.main.fragment_videos.view.*


/**
 * Favorite fragment videos
 */
class FavoritesFragment : Fragment(), VideoClickListener {

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
            adapter.submitList(it)
        })
        return viewRoot
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
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

    override fun onVideoClick(externalID: String) {
        Toast.makeText(requireContext(),"Video id $externalID selected",Toast.LENGTH_SHORT).show()
    }

    override fun onFavoriteClick(id:Int, externalID: String) {

    }
}