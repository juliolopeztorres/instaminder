package oob.instagramapitest.HomeComponent.Domain.GetInstagramUserInformationUseCase;

import javax.inject.Inject;

import oob.instagramapitest.HomeComponent.Domain.GetInstagramUserInformationUseCase.Model.InstagramUserInformation;

public class GetInstagramUserInformationUseCase implements GetInstagramUserInformationUseCaseRepositoryInterface.Callback {
    private GetInstagramUserInformationUseCaseViewInterface viewInterface;
    private GetInstagramUserInformationUseCaseRepositoryInterface repositoryInterface;

    @Inject
    public GetInstagramUserInformationUseCase(GetInstagramUserInformationUseCaseViewInterface viewInterface, GetInstagramUserInformationUseCaseRepositoryInterface repositoryInterface) {
        this.viewInterface = viewInterface;
        this.repositoryInterface = repositoryInterface;
    }

    public void get() {
        this.repositoryInterface.get(this);
    }

    @Override
    public void onGetUserInformationSuccess(InstagramUserInformation instagramUserInformation) {
        this.viewInterface.onGetUserInformationSuccess(instagramUserInformation);
    }

    @Override
    public void onGetUserInformationError(String message) {
        this.viewInterface.showError(message);
    }
}
