package com.chaldrac.twitterclient.main.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import butterknife.BindView
import butterknife.ButterKnife
import com.chaldrac.twitterclient.login.LoginActivity
import com.chaldrac.twitterclient.R
import com.chaldrac.twitterclient.hashtag.ui.HashtagsFragment
import com.chaldrac.twitterclient.images.ui.ImagesFragment
import com.chaldrac.twitterclient.main.adapter.MainSectionsPageAdapter
import com.google.android.material.tabs.TabLayout
import com.twitter.sdk.android.core.TwitterCore

class MainActivity : AppCompatActivity() {

    @BindView(R.id.toolbar) lateinit var toolbar: Toolbar
    @BindView(R.id.tabs) lateinit var tabs: TabLayout
    @BindView(R.id.container) lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        setSupportActionBar(toolbar)

        setupAdapter()
    }

    private fun setupAdapter() {
        val fragments = arrayOf(ImagesFragment(), HashtagsFragment())
        val titles = arrayOf(getString(R.string.main_header_images), getString(R.string.main_header_hashtags))
        val adapter = MainSectionsPageAdapter(supportFragmentManager, titles, fragments)

        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val itemId = item?.itemId
        if (itemId == R.id.actionLogout){
            TwitterCore.getInstance().sessionManager.clearActiveSession()
            logout()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun logout() {
        startActivity(
            Intent(this,
                LoginActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
    }

}
