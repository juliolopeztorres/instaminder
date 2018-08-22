package oob.instaminder.OptionsComponent.Framework.DependencyInjection;

import dagger.Module;
import dagger.Provides;
import oob.instaminder.OptionsComponent.Data.GetUserInformationUseCase.GetUserInformationUseCaseRepository;
import oob.instaminder.OptionsComponent.Data.SaveUserInformationUseCase.SaveUserInformationUseCaseRepository;
import oob.instaminder.OptionsComponent.Domain.GetUserInformationUseCase.GetUserInformationUseCaseRepositoryInterface;
import oob.instaminder.OptionsComponent.Domain.GetUserInformationUseCase.GetUserInformationUseCaseViewInterface;
import oob.instaminder.OptionsComponent.Domain.SaveUserInformationUseCase.SaveUserInformationUseCaseRepositoryInterface;
import oob.instaminder.OptionsComponent.Domain.SaveUserInformationUseCase.SaveUserInformationUseCaseViewInterface;
import oob.instaminder.OptionsComponent.Domain.ViewInterface;
import oob.instaminder.Util.PreferencesWrapper;

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
