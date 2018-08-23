package oob.instaminder.AddPhotoScheduleComponent.Data.MarkExternalStorageLastDialogAsShownUseCase;

import oob.instaminder.AddPhotoScheduleComponent.Domain.MarkExternalStorageLastDialogAsShownUseCase.MarkExternalStorageLastDialogAsShownUseCaseRepositoryInterface;
import oob.instaminder.Util.PreferencesWrapper;

public class MarkExternalStorageLastDialogAsShownUseCaseRepository implements MarkExternalStorageLastDialogAsShownUseCaseRepositoryInterface {
    private PreferencesWrapper preferencesWrapper;

    public MarkExternalStorageLastDialogAsShownUseCaseRepository(PreferencesWrapper preferencesWrapper) {
        this.preferencesWrapper = preferencesWrapper;
    }

    @Override
    public void mark() {
        this.preferencesWrapper.markExternalStorageDialogAsShown();
    }
}
