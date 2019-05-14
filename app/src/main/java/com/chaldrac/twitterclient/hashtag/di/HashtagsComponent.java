package com.chaldrac.twitterclient.hashtag.di;

import com.chaldrac.twitterclient.hashtag.HashtagsPresenter;
import com.chaldrac.twitterclient.hashtag.ui.HashtagsFragment;
import com.chaldrac.twitterclient.lib.di.LibsModule;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {LibsModule.class, HashtagsModule.class})
public interface HashtagsComponent {
    void inject(HashtagsFragment hashtagsFragment);
    HashtagsPresenter getPresenter();
}
