package oob.instaminder.HomeComponent.Domain.LoginWithNewInformationUseCase;

public interface LoginWithNewInformationUseCaseViewInterface {
    void onLoginSuccess();

    void showError(String message);
}
