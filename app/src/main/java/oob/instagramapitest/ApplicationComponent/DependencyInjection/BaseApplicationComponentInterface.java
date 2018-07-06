package oob.instagramapitest.ApplicationComponent.DependencyInjection;


import android.content.SharedPreferences;

import dagger.Component;

@BaseApplicationScopeInterface
@Component(modules = PreferencesModule.class)
public interface BaseApplicationComponentInterface {
    SharedPreferences getPreferences();
}
