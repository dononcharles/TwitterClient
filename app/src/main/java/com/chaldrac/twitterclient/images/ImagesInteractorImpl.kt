package com.chaldrac.twitterclient.images

class ImagesInteractorImpl(var repository: ImagesRepository) : ImagesInteractor {

    override fun execute() {
        repository.getImages()
    }

}