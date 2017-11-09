package com.jaychang.sa;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.jaychang.utils.AppUtils;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;

import java.util.Collections;

public class SimpleAuth {

    @SuppressLint("StaticFieldLeak")
    private static SimpleAuth instance;
    private Context appContext;
    private AuthData facebookAuthData;
    private AuthData googleAuthData;
    private AuthData twitterAuthData;
    private AuthData instagramAuthData;

    private SimpleAuth() {
    }

    public static SimpleAuth getInstance() {
        if (instance == null) {
            synchronized (SimpleAuth.class) {
                if (instance == null) {
                    instance = new SimpleAuth();
                }
            }
        }
        return instance;
    }

    static void init(Context context) {
        Context appContext = context.getApplicationContext();
        getInstance().appContext = appContext;
        getInstance().initTwitter(appContext);
    }


    private void initTwitter(Context appContext) {
        String consumerKey = AppUtils.getMetaDataValue(appContext, appContext.getString(R.string.sa_com_jaychang_sa_twitterConsumerKey));
        String consumerSecret = AppUtils.getMetaDataValue(appContext, appContext.getString(R.string.sa_com_jaychang_sa_twitterConsumerSecret));

        if (consumerKey != null && consumerSecret != null) {
            TwitterConfig twitterConfig = new TwitterConfig.Builder(appContext)
                    .twitterAuthConfig(new TwitterAuthConfig(consumerKey, consumerSecret))
                    .build();
            Twitter.initialize(twitterConfig);
        }
    }

    public void connectTwitter(@NonNull AuthCallback listener) {
        twitterAuthData = new AuthData(Collections.emptyList(), listener);
        TwitterAuthActivity.start(appContext);
    }

    public void disconnectTwitter() {
        twitterAuthData = null;
        TwitterCore.getInstance().getSessionManager().clearActiveSession();
        clearCookies();
    }

    private void clearCookies() {
        if (Build.VERSION.SDK_INT >= 21) {
            CookieManager.getInstance().removeAllCookies(null);
        } else {
            CookieSyncManager.createInstance(appContext);
            CookieManager.getInstance().removeAllCookie();
        }
    }

    AuthData getTwitterAuthData() {
        return twitterAuthData;
    }


}
