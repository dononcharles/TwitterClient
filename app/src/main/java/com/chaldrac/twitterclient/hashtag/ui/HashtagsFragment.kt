package com.chaldrac.twitterclient.hashtag.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.chaldrac.twitterclient.R
import com.chaldrac.twitterclient.TwitterClientApplication
import com.chaldrac.twitterclient.db.HashsTag
import com.chaldrac.twitterclient.hashtag.HashtagsPresenter
import com.chaldrac.twitterclient.hashtag.adapters.HashtagsAdapter
import com.chaldrac.twitterclient.hashtag.adapters.OnItemClickListener
import com.google.android.material.snackbar.Snackbar
import java.util.ArrayList
import javax.inject.Inject


class HashtagsFragment : Fragment(), HashtagsView, OnItemClickListener {
    private lateinit var unbinder: Unbinder

    @BindView(R.id.recyclerView)
    lateinit var recyclerView: RecyclerView
    @BindView(R.id.progressBar)
    lateinit var progressBar: ProgressBar
    @BindView(R.id.container)
    lateinit var container: FrameLayout


    @Inject
    lateinit var presenter: HashtagsPresenter

    @Inject
    lateinit var adapter: HashtagsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_content, container, false)

        unbinder = ButterKnife.bind(this, v)
        injectComponent()
        presenter.getHashtagTweets()
        setuprecyclerView()

        return v
    }

    private fun setuprecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    private fun injectComponent() {
        val application = activity?.application as TwitterClientApplication
        val component = application.geHashtagsComponent(this, this, this)
        component.inject(this)
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onPause() {
        presenter.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    companion object {

        @JvmStatic
        fun newInstance() = HashtagsFragment()
    }

    override fun showImages() {
        recyclerView.visibility = View.VISIBLE
    }

    override fun hideImages() {
        recyclerView.visibility = View.GONE
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgess() {
        progressBar.visibility = View.GONE
    }

    override fun onError(error: String) {
        Snackbar.make(container, error, Snackbar.LENGTH_LONG).show()
    }

    override fun setContent(items: ArrayList<HashsTag>) {
        adapter.setDataset(items)
    }

    override fun onClickItem(hashsTag: HashsTag) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(hashsTag.tweeturl)))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder.unbind()
    }
}
