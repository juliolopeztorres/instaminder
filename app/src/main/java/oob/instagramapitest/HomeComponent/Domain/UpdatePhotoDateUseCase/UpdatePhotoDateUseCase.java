package oob.instagramapitest.HomeComponent.Domain.UpdatePhotoDateUseCase;

import java.util.Date;

import javax.inject.Inject;

public class UpdatePhotoDateUseCase {
    private UpdatePhotoDateUseCaseViewInterface viewInterface;
    private UpdatePhotoDateUseCaseRepositoryInterface repositoryInterface;

    @Inject
    public UpdatePhotoDateUseCase(UpdatePhotoDateUseCaseViewInterface viewInterface, UpdatePhotoDateUseCaseRepositoryInterface repositoryInterface) {
        this.viewInterface = viewInterface;
        this.repositoryInterface = repositoryInterface;
    }

    public void update(String photoId, Date date) {
        this.repositoryInterface.update(photoId, date);
    }
}
