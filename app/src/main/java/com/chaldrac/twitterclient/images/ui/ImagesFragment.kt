package com.chaldrac.twitterclient.images.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife

import com.chaldrac.twitterclient.R
import com.chaldrac.twitterclient.db.Image
import com.chaldrac.twitterclient.images.ImagesPresenter
import com.chaldrac.twitterclient.images.ui.adapter.ImagesAdapter
import com.google.android.material.snackbar.Snackbar
import butterknife.Unbinder
import com.chaldrac.twitterclient.TwitterClientApplication
import com.chaldrac.twitterclient.images.ui.adapter.OnItemClickListener
import javax.inject.Inject


class ImagesFragment : Fragment(), ImagesView, OnItemClickListener {

    private var unbinder: Unbinder? = null

    @BindView(R.id.recyclerView) lateinit var recyclerView: RecyclerView
    @BindView(R.id.progressBar) lateinit var progressBar: ProgressBar
    @BindView(R.id.container) lateinit var container: FrameLayout

    @Inject
    lateinit var presenter : ImagesPresenter

    @Inject
    lateinit var adapter : ImagesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_content, container, false)
        unbinder = ButterKnife.bind(this, view)

        setupInjection()
        setuprecyclerView()
        presenter.getImageTweets()
        return view
    }

    private fun setuprecyclerView() {
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = adapter
    }

    private fun setupInjection() {
        val application = activity?.application as TwitterClientApplication
        val imagesComponent = application.geImagesComponent(this, this, this)
        //adapter = imagesComponent.getPresenter()
        imagesComponent.inject(this)
    }

    companion object {

        @JvmStatic
        fun newInstance() = ImagesFragment()
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

    override fun setContent(items: MutableList<Image>) {
        adapter.setItems(items)
    }

    override fun onItemClick(image: Image){
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(image.tweeturl)))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder?.unbind()
    }
}
