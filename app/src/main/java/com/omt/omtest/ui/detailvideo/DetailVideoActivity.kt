package com.omt.omtest.ui.detailvideo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.omt.omtest.R
import com.omt.omtest.db.RoomDB
import com.omt.omtest.network.Service
import com.omt.omtest.ui.SharedRepository
import com.omt.omtest.utils.*
import kotlinx.android.synthetic.main.activity_detail_video.*
import java.util.concurrent.TimeUnit

class DetailVideoActivity : AppCompatActivity() {

    private val externalID: String by lazy { intent.getStringExtra(EXTRA_EXTERNAL_ID) }
    private val isFavorite: Boolean by lazy { intent.getBooleanExtra(EXTRA_FAVORITE, false) }
    private val adapter: RecommendedAdapter by lazy { RecommendedAdapter() }
    private val viewModel: DetailViewModel by lazy { getViewModel { DetailViewModel(externalID, isFavorite,buildRepository(this)) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_video)

        setupRecyclerView()

        observe(viewModel.getVideo) {video ->
            Log.i("Detail", "onCreate: UPDATE LiveData VIDEO")
            tv_name.text = video?.name
            video?.attachments?.getOrNull(0)?.let {
                iv_thumbnail.loadUrl(it.value.imagePath())
            }

            tv_description.text = video?.description
            tv_id.text = video?.id.toString()
            tv_year.text = video?.year.toString()
            val duration = getString(R.string.videoduration)
                    .plus(TimeUnit.MILLISECONDS.toMinutes(video?.duration!!).toString().plus(" min"))
            tv_duration.text = duration

            cb_favorite.isChecked = video.isFavorite
            cb_favorite.setOnClickListener {
                 viewModel.setFavoriteState()
            }
        }

        observe(viewModel.getRecommended) {
            adapter.submitList(it)
        }

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item)
    }


    private fun setupRecyclerView() {
        rv_recommended.layoutManager = LinearLayoutManager(this)
        rv_recommended.setHasFixedSize(true)
        rv_recommended.adapter = adapter
    }

    private fun buildRepository(context: Context) =
            SharedRepository(Service.request, Service.requestRecommended,
                    RoomDB.getDatabase(context).videoDAO())

    companion object {

        private const val EXTRA_EXTERNAL_ID = "external_ID"
        private const val EXTRA_FAVORITE = "video_favorite"
        const val REQUEST_POSITION = 3654

        fun callDetail(context: Context, externalID: String, isFavorite: Boolean) {
            val intent = Intent(context,DetailVideoActivity::class.java).apply {
                putExtra(EXTRA_EXTERNAL_ID, externalID)
                putExtra(EXTRA_FAVORITE, isFavorite)
            }
            context.startActivity(intent)
            //(context as FragmentActivity).startActivityForResult(intent,REQUEST_POSITION)
        }

    }

}