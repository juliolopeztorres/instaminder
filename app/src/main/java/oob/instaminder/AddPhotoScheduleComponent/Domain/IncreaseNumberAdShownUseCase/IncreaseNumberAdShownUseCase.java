package oob.instaminder.AddPhotoScheduleComponent.Domain.IncreaseNumberAdShownUseCase;

import javax.inject.Inject;

public class IncreaseNumberAdShownUseCase {
    private IncreaseNumberAdShownUseCaseViewInterface viewInterface;
    private IncreaseNumberAdShownUseCaseRepositoryInterface repositoryInterface;

    @Inject
    public IncreaseNumberAdShownUseCase(IncreaseNumberAdShownUseCaseViewInterface viewInterface, IncreaseNumberAdShownUseCaseRepositoryInterface repositoryInterface) {
        this.viewInterface = viewInterface;
        this.repositoryInterface = repositoryInterface;
    }

    public void increase() {
        this.repositoryInterface.increase();
    }
}
