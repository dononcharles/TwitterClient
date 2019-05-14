package com.chaldrac.twitterclient.images.di;

import com.chaldrac.twitterclient.api.CustomTwitterApiClient;
import com.chaldrac.twitterclient.db.Image;
import com.chaldrac.twitterclient.images.*;
import com.chaldrac.twitterclient.images.ui.ImagesView;
import com.chaldrac.twitterclient.images.ui.adapter.ImagesAdapter;
import com.chaldrac.twitterclient.images.ui.adapter.OnItemClickListener;
import com.chaldrac.twitterclient.lib.base.EventBus;
import com.chaldrac.twitterclient.lib.base.ImageLoader;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Module
public class ImagesModule {

    private ImagesView view;
    private OnItemClickListener clickListener;

    public ImagesModule(ImagesView view, OnItemClickListener clickListener) {
        this.view = view;
        this.clickListener = clickListener;
    }

    @Provides
    @Singleton
    ImagesAdapter providesAdapter(List<Image> dataset, ImageLoader imageLoader, OnItemClickListener clickListener1){
        return new ImagesAdapter(dataset, imageLoader, clickListener1);
    }

    @Provides
    @Singleton
    OnItemClickListener providesOnItemClickListener(){
        return this.clickListener;
    }

    @Provides
    @Singleton
    List<Image> providesItemsList(){
        return new ArrayList<>();
    }

    @Provides
    @Singleton
    ImagesPresenter providesImagesPresenter(ImagesView view, EventBus eventBus, ImagesInteractor interactor){
        return new ImagesPresenterImpl(view, eventBus, interactor);
    }

    @Provides
    @Singleton
    ImagesView providesImagesView(){
        return this.view;
    }

    @Provides
    @Singleton
    ImagesInteractor providesImagesInteractor(ImagesRepository imagesRepository){
        return new ImagesInteractorImpl(imagesRepository);
    }

    @Provides
    @Singleton
    ImagesRepository providesImagesRepository(EventBus eventBus, CustomTwitterApiClient twitterApiClient){
        return new ImagesRepositoryImpl(eventBus, twitterApiClient);
    }

    @Provides
    @Singleton
    CustomTwitterApiClient providesCustomTwitterApiClient(TwitterSession twitterSession){
        return new CustomTwitterApiClient(twitterSession);
    }

    @Provides
    @Singleton
    TwitterSession providesTwitterSession(){
        return TwitterCore.getInstance().getSessionManager().getActiveSession();
    }
}
