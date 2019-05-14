package com.chaldrac.twitterclient.hashtag

import com.chaldrac.twitterclient.api.CustomTwitterApiClient
import com.chaldrac.twitterclient.db.HashsTag
import com.chaldrac.twitterclient.hashtag.events.HashtagEvent
import com.chaldrac.twitterclient.lib.base.EventBus
import com.twitter.sdk.android.core.models.HashtagEntity
import com.twitter.sdk.android.core.models.Tweet
import retrofit2.Call
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class HashtagsRepositoryImpl(var eventBus: EventBus, var customTwitterApiClient: CustomTwitterApiClient) : HashtagsRepository {

    companion object {
        const val HASHTAG_TWEET = 100
    }

    override fun getHashtags() {
        val call = customTwitterApiClient.timeLineService.homeTimeline(HASHTAG_TWEET, true, true, true, true)
        call.enqueue(object : retrofit2.Callback<MutableList<Tweet>> {
            override fun onFailure(call: Call<MutableList<Tweet>>?, t: Throwable?) {
                post(t!!.localizedMessage)
            }

            override fun onResponse(call: Call<MutableList<Tweet>>?, response: Response<MutableList<Tweet>>?) {
                val items = ArrayList<HashsTag>()
                for (tweet: Tweet in response!!.body()) {
                    if (containsHashtags(tweet)) {
                        val tweetModel = HashsTag()

                        tweetModel.id = tweet.idStr
                        tweetModel.favoriteCount = tweet.favoriteCount
                        val tweetText = tweet.text
                        tweetModel.tweetText = tweetText

                        val hashsTags = ArrayList<String>()
                        for (hashtag : HashtagEntity in tweet.entities.hashtags){
                            hashsTags.add(hashtag.text)
                        }
                        tweetModel.hashtags = hashsTags

                        items.add(tweetModel)
                    }
                }
                Collections.sort(items, object : Comparator<HashsTag> {
                    override fun compare(o1: HashsTag?, o2: HashsTag?): Int {
                        return o2?.favoriteCount!! - o1?.favoriteCount!!
                    }
                })

                post(items)
            }

        })
    }

    private fun containsHashtags(tweet: Tweet): Boolean {
        return tweet.entities != null && tweet.entities.hashtags != null && !tweet.entities.hashtags.isEmpty()
    }

    private fun post(hashtags : ArrayList<HashsTag>?, error :String?){
        val event = HashtagEvent()
        event.error = error
        event.hashtags = hashtags
        eventBus.post(event)
    }

    private fun post(hashtags : ArrayList<HashsTag>){
        post(hashtags, null)
    }

    private fun post(error :String?){
        post(null, error)
    }
}