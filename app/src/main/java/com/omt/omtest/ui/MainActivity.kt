package com.omt.omtest.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.omt.omtest.R
import com.omt.omtest.db.RoomDB
import com.omt.omtest.network.Service
import com.omt.omtest.ui.fragments.allvideos.VideosFragment
import com.omt.omtest.utils.attachFragment
import com.omt.omtest.utils.getViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val initView =  this.supportFragmentManager.findFragmentByTag(VideosFragment.VIEW_FRAGMENT)
            ?: VideosFragment.newInstance()

        attachFragment(this.supportFragmentManager,R.id.fl_container,initView, VideosFragment.VIEW_FRAGMENT)

        getViewModel { MainSharedViewModel(buildRepository(this)) }
    }

    private fun buildRepository(context: Context) =
            SharedRepository(Service.request, Service.requestRecommended,
                    RoomDB.getDatabase(context).videoDAO())
}

