package oob.instagramapitest.OptionsComponent.Domain.SaveUserInformationUseCase;

import javax.inject.Inject;

public class SaveUserInformationUseCase {
    private SaveUserInformationUseCaseViewInterface viewInterface;
    private SaveUserInformationUseCaseRepositoryInterface repositoryInterface;

    @Inject
    public SaveUserInformationUseCase(SaveUserInformationUseCaseViewInterface viewInterface, SaveUserInformationUseCaseRepositoryInterface repositoryInterface) {
        this.viewInterface = viewInterface;
        this.repositoryInterface = repositoryInterface;
    }

    public void save(String nick, String password) {
        this.repositoryInterface.save(nick, password);
    }
}
