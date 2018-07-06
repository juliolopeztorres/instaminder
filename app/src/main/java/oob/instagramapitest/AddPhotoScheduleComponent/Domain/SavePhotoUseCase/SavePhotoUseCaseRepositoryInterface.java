package oob.instagramapitest.AddPhotoScheduleComponent.Domain.SavePhotoUseCase;

import oob.instagramapitest.AddPhotoScheduleComponent.Domain.SavePhotoUseCase.Model.Photo;

public interface SavePhotoUseCaseRepositoryInterface {
    void save(Photo photo);
}
