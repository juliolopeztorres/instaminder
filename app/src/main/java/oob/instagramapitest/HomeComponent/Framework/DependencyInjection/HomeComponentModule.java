package oob.instagramapitest.HomeComponent.Framework.DependencyInjection;

import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;
import oob.instagramapitest.HomeComponent.Data.CheckNickPasswordStoredUseCase.CheckNickPasswordStoredUseCaseRepository;
import oob.instagramapitest.HomeComponent.Domain.CheckNickPasswordStoredUseCase.CheckNickPasswordStoredUseCaseRepositoryInterface;
import oob.instagramapitest.HomeComponent.Domain.CheckNickPasswordStoredUseCase.CheckNickPasswordStoredUseCaseViewInterface;
import oob.instagramapitest.HomeComponent.Domain.ViewInterface;

@Module
public class HomeComponentModule {
    private ViewInterface viewInterface;

    public HomeComponentModule(ViewInterface viewInterface) {
        this.viewInterface = viewInterface;
    }

    @Provides
    @HomeComponentScopeInterface
    CheckNickPasswordStoredUseCaseViewInterface provideCheckNickPasswordStoredUseCaseViewInterface() {
        return this.viewInterface;
    }

    @Provides
    @HomeComponentScopeInterface
    CheckNickPasswordStoredUseCaseRepositoryInterface provideCheckNickPasswordStoredUseCaseRepositoryInterface(SharedPreferences preferences) {
        return new CheckNickPasswordStoredUseCaseRepository(preferences);
    }
}
