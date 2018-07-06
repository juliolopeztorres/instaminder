package oob.instagramapitest.ApplicationComponent.DependencyInjection;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

@Module
public class DatabaseModule {
    private Context context;

    public DatabaseModule(Context context) {
        this.context = context;
    }

    @Provides
    @BaseApplicationScopeInterface
    Realm provideRealm() {
        Realm.init(this.context);
        Realm.setDefaultConfiguration(new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build());

        return Realm.getDefaultInstance();
    }
}
