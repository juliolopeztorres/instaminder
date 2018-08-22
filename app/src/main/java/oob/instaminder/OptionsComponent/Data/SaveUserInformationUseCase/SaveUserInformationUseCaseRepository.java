package oob.instaminder.OptionsComponent.Data.SaveUserInformationUseCase;

import oob.instaminder.OptionsComponent.Domain.SaveUserInformationUseCase.SaveUserInformationUseCaseRepositoryInterface;
import oob.instaminder.Util.PreferencesWrapper;

public class SaveUserInformationUseCaseRepository implements SaveUserInformationUseCaseRepositoryInterface {

    private PreferencesWrapper preferencesWrapper;

    public SaveUserInformationUseCaseRepository(PreferencesWrapper preferencesWrapper) {
        this.preferencesWrapper = preferencesWrapper;
    }

    @Override
    public void save(String nick, String password) {
        this.preferencesWrapper.saveUserLoginInformation(nick, password);
    }
}
