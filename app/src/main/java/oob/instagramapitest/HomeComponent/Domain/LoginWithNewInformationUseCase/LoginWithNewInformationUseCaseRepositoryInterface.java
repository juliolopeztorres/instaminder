package oob.instagramapitest.HomeComponent.Domain.LoginWithNewInformationUseCase;

public interface LoginWithNewInformationUseCaseRepositoryInterface {
    void login(Callback callback);

    interface Callback {
        void onLoginSuccess();

        void onLoginError(String message);
    }
}
