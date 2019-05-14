package com.chaldrac.twitterclient.images

import com.chaldrac.twitterclient.images.events.ImagesEvent

interface ImagesPresenter {
    fun onResult()
    fun onPause()
    fun onDestroy()
    fun onResume()
    fun getImageTweets()
    fun onEventMainThread(event : ImagesEvent)
}