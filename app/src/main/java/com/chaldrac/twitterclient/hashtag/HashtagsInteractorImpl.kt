package com.chaldrac.twitterclient.hashtag

class HashtagsInteractorImpl(var hashtagsRepository: HashtagsRepository) : HashtagsInteractor {
    override fun execute() {
        hashtagsRepository.getHashtags()
    }
}