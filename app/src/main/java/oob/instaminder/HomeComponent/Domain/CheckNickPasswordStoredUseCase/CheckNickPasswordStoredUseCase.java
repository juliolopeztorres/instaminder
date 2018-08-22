package oob.instaminder.HomeComponent.Domain.CheckNickPasswordStoredUseCase;

import javax.inject.Inject;

public class CheckNickPasswordStoredUseCase {
    private CheckNickPasswordStoredUseCaseViewInterface viewInterface;
    private CheckNickPasswordStoredUseCaseRepositoryInterface repositoryInterface;

    @Inject
    public CheckNickPasswordStoredUseCase(CheckNickPasswordStoredUseCaseViewInterface viewInterface, CheckNickPasswordStoredUseCaseRepositoryInterface repositoryInterface) {
        this.viewInterface = viewInterface;
        this.repositoryInterface = repositoryInterface;
    }

    public boolean check() {
        return this.repositoryInterface.check();
    }
}
