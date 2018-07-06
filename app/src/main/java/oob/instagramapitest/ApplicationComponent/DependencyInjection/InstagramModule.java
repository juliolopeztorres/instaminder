package oob.instagramapitest.ApplicationComponent.DependencyInjection;

import dagger.Module;
import dagger.Provides;
import oob.instagramapitest.Util.InstagramAPI.InstagramWrapper;

@Module
class InstagramModule {

    @Provides
    @BaseApplicationScopeInterface
    InstagramWrapper provideInstagramWrapper() {
        return new InstagramWrapper();
    }
}
