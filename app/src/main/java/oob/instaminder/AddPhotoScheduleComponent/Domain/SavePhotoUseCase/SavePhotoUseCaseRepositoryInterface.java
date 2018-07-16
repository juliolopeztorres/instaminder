package oob.instaminder.AddPhotoScheduleComponent.Domain.SavePhotoUseCase;

import oob.instaminder.AddPhotoScheduleComponent.Domain.SavePhotoUseCase.Model.Photo;

public interface SavePhotoUseCaseRepositoryInterface {
    void save(Photo photo);
}
