package com.chaldrac.twitterclient.hashtag.adapters

import com.chaldrac.twitterclient.db.HashsTag

interface OnItemClickListener {
    fun onClickItem(hashsTag: HashsTag)
}
