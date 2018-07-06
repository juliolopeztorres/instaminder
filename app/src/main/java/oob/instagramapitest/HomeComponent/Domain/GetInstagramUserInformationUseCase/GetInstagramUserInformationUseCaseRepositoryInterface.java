package oob.instagramapitest.HomeComponent.Domain.GetInstagramUserInformationUseCase;

import oob.instagramapitest.HomeComponent.Domain.GetInstagramUserInformationUseCase.Model.InstagramUserInformation;

public interface GetInstagramUserInformationUseCaseRepositoryInterface {
    void get(Callback callback);

    interface Callback {
        void onGetUserInformationSuccess(InstagramUserInformation instagramUserInformation);

        void onGetUserInformationError(String message);
    }
}
