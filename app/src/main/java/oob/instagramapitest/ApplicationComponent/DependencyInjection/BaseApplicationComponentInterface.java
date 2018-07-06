package oob.instagramapitest.ApplicationComponent.DependencyInjection;

import dagger.Component;
import io.realm.Realm;
import oob.instagramapitest.Util.InstagramAPI.InstagramWrapper;
import oob.instagramapitest.Util.PreferencesWrapper;

@BaseApplicationScopeInterface
@Component(modules = {PreferencesModule.class, InstagramModule.class, DatabaseModule.class})
public interface BaseApplicationComponentInterface {
    PreferencesWrapper getPreferencesWrapper();

    InstagramWrapper getInstagramWrapper();

    Realm getRealm();
}
