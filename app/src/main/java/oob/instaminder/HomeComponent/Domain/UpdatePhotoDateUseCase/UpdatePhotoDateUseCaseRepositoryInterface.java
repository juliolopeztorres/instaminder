package oob.instaminder.HomeComponent.Domain.UpdatePhotoDateUseCase;

import java.util.Date;

import oob.instaminder.HomeComponent.Domain.GetAllPhotosUseCase.Model.Photo;

public interface UpdatePhotoDateUseCaseRepositoryInterface {
    void update(Photo photo, Date date);
}
