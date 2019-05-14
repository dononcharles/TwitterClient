package com.chaldrac.twitterclient.images

import com.chaldrac.twitterclient.images.events.ImagesEvent
import com.chaldrac.twitterclient.images.ui.ImagesView
import com.chaldrac.twitterclient.lib.base.EventBus
import org.greenrobot.eventbus.Subscribe

class ImagesPresenterImpl(var view : ImagesView?, var eventBus: EventBus, var interactor: ImagesInteractor) : ImagesPresenter {

    override fun onResult() {}

    override fun onPause() {
        eventBus.unregister(this)
    }

    override fun onDestroy() {
        view = null
    }

    override fun onResume() {
        eventBus.register(this)
    }

    override fun getImageTweets() {
        if (view != null){
            view?.hideImages()
            view?.showProgress()
        }
        interactor.execute()
    }

    @Subscribe override fun onEventMainThread(event: ImagesEvent) {
        val errorMsg = event.error
        if (view != null){
            view?.showImages()
            view?.hideProgess()

            if (errorMsg != null){
                view?.onError(errorMsg)
            }else {
                view?.setContent(event.images!!)
            }
        }
    }
}