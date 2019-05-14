package com.chaldrac.twitterclient.db

class HashsTag {

    var id: String? = null
    var url: String? = null
    lateinit var tweetText: String
    var favoriteCount: Int = 0
    lateinit var hashtags: MutableList<String>

    val tweeturl: String
        get() = BASE_TWEET_URL + this.id!!


    companion object {
        const val BASE_TWEET_URL = "https://twitter.com/null/status/"
    }
}
