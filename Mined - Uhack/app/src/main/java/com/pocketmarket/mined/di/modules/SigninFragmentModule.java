package com.pocketmarket.mined.di.modules;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;

import com.pocketmarket.mined.controllers.ErrorMessage;
import com.pocketmarket.mined.data.SharedPrefReference;
import com.pocketmarket.mined.di.ActivityErrorMessage;
import com.pocketmarket.mined.di.ApplicationActivity;
import com.pocketmarket.mined.di.ApplicationContext;
import com.pocketmarket.mined.di.SharedReference;
import com.pocketmarket.mined.utility.AppApi;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mark on 12/1/17.
 */
@Module
public class SigninFragmentModule {
    private final static String TAG = "SigninFragmentModule";

    private Fragment mFragment;
    private Activity mActivity;
    private SharedPreferences mSharedPreferences;

    public SigninFragmentModule(Fragment fragment) {
        mFragment = fragment;

    }

    @Provides
    @ApplicationContext
    Context provideContext(){
        return mFragment.getContext();
    };

    @Provides
    @ApplicationActivity
    Activity provideActivity(){
        mActivity = mFragment.getActivity();
        return mActivity;
    }

    @Provides
    @ActivityErrorMessage
    ErrorMessage provideErrorMessage(){
        return new ErrorMessage(mActivity);
    }

    @Provides
    SharedPreferences provideSharedPreferences(){
        return mActivity.getSharedPreferences(AppApi.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    @Provides
    @SharedReference
    SharedPrefReference provideSharedPrefReference(){
        mSharedPreferences = mActivity.getSharedPreferences(AppApi.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return new SharedPrefReference(mSharedPreferences);
    }

}
