package oob.instagramapitest.OptionsComponent.Data.SaveUserInformationUseCase;

import oob.instagramapitest.OptionsComponent.Domain.SaveUserInformationUseCase.SaveUserInformationUseCaseRepositoryInterface;
import oob.instagramapitest.Util.PreferencesWrapper;

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
