package oob.instagramapitest.HomeComponent.Domain.LoginWithNewInformationUseCase;

import javax.inject.Inject;

public class LoginWithNewInformationUseCase implements LoginWithNewInformationUseCaseRepositoryInterface.Callback {
    private LoginWithNewInformationUseCaseViewInterface viewInterface;
    private LoginWithNewInformationUseCaseRepositoryInterface repositoryInterface;

    @Inject
    public LoginWithNewInformationUseCase(LoginWithNewInformationUseCaseViewInterface viewInterface, LoginWithNewInformationUseCaseRepositoryInterface repositoryInterface) {
        this.viewInterface = viewInterface;
        this.repositoryInterface = repositoryInterface;
    }

    public void login() {
        this.repositoryInterface.login(this);
    }

    @Override
    public void onLoginSuccess() {
        this.viewInterface.onLoginSuccess();
    }

    @Override
    public void onLoginError(String message) {
        this.viewInterface.showError(message);
    }
}
