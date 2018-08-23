package oob.instaminder.AddPhotoScheduleComponent.Domain.MarkExternalStorageLastDialogAsShownUseCase;

import javax.inject.Inject;

public class MarkExternalStorageLastDialogAsShownUseCase {
    private MarkExternalStorageLastDialogAsShownUseCaseViewInterface viewInterface;
    private MarkExternalStorageLastDialogAsShownUseCaseRepositoryInterface repositoryInterface;

    @Inject
    public MarkExternalStorageLastDialogAsShownUseCase(MarkExternalStorageLastDialogAsShownUseCaseViewInterface viewInterface, MarkExternalStorageLastDialogAsShownUseCaseRepositoryInterface repositoryInterface) {
        this.viewInterface = viewInterface;
        this.repositoryInterface = repositoryInterface;
    }

    public void mark() {
        this.repositoryInterface.mark();
    }
}
