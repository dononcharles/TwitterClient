package com.chaldrac.twitterclient.images.di;

import com.chaldrac.twitterclient.images.ImagesPresenter;
import com.chaldrac.twitterclient.images.ui.ImagesFragment;
import com.chaldrac.twitterclient.lib.di.LibsModule;
import dagger.Component;

import javax.inject.Singleton;

@Singleton @Component(modules = {LibsModule.class, ImagesModule.class})
public interface ImagesComponent {

   void inject(ImagesFragment fragment);
    ImagesPresenter getPresenter();

}
