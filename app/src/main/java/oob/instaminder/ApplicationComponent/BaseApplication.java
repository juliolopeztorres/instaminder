package oob.instaminder.ApplicationComponent;

import android.app.Application;

import oob.instaminder.ApplicationComponent.DependencyInjection.BaseApplicationComponentInterface;
import oob.instaminder.ApplicationComponent.DependencyInjection.DaggerBaseApplicationComponentInterface;
import oob.instaminder.ApplicationComponent.DependencyInjection.DatabaseModule;
import oob.instaminder.ApplicationComponent.DependencyInjection.PreferencesModule;

public class BaseApplication extends Application {
    private static final String PREFERENCES_NAME = "instaminder_preferences";
    private boolean firstLaunch;

    private BaseApplicationComponentInterface component;

    @Override
    public void onCreate() {
        super.onCreate();

        // ------------ DAGGER - DI -------------- //;
        this.component = DaggerBaseApplicationComponentInterface.builder()
                .preferencesModule(new PreferencesModule(this, PREFERENCES_NAME))
                .databaseModule(new DatabaseModule(this))
                .build();

        this.defineFirstLaunch();
    }

    private void defineFirstLaunch() {
        this.firstLaunch = this.component.getPreferencesWrapper().isFirstLaunch();

        if (this.firstLaunch) {
            this.component.getPreferencesWrapper().updateFirstLaunch();
        }
    }

    public BaseApplicationComponentInterface getComponent() {
        return component;
    }

    public boolean isFirstLaunch() {
        return this.firstLaunch;
    }
}
