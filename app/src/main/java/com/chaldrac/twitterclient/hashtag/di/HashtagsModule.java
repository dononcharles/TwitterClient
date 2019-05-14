package com.chaldrac.twitterclient.hashtag.di;

import com.chaldrac.twitterclient.api.CustomTwitterApiClient;
import com.chaldrac.twitterclient.db.HashsTag;
import com.chaldrac.twitterclient.hashtag.*;
import com.chaldrac.twitterclient.hashtag.adapters.HashtagsAdapter;
import com.chaldrac.twitterclient.hashtag.adapters.OnItemClickListener;
import com.chaldrac.twitterclient.hashtag.ui.HashtagsView;
import com.chaldrac.twitterclient.lib.base.EventBus;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Module
public class HashtagsModule {

    private HashtagsView hashtagsView;
    private OnItemClickListener onItemClickListener;

    public HashtagsModule(HashtagsView hashtagsView, OnItemClickListener onItemClickListener) {
        this.hashtagsView = hashtagsView;
        this.onItemClickListener = onItemClickListener;
    }

    @Singleton
    @Provides
    HashtagsAdapter providesHashtagsAdapter(List<HashsTag> hashsTags, OnItemClickListener clickListener){
        return new HashtagsAdapter(hashsTags, clickListener);
    }

    @Singleton
    @Provides
    OnItemClickListener providesOnItemClickListener(){
        return this.onItemClickListener;
    }

    @Singleton
    @Provides
    List<HashsTag> providesHashsTagList(){
        return new ArrayList<>();
    }

    @Singleton
    @Provides
    HashtagsPresenter providesHashtagsPresenter(HashtagsView view, EventBus bus, HashtagsInteractor interactor){
        return new HashtagsPresenterImpl(view, bus, interactor);
    }

    @Singleton
    @Provides
    HashtagsView providesHashtagsView(){
        return this.hashtagsView;
    }

    @Singleton
    @Provides
    HashtagsInteractor providesHashtagsInteractor(HashtagsRepository repository){
        return new HashtagsInteractorImpl(repository);
    }

    @Singleton
    @Provides
    HashtagsRepository providesHashtagsRepository(EventBus eventBus, CustomTwitterApiClient apiClient){
        return new HashtagsRepositoryImpl(eventBus, apiClient);
    }

    @Singleton
    @Provides
    CustomTwitterApiClient providesCustomTwitterApiClient(TwitterSession twitterSession){
        return new CustomTwitterApiClient(twitterSession);
    }

    @Singleton
    @Provides
    TwitterSession providesTwitterSession(){
        return TwitterCore.getInstance().getSessionManager().getActiveSession();
    }
}
