package oob.instaminder.HomeComponent.Data.LoginWithNewInformationUseCase;

import oob.instaminder.HomeComponent.Domain.LoginWithNewInformationUseCase.LoginWithNewInformationUseCaseRepositoryInterface;
import oob.instaminder.Util.InstagramAPI.InstagramWrapper;
import oob.instaminder.Util.PreferencesWrapper;

public class LoginWithNewInformationUseCaseRepository implements LoginWithNewInformationUseCaseRepositoryInterface {
    private InstagramWrapper instagramWrapper;
    private PreferencesWrapper preferencesWrapper;

    public LoginWithNewInformationUseCaseRepository(InstagramWrapper instagramWrapper, PreferencesWrapper preferencesWrapper) {
        this.instagramWrapper = instagramWrapper;
        this.preferencesWrapper = preferencesWrapper;
    }

    @Override
    public void login(final Callback callback) {
        this.instagramWrapper.loginWithNewInformation(
                this.preferencesWrapper.getUsername(),
                this.preferencesWrapper.getPassword(),
                new InstagramWrapper.LoginCallback() {
                    @Override
                    public void onLoginSuccess() {
                        callback.onLoginSuccess();
                    }

                    @Override
                    public void onLoginError(String message) {
                        callback.onLoginError(message);
                    }
                });
    }
}
