package oob.instaminder.HomeComponent.Domain.UpdatePhotoDateUseCase;

import java.util.Date;

public interface UpdatePhotoDateUseCaseRepositoryInterface {
    void update(String photoId, Date date);
}
