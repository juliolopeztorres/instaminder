package oob.instaminder.HomeComponent.Domain.GetAllPhotosUseCase;

import java.util.List;

import javax.inject.Inject;

import oob.instaminder.HomeComponent.Domain.GetAllPhotosUseCase.Model.Photo;

public class GetAllPhotosUseCase implements GetAllPhotosUseCaseRepositoryInterface.Callback {
    private GetAllPhotosUseCaseViewInterface viewInterface;
    private GetAllPhotosUseCaseRepositoryInterface repositoryInterface;

    @Inject
    public GetAllPhotosUseCase(GetAllPhotosUseCaseViewInterface viewInterface, GetAllPhotosUseCaseRepositoryInterface repositoryInterface) {
        this.viewInterface = viewInterface;
        this.repositoryInterface = repositoryInterface;
    }

    public void fetchAll() {
        this.repositoryInterface.fetchAllOrderByDate(this);
    }

    @Override
    public void onPhotoListRetrieved(List<Photo> photoList) {
        this.viewInterface.loadPhotos(photoList);
    }
}
