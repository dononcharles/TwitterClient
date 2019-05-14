package com.chaldrac.twitterclient.hashtag.ui

import com.chaldrac.twitterclient.db.HashsTag

interface HashtagsView {
    fun showImages()
    fun hideImages()
    fun showProgress()
    fun hideProgess()

    fun onError(error:String)
    fun setContent(items : ArrayList<HashsTag>)
}