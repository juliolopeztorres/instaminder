package oob.instagramapitest.HomeComponent.Domain.GetInstagramUserInformationUseCase;

import oob.instagramapitest.HomeComponent.Domain.GetInstagramUserInformationUseCase.Model.InstagramUserInformation;

public interface GetInstagramUserInformationUseCaseViewInterface {
    void onGetUserInformationSuccess(InstagramUserInformation instagramUserInformation);

    void showError(String message);
}
