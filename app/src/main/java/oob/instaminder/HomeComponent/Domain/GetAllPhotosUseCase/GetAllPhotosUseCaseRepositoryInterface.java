package oob.instaminder.HomeComponent.Domain.GetAllPhotosUseCase;

import java.util.List;

import oob.instaminder.HomeComponent.Domain.GetAllPhotosUseCase.Model.Photo;

public interface GetAllPhotosUseCaseRepositoryInterface {
    List<Photo> getAllOrderByDate();
}
