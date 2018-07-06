package oob.instagramapitest.ApplicationComponent.DependencyInjection;

import dagger.Component;
import oob.instagramapitest.Util.InstagramAPI.InstagramWrapper;
import oob.instagramapitest.Util.PreferencesWrapper;

@BaseApplicationScopeInterface
@Component(modules = {PreferencesModule.class, InstagramModule.class})
public interface BaseApplicationComponentInterface {
    PreferencesWrapper getPreferencesWrapper();

    InstagramWrapper getInstagramWrapper();
}
