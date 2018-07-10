package oob.instagramapitest.HomeComponent.Framework.DependencyInjection;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import oob.instagramapitest.HomeComponent.Data.CheckNickPasswordStoredUseCase.CheckNickPasswordStoredUseCaseRepository;
import oob.instagramapitest.HomeComponent.Data.GetAllPhotosUseCase.GetAllPhotosUseCaseRepository;
import oob.instagramapitest.HomeComponent.Data.GetInstagramUserInformationUseCase.GetInstagramUserInformationUseCaseRepository;
import oob.instagramapitest.HomeComponent.Data.LoginWithNewInformationUseCase.LoginWithNewInformationUseCaseRepository;
import oob.instagramapitest.HomeComponent.Data.RemovePhotoUseCase.RemovePhotoUseCaseRepository;
import oob.instagramapitest.HomeComponent.Data.UpdatePhotoDateUseCase.UpdatePhotoDateUseCaseRepository;
import oob.instagramapitest.HomeComponent.Domain.CheckNickPasswordStoredUseCase.CheckNickPasswordStoredUseCaseRepositoryInterface;
import oob.instagramapitest.HomeComponent.Domain.CheckNickPasswordStoredUseCase.CheckNickPasswordStoredUseCaseViewInterface;
import oob.instagramapitest.HomeComponent.Domain.GetAllPhotosUseCase.GetAllPhotosUseCaseRepositoryInterface;
import oob.instagramapitest.HomeComponent.Domain.GetAllPhotosUseCase.GetAllPhotosUseCaseViewInterface;
import oob.instagramapitest.HomeComponent.Domain.GetInstagramUserInformationUseCase.GetInstagramUserInformationUseCaseRepositoryInterface;
import oob.instagramapitest.HomeComponent.Domain.GetInstagramUserInformationUseCase.GetInstagramUserInformationUseCaseViewInterface;
import oob.instagramapitest.HomeComponent.Domain.LoginWithNewInformationUseCase.LoginWithNewInformationUseCaseRepositoryInterface;
import oob.instagramapitest.HomeComponent.Domain.LoginWithNewInformationUseCase.LoginWithNewInformationUseCaseViewInterface;
import oob.instagramapitest.HomeComponent.Domain.RemovePhotoUseCase.RemovePhotoUseCaseRepositoryInterface;
import oob.instagramapitest.HomeComponent.Domain.RemovePhotoUseCase.RemovePhotoUseCaseViewInterface;
import oob.instagramapitest.HomeComponent.Domain.UpdatePhotoDateUseCase.UpdatePhotoDateUseCaseRepositoryInterface;
import oob.instagramapitest.HomeComponent.Domain.UpdatePhotoDateUseCase.UpdatePhotoDateUseCaseViewInterface;
import oob.instagramapitest.HomeComponent.Domain.ViewInterface;
import oob.instagramapitest.Util.InstagramAPI.InstagramWrapper;
import oob.instagramapitest.Util.PreferencesWrapper;

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
    CheckNickPasswordStoredUseCaseRepositoryInterface provideCheckNickPasswordStoredUseCaseRepositoryInterface(PreferencesWrapper preferencesWrapper) {
        return new CheckNickPasswordStoredUseCaseRepository(preferencesWrapper);
    }

    @Provides
    @HomeComponentScopeInterface
    GetInstagramUserInformationUseCaseViewInterface provideGetInstagramUserInformationUseCaseViewInterface() {
        return this.viewInterface;
    }

    @Provides
    @HomeComponentScopeInterface
    GetInstagramUserInformationUseCaseRepositoryInterface provideGetInstagramUserInformationUseCaseRepositoryInterface(InstagramWrapper instagramWrapper, PreferencesWrapper preferencesWrapper) {
        return new GetInstagramUserInformationUseCaseRepository(instagramWrapper, preferencesWrapper);
    }

    @Provides
    @HomeComponentScopeInterface
    LoginWithNewInformationUseCaseViewInterface provideLoginWithNewInformationUseCaseViewInterface() {
        return this.viewInterface;
    }

    @Provides
    @HomeComponentScopeInterface
    LoginWithNewInformationUseCaseRepositoryInterface provideLoginWithNewInformationUseCaseRepositoryInterface(InstagramWrapper instagramWrapper, PreferencesWrapper preferencesWrapper) {
        return new LoginWithNewInformationUseCaseRepository(instagramWrapper, preferencesWrapper);
    }

    @Provides
    @HomeComponentScopeInterface
    GetAllPhotosUseCaseViewInterface provideGetAllPhotosUseCaseViewInterface() {
        return this.viewInterface;
    }

    @Provides
    @HomeComponentScopeInterface
    GetAllPhotosUseCaseRepositoryInterface provideGetAllPhotosUseCaseRepositoryInterface(Realm realm) {
        return new GetAllPhotosUseCaseRepository(realm);
    }

    @Provides
    @HomeComponentScopeInterface
    UpdatePhotoDateUseCaseViewInterface provideUpdatePhotoDateUseCaseViewInterface() {
        return this.viewInterface;
    }

    @Provides
    @HomeComponentScopeInterface
    UpdatePhotoDateUseCaseRepositoryInterface provideUpdatePhotoDateUseCaseRepositoryInterface(Context context, Realm realm) {
        return new UpdatePhotoDateUseCaseRepository(context, realm);
    }

    @Provides
    @HomeComponentScopeInterface
    RemovePhotoUseCaseViewInterface provideRemovePhotoUseCaseViewInterface() {
        return this.viewInterface;
    }

    @Provides
    @HomeComponentScopeInterface
    RemovePhotoUseCaseRepositoryInterface provideRemovePhotoUseCaseRepositoryInterface(Context context, Realm realm) {
        return new RemovePhotoUseCaseRepository(context, realm);
    }
}
