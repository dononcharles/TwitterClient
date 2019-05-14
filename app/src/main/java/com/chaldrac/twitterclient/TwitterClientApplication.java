package com.chaldrac.twitterclient;

import android.app.Application;
import androidx.fragment.app.Fragment;
import com.chaldrac.twitterclient.hashtag.di.DaggerHashtagsComponent;
import com.chaldrac.twitterclient.hashtag.di.HashtagsComponent;
import com.chaldrac.twitterclient.hashtag.di.HashtagsModule;
import com.chaldrac.twitterclient.hashtag.ui.HashtagsView;
import com.chaldrac.twitterclient.images.di.DaggerImagesComponent;
import com.chaldrac.twitterclient.images.di.ImagesComponent;
import com.chaldrac.twitterclient.images.di.ImagesModule;
import com.chaldrac.twitterclient.images.ui.ImagesView;
import com.chaldrac.twitterclient.images.ui.adapter.OnItemClickListener;
import com.chaldrac.twitterclient.lib.di.LibsModule;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

public class TwitterClientApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initTwitter();
    }

    private void initTwitter() {
        TwitterConfig config = new TwitterConfig.Builder(this)
                .twitterAuthConfig(new TwitterAuthConfig(BuildConfig.TWITTER_KEY, BuildConfig.TWITTER_SECRET))
                .build();
        Twitter.initialize(config);
    }

    public  ImagesComponent geImagesComponent(Fragment fragment, ImagesView view, OnItemClickListener onItemClickListener){
        return DaggerImagesComponent
                .builder()
                .libsModule(new LibsModule(fragment))
                .imagesModule(new ImagesModule(view, onItemClickListener))
                .build();
    }

    public HashtagsComponent geHashtagsComponent(Fragment fragment, HashtagsView view, com.chaldrac.twitterclient.hashtag.adapters.OnItemClickListener onItemClickListener){
        return DaggerHashtagsComponent
                .builder()
                .libsModule(new LibsModule(fragment))
                .hashtagsModule(new HashtagsModule(view, onItemClickListener))
                .build();
    }
}
