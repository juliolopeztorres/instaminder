package oob.instaminder.ApplicationComponent.DependencyInjection;

import dagger.Module;
import dagger.Provides;
import oob.instaminder.Util.InstagramAPI.InstagramWrapper;

@Module
class InstagramModule {

    @Provides
    @BaseApplicationScopeInterface
    InstagramWrapper provideInstagramWrapper() {
        return new InstagramWrapper();
    }
}
