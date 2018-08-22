package oob.instaminder.HomeComponent.Domain;

import oob.instaminder.HomeComponent.Domain.CheckNickPasswordStoredUseCase.CheckNickPasswordStoredUseCaseViewInterface;
import oob.instaminder.HomeComponent.Domain.GetAllPhotosUseCase.GetAllPhotosUseCaseViewInterface;
import oob.instaminder.HomeComponent.Domain.GetInstagramUserInformationUseCase.GetInstagramUserInformationUseCaseViewInterface;
import oob.instaminder.HomeComponent.Domain.LoginWithNewInformationUseCase.LoginWithNewInformationUseCaseViewInterface;
import oob.instaminder.HomeComponent.Domain.RemovePhotoUseCase.RemovePhotoUseCaseViewInterface;
import oob.instaminder.HomeComponent.Domain.UpdatePhotoDateUseCase.UpdatePhotoDateUseCaseViewInterface;

public interface ViewInterface extends
        CheckNickPasswordStoredUseCaseViewInterface,
        GetInstagramUserInformationUseCaseViewInterface,
        LoginWithNewInformationUseCaseViewInterface,
        GetAllPhotosUseCaseViewInterface,
        UpdatePhotoDateUseCaseViewInterface,
        RemovePhotoUseCaseViewInterface {
}
