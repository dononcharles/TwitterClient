package com.chaldrac.twitterclient.images.ui

import com.chaldrac.twitterclient.db.Image


interface ImagesView {
    fun showImages()
    fun hideImages()
    fun showProgress()
    fun hideProgess()

    fun onError(error:String)
    fun setContent(items : MutableList<Image>)
}