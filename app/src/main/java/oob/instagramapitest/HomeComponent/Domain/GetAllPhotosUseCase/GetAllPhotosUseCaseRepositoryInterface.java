package oob.instagramapitest.HomeComponent.Domain.GetAllPhotosUseCase;

import java.util.List;

import oob.instagramapitest.HomeComponent.Domain.GetAllPhotosUseCase.Model.Photo;

public interface GetAllPhotosUseCaseRepositoryInterface {
    List<Photo> getAllOrderByDate();
}
