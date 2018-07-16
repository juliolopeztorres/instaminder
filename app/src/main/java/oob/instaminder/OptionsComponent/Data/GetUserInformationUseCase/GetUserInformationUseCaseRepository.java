package oob.instaminder.OptionsComponent.Data.GetUserInformationUseCase;

import oob.instaminder.OptionsComponent.Domain.GetUserInformationUseCase.GetUserInformationUseCaseRepositoryInterface;
import oob.instaminder.OptionsComponent.Domain.GetUserInformationUseCase.Model.UserInformation;
import oob.instaminder.Util.PreferencesWrapper;

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
