package com.omt.omtest.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.omt.omtest.R
import com.omt.omtest.db.RoomDB
import com.omt.omtest.network.Service
import com.omt.omtest.utils.getViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainSharedViewModel by lazy { getViewModel { MainSharedViewModel(
        buildRepository(this)) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}

private fun buildRepository(context: Context) =
    SharedRepository(Service.request, Service.requestRecommended,
        RoomDB.getDatabase(context).videoDAO())