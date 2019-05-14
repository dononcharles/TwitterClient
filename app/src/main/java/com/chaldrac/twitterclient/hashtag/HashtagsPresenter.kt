package com.chaldrac.twitterclient.hashtag

import com.chaldrac.twitterclient.hashtag.events.HashtagEvent

interface HashtagsPresenter {
    fun onPause()
    fun onDestroy()
    fun onResume()
    fun getHashtagTweets()
    fun onEventMainThread(event : HashtagEvent)
}