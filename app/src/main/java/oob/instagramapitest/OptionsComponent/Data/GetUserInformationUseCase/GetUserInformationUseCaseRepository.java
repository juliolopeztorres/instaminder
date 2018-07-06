package oob.instagramapitest.OptionsComponent.Data.GetUserInformationUseCase;

import android.content.SharedPreferences;

import oob.instagramapitest.ApplicationComponent.ApplicationConstant;
import oob.instagramapitest.OptionsComponent.Domain.GetUserInformationUseCase.GetUserInformationUseCaseRepositoryInterface;
import oob.instagramapitest.OptionsComponent.Domain.GetUserInformationUseCase.Model.UserInformation;

public class GetUserInformationUseCaseRepository implements GetUserInformationUseCaseRepositoryInterface {

    private SharedPreferences preferences;

    public GetUserInformationUseCaseRepository(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    @Override
    public UserInformation get() {
        return new UserInformation(
                this.preferences.getString(ApplicationConstant.PREFERENCES_NICK_KEY, ""),
                this.preferences.getString(ApplicationConstant.PREFERENCES_PASSWORD_KEY, "")
        );
    }
}
