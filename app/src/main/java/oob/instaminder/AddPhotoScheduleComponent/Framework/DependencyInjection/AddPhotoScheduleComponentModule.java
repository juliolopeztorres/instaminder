package oob.instaminder.AddPhotoScheduleComponent.Framework.DependencyInjection;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import oob.instaminder.AddPhotoScheduleComponent.Data.GetNumberAdShownUseCase.GetNumberAdShownUseCaseRepository;
import oob.instaminder.AddPhotoScheduleComponent.Data.IncreaseNumberAdShownUseCase.IncreaseNumberAdShownUseCaseRepository;
import oob.instaminder.AddPhotoScheduleComponent.Data.SavePhotoUseCase.SavePhotoUseCaseRepository;
import oob.instaminder.AddPhotoScheduleComponent.Domain.GetNumberAdShownUseCase.GetNumberAdShownUseCaseRepositoryInterface;
import oob.instaminder.AddPhotoScheduleComponent.Domain.GetNumberAdShownUseCase.GetNumberAdShownUseCaseViewRepository;
import oob.instaminder.AddPhotoScheduleComponent.Domain.IncreaseNumberAdShownUseCase.IncreaseNumberAdShownUseCaseRepositoryInterface;
import oob.instaminder.AddPhotoScheduleComponent.Domain.IncreaseNumberAdShownUseCase.IncreaseNumberAdShownUseCaseViewInterface;
import oob.instaminder.AddPhotoScheduleComponent.Domain.SavePhotoUseCase.SavePhotoUseCaseRepositoryInterface;
import oob.instaminder.AddPhotoScheduleComponent.Domain.SavePhotoUseCase.SavePhotoUseCaseViewInterface;
import oob.instaminder.AddPhotoScheduleComponent.Domain.ViewInterface;
import oob.instaminder.Util.PreferencesWrapper;

@Module
public class AddPhotoScheduleComponentModule {
    private ViewInterface viewInterface;

    public AddPhotoScheduleComponentModule(ViewInterface viewInterface) {
        this.viewInterface = viewInterface;
    }

    @AddPhotoScheduleComponentScopeInterface
    @Provides
    SavePhotoUseCaseViewInterface provideSavePhotoUseCaseViewInterface() {
        return this.viewInterface;
    }

    @AddPhotoScheduleComponentScopeInterface
    @Provides
    SavePhotoUseCaseRepositoryInterface provideSavePhotoUseCaseRepositoryInterface(Context context, Realm realm) {
        return new SavePhotoUseCaseRepository(context, realm);
    }

    @AddPhotoScheduleComponentScopeInterface
    @Provides
    GetNumberAdShownUseCaseViewRepository provideGetNumberAdShownUseCaseViewRepository() {
        return this.viewInterface;
    }

    @AddPhotoScheduleComponentScopeInterface
    @Provides
    GetNumberAdShownUseCaseRepositoryInterface provideGetNumberAdShownUseCaseRepositoryInterface(PreferencesWrapper preferencesWrapper) {
        return new GetNumberAdShownUseCaseRepository(preferencesWrapper);
    }

    @AddPhotoScheduleComponentScopeInterface
    @Provides
    IncreaseNumberAdShownUseCaseViewInterface provideIncreaseNumberAdShownUseCaseViewInterface() {
        return this.viewInterface;
    }

    @AddPhotoScheduleComponentScopeInterface
    @Provides
    IncreaseNumberAdShownUseCaseRepositoryInterface provideIncreaseNumberAdShownUseCaseRepositoryInterface(PreferencesWrapper preferencesWrapper) {
        return new IncreaseNumberAdShownUseCaseRepository(preferencesWrapper);
    }
}
