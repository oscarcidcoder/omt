package com.omt.omtest.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import androidx.fragment.app.Fragment
import com.omt.omtest.R
import com.omt.omtest.db.RoomDB
import com.omt.omtest.network.Service
import com.omt.omtest.ui.fragments.allvideos.VideosFragment
import com.omt.omtest.utils.attachFragment
import com.omt.omtest.utils.getViewModel

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    lateinit var viewModel: MainSharedViewModel
    private val listenerSearch: MutableSet<SearchHelper> = mutableSetOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val initView =  this.supportFragmentManager.findFragmentByTag(VideosFragment.VIEW_FRAGMENT)
            ?: VideosFragment.newInstance()

        viewModel = getViewModel { MainSharedViewModel(buildRepository(this)) }

        attachFragment(this.supportFragmentManager,R.id.fl_container,initView, VideosFragment.VIEW_FRAGMENT)

    }

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)
        if (fragment is SearchHelper) {
            listenerSearch.add(fragment)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search,menu)

        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(
                object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(p0: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(charecter: String?): Boolean {
                        listenerSearch.forEach {
                            it.doSearch(charecter)
                        }
                        return false
                    }

                }
        )

        return super.onCreateOptionsMenu(menu)
    }

    private fun buildRepository(context: Context) =
            SharedRepository(Service.request, Service.requestRecommended,
                    RoomDB.getDatabase(context).videoDAO())

    override fun onRestart() {
        super.onRestart()
        // Actualizar la lista de videos para ver los cambios
        viewModel.updateVideosState()
    }

    override fun onStop() {
        super.onStop()
        // Guardar la data
        viewModel.saveFavorites()
    }

    override fun onDestroy() {
        super.onDestroy()
        listenerSearch.clear()
    }

}

// CallBack para comunicacion con los fragmentos
interface SearchHelper {
    fun doSearch(search: String?)
}

