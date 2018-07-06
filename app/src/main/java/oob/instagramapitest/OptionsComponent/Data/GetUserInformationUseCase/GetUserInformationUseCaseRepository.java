package oob.instagramapitest.OptionsComponent.Data.GetUserInformationUseCase;

import oob.instagramapitest.OptionsComponent.Domain.GetUserInformationUseCase.GetUserInformationUseCaseRepositoryInterface;
import oob.instagramapitest.OptionsComponent.Domain.GetUserInformationUseCase.Model.UserInformation;
import oob.instagramapitest.Util.PreferencesWrapper;

public class GetUserInformationUseCaseRepository implements GetUserInformationUseCaseRepositoryInterface {

    private PreferencesWrapper preferencesWrapper;

    public GetUserInformationUseCaseRepository(PreferencesWrapper preferencesWrapper) {
        this.preferencesWrapper = preferencesWrapper;
    }

    @Override
    public UserInformation get() {
        return new UserInformation(
                this.preferencesWrapper.getUsername(),
                this.preferencesWrapper.getPassword()
        );
    }
}
