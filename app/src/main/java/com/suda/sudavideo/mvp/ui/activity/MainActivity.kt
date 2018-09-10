package com.suda.sudavideo.mvp.ui.activity

import android.app.ProgressDialog
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.suda.sudavideo.R
import com.suda.sudavideo.di.component.DaggerMainComponent
import com.suda.sudavideo.di.moudle.MainModule
import com.suda.sudavideo.mvp.contract.MainContract
import com.suda.sudavideo.mvp.model.entity.Source
import com.suda.sudavideo.mvp.model.entity.Video
import com.suda.sudavideo.mvp.presenter.MainPresenter
import com.suda.sudavideo.mvp.ui.adapter.SearchVideoAdapter
import com.suda.sudavideo.mvp.ui.adapter.VideoPageAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : BaseActivity<MainPresenter>(), MainContract.View, NavigationView.OnNavigationItemSelectedListener {

    private val progressDialog: ProgressDialog by lazy {
        ProgressDialog(this@MainActivity)
    }

    private lateinit var searchView: SearchView

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerMainComponent.builder()
                .appComponent(appComponent)
                .mainModule(MainModule(this as MainContract.View))
                .build()
                .inject(this)
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main
    }

    override fun initData(savedInstanceState: Bundle?) {
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)


        ry_search.layoutManager = LinearLayoutManager(this)


        intent.putExtra("isInitToolbar", true)
        mPresenter?.getSources()
    }

    override fun onStop() {
        super.onStop()
        if (!searchView.isIconified) {
            searchView.setQuery("",false)
            searchView.setIconified(true)
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else if (!searchView.isIconified) {
            searchView.setQuery("",false)
            searchView.setIconified(true)
        } else {
            super.onBackPressed()
        }
    }

    override fun showShowSources(sources: List<Source>) {
        main_vp.adapter = VideoPageAdapter(supportFragmentManager, this, sources)
        main_tab_layout.tabMode = TabLayout.MODE_SCROLLABLE
        main_tab_layout.setupWithViewPager(main_vp)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnCloseListener {
            ry_search.visibility = View.GONE
            return@setOnCloseListener false
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null && newText != "") {
                    mPresenter?.searchVideoByName(newText)
                }else{
                    ry_search.visibility = View.GONE
                }
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {

                return true
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.search -> {
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun showSearchVideos(videos: List<Video>) {
        ry_search.visibility = if (videos.size > 0) {
            View.VISIBLE
        } else {
            View.GONE
        }
        ry_search.adapter = SearchVideoAdapter(videos)
    }

    override fun showLoading() {
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog.setMessage(getString(R.string.loading))
//        progressDialog.setCancelable(false)
        progressDialog.show()

    }

    override fun hideLoading() {
        progressDialog.cancel()
    }

    override fun showMessage(message: String) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun launchActivity(intent: Intent) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun killMyself() {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
