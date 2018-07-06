package oob.instagramapitest.ApplicationComponent;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import oob.instagramapitest.ApplicationComponent.DependencyInjection.BaseApplicationComponentInterface;
import oob.instagramapitest.ApplicationComponent.DependencyInjection.DaggerBaseApplicationComponentInterface;
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
                .build();

        Realm.init(this);
        Realm.setDefaultConfiguration(new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build());
    }

    public BaseApplicationComponentInterface getComponent() {
        return component;
    }
}
