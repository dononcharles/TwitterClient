package com.chaldrac.twitterclient.images

import com.chaldrac.twitterclient.api.CustomTwitterApiClient
import com.chaldrac.twitterclient.db.Image
import com.chaldrac.twitterclient.images.events.ImagesEvent
import com.chaldrac.twitterclient.lib.base.EventBus
import com.twitter.sdk.android.core.models.Tweet
import retrofit2.Call
import retrofit2.Response
import java.util.*

class ImagesRepositoryImpl(var eventBus: EventBus, var customTwitterApiClient: CustomTwitterApiClient) : ImagesRepository {

    companion object {
        const val TWEET_COUNT = 100
    }

    override fun getImages() {
        val call = customTwitterApiClient.timeLineService.homeTimeline(TWEET_COUNT, true, true, true, true)
        call.enqueue(object : retrofit2.Callback<MutableList<Tweet>> {
            override fun onFailure(call: Call<MutableList<Tweet>>?, t: Throwable?) {
                post(t!!.localizedMessage)
            }

            override fun onResponse(call: Call<MutableList<Tweet>>?, response: Response<MutableList<Tweet>>?) {
                val items = ArrayList<Image>()
                for (tweet: Tweet in response!!.body()) {
                    if (containsImages(tweet)) {
                        val tweetModel = Image()

                        tweetModel.id = tweet.idStr
                        tweetModel.favoriteCount = tweet.favoriteCount

                        var tweetText = tweet.text
                        val index = tweetText.indexOf("http")
                        if (index > 0) {
                            tweetText = tweetText.substring(0, index)
                        }
                        tweetModel.tweetText = tweetText

                        val currentPhoto = tweet.entities.media[0]
                        val imageUrl = currentPhoto.mediaUrl
                        tweetModel.url = imageUrl

                        items.add(tweetModel)
                    }
                }
                Collections.sort(items, object : Comparator<Image> {
                    override fun compare(o1: Image?, o2: Image?): Int {
                        return o2?.favoriteCount!! - o1?.favoriteCount!!
                    }
                })

                post(items)
            }

        })
    }

    private fun containsImages(tweet: Tweet): Boolean {
        return tweet.entities != null && tweet.entities.media != null && !tweet.entities.media.isEmpty()
    }

    private fun post(items: MutableList<Image>?, error: String?) {
        val event = ImagesEvent()
        event.error = error
        event.images = items
        eventBus.post(event)
    }

    private fun post(error: String) {
        post(null, error)
    }

    private fun post(items: MutableList<Image>) {
        post(items, null)
    }
}