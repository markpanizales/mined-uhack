package com.pocketmarket.mined.di.components;

import com.pocketmarket.mined.activity.SigninActivity;
import com.pocketmarket.mined.di.PerActivity;
import com.pocketmarket.mined.di.modules.ActivityModule;

import dagger.Component;

/**
 * Created by mark on 12/1/17.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(SigninActivity signinActivity);
}
