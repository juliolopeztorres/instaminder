package oob.instaminder.HomeComponent.Domain.UpdatePhotoDateUseCase;

import java.util.Date;

import javax.inject.Inject;

import oob.instaminder.HomeComponent.Domain.GetAllPhotosUseCase.Model.Photo;

public class UpdatePhotoDateUseCase {
    private UpdatePhotoDateUseCaseViewInterface viewInterface;
    private UpdatePhotoDateUseCaseRepositoryInterface repositoryInterface;

    @Inject
    public UpdatePhotoDateUseCase(UpdatePhotoDateUseCaseViewInterface viewInterface, UpdatePhotoDateUseCaseRepositoryInterface repositoryInterface) {
        this.viewInterface = viewInterface;
        this.repositoryInterface = repositoryInterface;
    }

    public void update(Photo photo, Date date) {
        this.repositoryInterface.update(photo, date);
    }
}
