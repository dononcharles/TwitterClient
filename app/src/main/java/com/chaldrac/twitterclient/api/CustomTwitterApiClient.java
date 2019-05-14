package com.chaldrac.twitterclient.api;

import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterSession;

public class CustomTwitterApiClient extends TwitterApiClient {

    public CustomTwitterApiClient(TwitterSession session) {
        super(session);
    }

    public TimeLineService getTimeLineService(){
        return getService(TimeLineService.class);
    }
}
