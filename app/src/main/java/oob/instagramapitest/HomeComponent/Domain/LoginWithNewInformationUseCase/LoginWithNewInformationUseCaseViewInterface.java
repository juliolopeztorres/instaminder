package oob.instagramapitest.HomeComponent.Domain.LoginWithNewInformationUseCase;

public interface LoginWithNewInformationUseCaseViewInterface {
    void onLoginSuccess();

    void showError(String message);
}
