package oob.instagramapitest.HomeComponent.Domain.RemovePhotoUseCase;

import javax.inject.Inject;

public class RemovePhotoUseCase {
    private RemovePhotoUseCaseViewInterface viewInterface;
    private RemovePhotoUseCaseRepositoryInterface repositoryInterface;

    @Inject
    public RemovePhotoUseCase(RemovePhotoUseCaseViewInterface viewInterface, RemovePhotoUseCaseRepositoryInterface repositoryInterface) {
        this.viewInterface = viewInterface;
        this.repositoryInterface = repositoryInterface;
    }

    public void remove(String photoId) {
        this.repositoryInterface.remove(photoId);
    }
}
