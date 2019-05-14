package com.chaldrac.twitterclient.lib.di;

import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.chaldrac.twitterclient.lib.GlideImageLoader;
import com.chaldrac.twitterclient.lib.GreenRobotEventBus;
import com.chaldrac.twitterclient.lib.base.EventBus;
import com.chaldrac.twitterclient.lib.base.ImageLoader;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class LibsModule {

    private Fragment fragment;

    public LibsModule(Fragment fragment){
        this.fragment = fragment;
    }

    @Singleton
    @Provides
    EventBus providesEventBus(org.greenrobot.eventbus.EventBus eventBus){
        return new GreenRobotEventBus(eventBus);
    }

    @Singleton
    @Provides
    org.greenrobot.eventbus.EventBus providesLibraryEventBus(){
        return org.greenrobot.eventbus.EventBus.getDefault();
    }

    @Singleton
    @Provides
    ImageLoader providesImageLoader(RequestManager requestManager){
        return new GlideImageLoader(requestManager);
    }

    @Singleton
    @Provides
    Fragment providesFragment(){
        return this.fragment;
    }

    @Singleton
    @Provides
    RequestManager providesRequestManager(){
        return Glide.with(fragment);
    }
}
