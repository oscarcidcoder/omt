package com.omt.omtest.ui.detailvideo


import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.omt.omtest.R
import com.omt.omtest.db.RoomDB
import com.omt.omtest.network.Service
import com.omt.omtest.ui.MainSharedViewModel
import com.omt.omtest.ui.SharedRepository
import com.omt.omtest.utils.getViewModel
import com.omt.omtest.utils.observe

class DetailVideoActivity : AppCompatActivity() {

    val externalID: String by lazy { intent.getStringExtra(EXTRA_EXTERNAL_ID) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_video)

        val viewModel = getViewModel { MainSharedViewModel(buildRepository(this)) }

        val viewModel2: MainSharedViewModel by viewModels()

        val prueba = viewModel2.getVideo2(externalID)
        Log.i("Detail", "onCreate: $prueba")

        observe(viewModel.getAllVideos) {
            it?.forEach {
                Log.i("TAG", "onCreate: ${it.id}")
            }
            val buscado = it?.find { it.externalId == externalID }
            Log.i("TAG", "onCreate: Buscado $buscado")
        }

    }

    private fun buildRepository(context: Context) =
            SharedRepository(Service.request, Service.requestRecommended,
                    RoomDB.getDatabase(context).videoDAO())

    companion object {

        private const val EXTRA_EXTERNAL_ID = "external_ID"

        fun callDetail(context: Context, externalID: String ) {
            val intent = Intent(context,DetailVideoActivity::class.java).apply {
                putExtra(EXTRA_EXTERNAL_ID, externalID)
            }
            context.startActivity(intent)
        }

    }


}