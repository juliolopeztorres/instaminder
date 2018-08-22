package oob.instaminder.AddPhotoScheduleComponent.Domain.GetNumberAdShownUseCase;

import javax.inject.Inject;

public class GetNumberAdShownUseCase {
    private GetNumberAdShownUseCaseViewRepository viewRepository;
    private GetNumberAdShownUseCaseRepositoryInterface repositoryInterface;

    @Inject
    public GetNumberAdShownUseCase(GetNumberAdShownUseCaseViewRepository viewRepository, GetNumberAdShownUseCaseRepositoryInterface repositoryInterface) {
        this.viewRepository = viewRepository;
        this.repositoryInterface = repositoryInterface;
    }

    public int get() {
        return this.repositoryInterface.get();
    }
}
