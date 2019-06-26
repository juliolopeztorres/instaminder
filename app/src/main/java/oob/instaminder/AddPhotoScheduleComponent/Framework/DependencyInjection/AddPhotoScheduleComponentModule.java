package oob.instaminder.AddPhotoScheduleComponent.Framework.DependencyInjection;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import oob.instaminder.AddPhotoScheduleComponent.Data.ExternalStorageLastDialogShownUseCase.ExternalStorageLastDialogShownUseCaseRepository;
import oob.instaminder.AddPhotoScheduleComponent.Data.MarkExternalStorageLastDialogAsShownUseCase.MarkExternalStorageLastDialogAsShownUseCaseRepository;
import oob.instaminder.AddPhotoScheduleComponent.Data.SavePhotoUseCase.SavePhotoUseCaseRepository;
import oob.instaminder.AddPhotoScheduleComponent.Domain.ExternalStorageLastDialogShownUseCase.ExternalStorageLastDialogShownUseCaseRepositoryInterface;
import oob.instaminder.AddPhotoScheduleComponent.Domain.ExternalStorageLastDialogShownUseCase.ExternalStorageLastDialogShownUseCaseViewInterface;
import oob.instaminder.AddPhotoScheduleComponent.Domain.MarkExternalStorageLastDialogAsShownUseCase.MarkExternalStorageLastDialogAsShownUseCaseRepositoryInterface;
import oob.instaminder.AddPhotoScheduleComponent.Domain.MarkExternalStorageLastDialogAsShownUseCase.MarkExternalStorageLastDialogAsShownUseCaseViewInterface;
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
    ExternalStorageLastDialogShownUseCaseViewInterface provideExternalStorageLastDialogShownUseCaseViewInterface() {
        return this.viewInterface;
    }

    @AddPhotoScheduleComponentScopeInterface
    @Provides
    ExternalStorageLastDialogShownUseCaseRepositoryInterface provideExternalStorageLastDialogShownUseCaseRepositoryInterface(PreferencesWrapper preferencesWrapper) {
        return new ExternalStorageLastDialogShownUseCaseRepository(preferencesWrapper);
    }

    @AddPhotoScheduleComponentScopeInterface
    @Provides
    MarkExternalStorageLastDialogAsShownUseCaseViewInterface provideMarkExternalStorageLastDialogAsShownUseCaseViewInterface() {
        return this.viewInterface;
    }

    @AddPhotoScheduleComponentScopeInterface
    @Provides
    MarkExternalStorageLastDialogAsShownUseCaseRepositoryInterface provideMarkExternalStorageLastDialogAsShownUseCaseRepository(PreferencesWrapper preferencesWrapper) {
        return new MarkExternalStorageLastDialogAsShownUseCaseRepository(preferencesWrapper);
    }
}
