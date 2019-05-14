package com.chaldrac.twitterclient.images.events

import com.chaldrac.twitterclient.db.Image

class ImagesEvent {
    var error: String? = null
    var images: MutableList<Image>? = null
}
