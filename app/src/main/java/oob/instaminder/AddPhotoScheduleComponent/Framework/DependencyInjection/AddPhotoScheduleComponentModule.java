package oob.instaminder.AddPhotoScheduleComponent.Framework.DependencyInjection;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import oob.instaminder.AddPhotoScheduleComponent.Data.SavePhotoUseCase.SavePhotoUseCaseRepository;
import oob.instaminder.AddPhotoScheduleComponent.Domain.SavePhotoUseCase.SavePhotoUseCaseRepositoryInterface;
import oob.instaminder.AddPhotoScheduleComponent.Domain.SavePhotoUseCase.SavePhotoUseCaseViewInterface;
import oob.instaminder.AddPhotoScheduleComponent.Domain.ViewInterface;

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
}
