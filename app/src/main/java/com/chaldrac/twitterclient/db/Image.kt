package com.chaldrac.twitterclient.db

class Image {
    var id: String? = null
    var url: String? = null
    lateinit var tweetText: String
    var favoriteCount: Int = 0

    val tweeturl: String
        get() = BASE_TWEET_URL + this.id!!

    constructor(id: String, url: String, tweetText: String, favoriteCount: Int) {
        this.id = id
        this.url = url
        this.tweetText = tweetText
        this.favoriteCount = favoriteCount
    }

    constructor() {}

    companion object {
        const val BASE_TWEET_URL = "https://twitter.com/null/status/"
    }
}
