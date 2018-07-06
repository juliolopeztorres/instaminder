package oob.instagramapitest.AddPhotoScheduleComponent.Framework.DependencyInjection;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import oob.instagramapitest.AddPhotoScheduleComponent.Data.SavePhotoUseCase.SavePhotoUseCaseRepository;
import oob.instagramapitest.AddPhotoScheduleComponent.Domain.SavePhotoUseCase.SavePhotoUseCaseRepositoryInterface;
import oob.instagramapitest.AddPhotoScheduleComponent.Domain.SavePhotoUseCase.SavePhotoUseCaseViewInterface;
import oob.instagramapitest.AddPhotoScheduleComponent.Domain.ViewInterface;

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
    SavePhotoUseCaseRepositoryInterface provideSavePhotoUseCaseRepositoryInterface(Realm realm) {
        return new SavePhotoUseCaseRepository(realm);
    }
}
