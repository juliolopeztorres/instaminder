package oob.instagramapitest.HomeComponent.Data.CheckNickPasswordStoredUseCase;

import android.content.SharedPreferences;

import oob.instagramapitest.ApplicationComponent.ApplicationConstant;
import oob.instagramapitest.HomeComponent.Domain.CheckNickPasswordStoredUseCase.CheckNickPasswordStoredUseCaseRepositoryInterface;

public class CheckNickPasswordStoredUseCaseRepository implements CheckNickPasswordStoredUseCaseRepositoryInterface {

    private SharedPreferences preferences;

    public CheckNickPasswordStoredUseCaseRepository(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    @Override
    public boolean check() {
        return !this.preferences.getString(ApplicationConstant.PREFERENCES_NICK_KEY, "").isEmpty();
    }
}
