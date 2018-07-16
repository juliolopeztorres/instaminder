package oob.instaminder.ApplicationComponent.DependencyInjection;

import android.content.Context;

import dagger.Component;
import io.realm.Realm;
import oob.instaminder.Util.InstagramAPI.InstagramWrapper;
import oob.instaminder.Util.PreferencesWrapper;

@BaseApplicationScopeInterface
@Component(modules = {PreferencesModule.class, InstagramModule.class, DatabaseModule.class})
public interface BaseApplicationComponentInterface {
    Context getContext();

    PreferencesWrapper getPreferencesWrapper();

    InstagramWrapper getInstagramWrapper();

    Realm getRealm();
}
