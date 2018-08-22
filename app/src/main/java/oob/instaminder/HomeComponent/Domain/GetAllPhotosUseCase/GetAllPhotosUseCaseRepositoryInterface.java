package oob.instaminder.HomeComponent.Domain.GetAllPhotosUseCase;

import java.util.List;

import oob.instaminder.HomeComponent.Domain.GetAllPhotosUseCase.Model.Photo;

public interface GetAllPhotosUseCaseRepositoryInterface {
    void fetchAllOrderByDate(Callback callback);

    interface Callback {
        void onPhotoListRetrieved(List<Photo> photoList);
    }
}
