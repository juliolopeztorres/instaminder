package oob.instagramapitest.OptionsComponent.Framework.DependencyInjection;

import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;
import oob.instagramapitest.OptionsComponent.Data.GetUserInformationUseCase.GetUserInformationUseCaseRepository;
import oob.instagramapitest.OptionsComponent.Data.SaveUserInformationUseCase.SaveUserInformationUseCaseRepository;
import oob.instagramapitest.OptionsComponent.Domain.GetUserInformationUseCase.GetUserInformationUseCaseRepositoryInterface;
import oob.instagramapitest.OptionsComponent.Domain.GetUserInformationUseCase.GetUserInformationUseCaseViewInterface;
import oob.instagramapitest.OptionsComponent.Domain.SaveUserInformationUseCase.SaveUserInformationUseCaseRepositoryInterface;
import oob.instagramapitest.OptionsComponent.Domain.SaveUserInformationUseCase.SaveUserInformationUseCaseViewInterface;
import oob.instagramapitest.OptionsComponent.Domain.ViewInterface;

@Module
public class OptionsComponentModule {
    private ViewInterface viewInterface;

    public OptionsComponentModule(ViewInterface viewInterface) {
        this.viewInterface = viewInterface;
    }

    @OptionsComponentScopeInterface
    @Provides
    SaveUserInformationUseCaseViewInterface provideSaveUserInformationUseCaseViewInterface() {
        return this.viewInterface;
    }

    @OptionsComponentScopeInterface
    @Provides
    SaveUserInformationUseCaseRepositoryInterface provideSaveUserInformationUseCaseRepositoryInterface(SharedPreferences preferences) {
        return new SaveUserInformationUseCaseRepository(preferences);
    }

    @OptionsComponentScopeInterface
    @Provides
    GetUserInformationUseCaseViewInterface provideGetUserInformationUseCaseViewInterface() {
        return this.viewInterface;
    }

    @OptionsComponentScopeInterface
    @Provides
    GetUserInformationUseCaseRepositoryInterface provideGetUserInformationUseCaseRepositoryInterface(SharedPreferences preferences) {
        return new GetUserInformationUseCaseRepository(preferences);
    }
}
