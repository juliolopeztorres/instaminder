package oob.instagramapitest.AddPhotoScheduleComponent.Domain.SavePhotoUseCase;

import javax.inject.Inject;

import oob.instagramapitest.AddPhotoScheduleComponent.Domain.SavePhotoUseCase.Model.Photo;

public class SavePhotoUseCase {
    private SavePhotoUseCaseViewInterface viewInterface;
    private SavePhotoUseCaseRepositoryInterface repositoryInterface;

    @Inject
    public SavePhotoUseCase(SavePhotoUseCaseViewInterface viewInterface, SavePhotoUseCaseRepositoryInterface repositoryInterface) {
        this.viewInterface = viewInterface;
        this.repositoryInterface = repositoryInterface;
    }

    public void save(Photo photo) {
        this.repositoryInterface.save(photo);
    }
}
