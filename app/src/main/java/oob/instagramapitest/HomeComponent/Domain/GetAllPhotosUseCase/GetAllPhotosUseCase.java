package oob.instagramapitest.HomeComponent.Domain.GetAllPhotosUseCase;

import java.util.List;

import javax.inject.Inject;

import oob.instagramapitest.HomeComponent.Domain.GetAllPhotosUseCase.Model.Photo;

public class GetAllPhotosUseCase {
    private GetAllPhotosUseCaseViewInterface viewInterface;
    private GetAllPhotosUseCaseRepositoryInterface repositoryInterface;

    @Inject
    public GetAllPhotosUseCase(GetAllPhotosUseCaseViewInterface viewInterface, GetAllPhotosUseCaseRepositoryInterface repositoryInterface) {
        this.viewInterface = viewInterface;
        this.repositoryInterface = repositoryInterface;
    }

    public List<Photo> getAll() {
        return this.repositoryInterface.getAllOrderByDate();
    }
}
