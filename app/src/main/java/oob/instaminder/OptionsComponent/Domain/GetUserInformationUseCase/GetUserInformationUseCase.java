package oob.instaminder.OptionsComponent.Domain.GetUserInformationUseCase;

import javax.inject.Inject;

import oob.instaminder.OptionsComponent.Domain.GetUserInformationUseCase.Model.UserInformation;

public class GetUserInformationUseCase {
    private GetUserInformationUseCaseViewInterface viewInterface;
    private GetUserInformationUseCaseRepositoryInterface repositoryInterface;

    @Inject
    public GetUserInformationUseCase(GetUserInformationUseCaseViewInterface viewInterface, GetUserInformationUseCaseRepositoryInterface repositoryInterface) {
        this.viewInterface = viewInterface;
        this.repositoryInterface = repositoryInterface;
    }

    public UserInformation get() {
        return this.repositoryInterface.get();
    }
}
