package oob.instagramapitest.ApplicationComponent;

import android.app.Application;

import oob.instagramapitest.ApplicationComponent.DependencyInjection.BaseApplicationComponentInterface;
import oob.instagramapitest.ApplicationComponent.DependencyInjection.DaggerBaseApplicationComponentInterface;
import oob.instagramapitest.ApplicationComponent.DependencyInjection.DatabaseModule;
import oob.instagramapitest.ApplicationComponent.DependencyInjection.PreferencesModule;

public class BaseApplication extends Application {
    private static final String PREFERENCES_NAME = "instaminder_preferences";

    private BaseApplicationComponentInterface component;

    @Override
    public void onCreate() {
        super.onCreate();

        // ------------ DAGGER - DI -------------- //;
        this.component = DaggerBaseApplicationComponentInterface.builder()
                .preferencesModule(new PreferencesModule(this, PREFERENCES_NAME))
                .databaseModule(new DatabaseModule(this))
                .build();
    }

    public BaseApplicationComponentInterface getComponent() {
        return component;
    }
}
