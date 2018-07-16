package oob.instaminder.HomeComponent.Domain.GetInstagramUserInformationUseCase;

import oob.instaminder.HomeComponent.Domain.GetInstagramUserInformationUseCase.Model.InstagramUserInformation;

public interface GetInstagramUserInformationUseCaseViewInterface {
    void onGetUserInformationSuccess(InstagramUserInformation instagramUserInformation);

    void showError(String message);
}
