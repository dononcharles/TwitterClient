package com.chaldrac.twitterclient.hashtag

import com.chaldrac.twitterclient.hashtag.events.HashtagEvent
import com.chaldrac.twitterclient.hashtag.ui.HashtagsView
import com.chaldrac.twitterclient.lib.base.EventBus
import org.greenrobot.eventbus.Subscribe

class HashtagsPresenterImpl(var view : HashtagsView?, var eventBus: EventBus, var interactor: HashtagsInteractor) : HashtagsPresenter {
    override fun onPause() {
        eventBus.unregister(this)
    }

    override fun onDestroy() {
        view = null
    }

    override fun onResume() {
        eventBus.register(this)
    }

    override fun getHashtagTweets() {
        if (view != null){
            view?.hideImages()
            view?.showProgress()
        }
        interactor.execute()
    }

    @Subscribe
    override fun onEventMainThread(event: HashtagEvent) {
        val errorMsg = event.error
        if (view != null){
            view?.showImages()
            view?.hideProgess()

            if (errorMsg != null){
                view?.onError(errorMsg)
            }else {
                view?.setContent(event.hashtags!!)
            }
        }
    }
}