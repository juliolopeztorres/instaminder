package oob.instagramapitest.OptionsComponent.Framework.DependencyInjection;

import dagger.Module;
import dagger.Provides;
import oob.instagramapitest.OptionsComponent.Data.GetUserInformationUseCase.GetUserInformationUseCaseRepository;
import oob.instagramapitest.OptionsComponent.Data.SaveUserInformationUseCase.SaveUserInformationUseCaseRepository;
import oob.instagramapitest.OptionsComponent.Domain.GetUserInformationUseCase.GetUserInformationUseCaseRepositoryInterface;
import oob.instagramapitest.OptionsComponent.Domain.GetUserInformationUseCase.GetUserInformationUseCaseViewInterface;
import oob.instagramapitest.OptionsComponent.Domain.SaveUserInformationUseCase.SaveUserInformationUseCaseRepositoryInterface;
import oob.instagramapitest.OptionsComponent.Domain.SaveUserInformationUseCase.SaveUserInformationUseCaseViewInterface;
import oob.instagramapitest.OptionsComponent.Domain.ViewInterface;
import oob.instagramapitest.Util.PreferencesWrapper;

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
    SaveUserInformationUseCaseRepositoryInterface provideSaveUserInformationUseCaseRepositoryInterface(PreferencesWrapper preferencesWrapper) {
        return new SaveUserInformationUseCaseRepository(preferencesWrapper);
    }

    @OptionsComponentScopeInterface
    @Provides
    GetUserInformationUseCaseViewInterface provideGetUserInformationUseCaseViewInterface() {
        return this.viewInterface;
    }

    @OptionsComponentScopeInterface
    @Provides
    GetUserInformationUseCaseRepositoryInterface provideGetUserInformationUseCaseRepositoryInterface(PreferencesWrapper preferencesWrapper) {
        return new GetUserInformationUseCaseRepository(preferencesWrapper);
    }
}
