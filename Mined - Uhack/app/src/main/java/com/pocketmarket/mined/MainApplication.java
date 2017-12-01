package com.pocketmarket.mined;

import android.app.Application;
import android.content.Context;

import com.pocketmarket.mined.di.components.ApplicationComponent;
import com.pocketmarket.mined.di.components.DaggerApplicationComponent;
import com.pocketmarket.mined.di.modules.ApplicationModule;

import timber.log.Timber;

/**
 * Created by mark on 12/1/17.
 */

public class MainApplication extends Application {
    protected ApplicationComponent mApplicationComponent;

    public static MainApplication get(Context context){
        return (MainApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree() {
            @Override
            protected String createStackElementTag(StackTraceElement element) {
                return super.createStackElementTag(element) + ":" + element.getLineNumber();
            }
        });

        mApplicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        mApplicationComponent.inject(this);

    }

    public ApplicationComponent getComponent(){
        return mApplicationComponent;
    }

}

