package oob.instagramapitest.OptionsComponent.Data.SaveUserInformationUseCase;

import android.content.SharedPreferences;

import oob.instagramapitest.ApplicationComponent.ApplicationConstant;
import oob.instagramapitest.OptionsComponent.Domain.SaveUserInformationUseCase.SaveUserInformationUseCaseRepositoryInterface;

public class SaveUserInformationUseCaseRepository implements SaveUserInformationUseCaseRepositoryInterface {

    private SharedPreferences preferences;

    public SaveUserInformationUseCaseRepository(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    @Override
    public void save(String nick, String password) {
        this.preferences.edit()
                .putString(ApplicationConstant.PREFERENCES_NICK_KEY, nick)
                .putString(ApplicationConstant.PREFERENCES_PASSWORD_KEY, password)
                .apply();
    }
}
