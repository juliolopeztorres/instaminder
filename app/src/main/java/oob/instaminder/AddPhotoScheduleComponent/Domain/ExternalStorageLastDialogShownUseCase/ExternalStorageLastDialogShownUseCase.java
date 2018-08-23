package oob.instaminder.AddPhotoScheduleComponent.Domain.ExternalStorageLastDialogShownUseCase;

import javax.inject.Inject;

public class ExternalStorageLastDialogShownUseCase {
    private ExternalStorageLastDialogShownUseCaseViewInterface viewInterface;
    private ExternalStorageLastDialogShownUseCaseRepositoryInterface repositoryInterface;

    @Inject
    public ExternalStorageLastDialogShownUseCase(ExternalStorageLastDialogShownUseCaseViewInterface viewInterface, ExternalStorageLastDialogShownUseCaseRepositoryInterface repositoryInterface) {
        this.viewInterface = viewInterface;
        this.repositoryInterface = repositoryInterface;
    }

    public boolean check() {
        return this.repositoryInterface.check();
    }
}
