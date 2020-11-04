package com.omt.omtest.ui.fragments.allvideos

import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [VideosFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class VideosFragment : Fragment(), VideoClickListener, SearchHelper {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val viewModel: MainSharedViewModel by activityViewModels()
    private val adapter: VideoAdapter by lazy { VideoAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewRoot = inflater.inflate(R.layout.fragment_videos, container, false)
        observe(viewModel.getAllVideos, {
            viewRoot.pb_loading.visibility = View.GONE
            //adapter.submitList(null)
            adapter.submitList(it)
        })

        observe(viewModel.updateAdapterData) {
            it?.first?.let { pos -> if (pos != -1) adapter.notifyItemChanged(pos) }
        }
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

        const val VIEW_FRAGMENT = "VideosList_Fragment"

        @JvmStatic
        fun newInstance(): Fragment = VideosFragment()
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment VideosFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            VideosFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onVideoClick(video: Video, position: Int) {
        viewModel.setVideoClickPosition(video.id,position)
        DetailVideoActivity.callDetail(requireContext(),video.externalId, video.isFavorite)
    }

    override fun onFavoriteClick(id:Int, externalID: String) {
        viewModel.setFavoriteVideo(id,externalID)
        Toast.makeText(requireContext(),"Video id $id -> $externalID favorite",Toast.LENGTH_SHORT).show()
    }

    override fun doSearch(search: String?) {
        adapter.filter.filter(search)
    }
}