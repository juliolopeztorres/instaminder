package oob.instagramapitest.HomeComponent.Domain;

import oob.instagramapitest.HomeComponent.Domain.CheckNickPasswordStoredUseCase.CheckNickPasswordStoredUseCaseViewInterface;
import oob.instagramapitest.HomeComponent.Domain.GetAllPhotosUseCase.GetAllPhotosUseCaseViewInterface;
import oob.instagramapitest.HomeComponent.Domain.GetInstagramUserInformationUseCase.GetInstagramUserInformationUseCaseViewInterface;
import oob.instagramapitest.HomeComponent.Domain.LoginWithNewInformationUseCase.LoginWithNewInformationUseCaseViewInterface;

public interface ViewInterface extends
        CheckNickPasswordStoredUseCaseViewInterface,
        GetInstagramUserInformationUseCaseViewInterface,
        LoginWithNewInformationUseCaseViewInterface,
        GetAllPhotosUseCaseViewInterface {
}
