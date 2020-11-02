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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [VideosFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoritesFragment : Fragment(), VideoClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val viewModel: MainSharedViewModel by activityViewModels()
    private val adapter: VideoAdapter by lazy { VideoAdapter(this, true) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewRoot = inflater.inflate(R.layout.fragment_videos, container, false)
        observe(viewModel.getFavoritesVideos(), {
            viewRoot.pb_loading.visibility = View.GONE
            adapter.submitList(it)
        })
        viewModel.getVideos()
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