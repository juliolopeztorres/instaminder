package oob.instaminder.AddPhotoScheduleComponent.Data.ExternalStorageLastDialogShownUseCase;

import oob.instaminder.AddPhotoScheduleComponent.Domain.ExternalStorageLastDialogShownUseCase.ExternalStorageLastDialogShownUseCaseRepositoryInterface;
import oob.instaminder.Util.PreferencesWrapper;

public class ExternalStorageLastDialogShownUseCaseRepository implements ExternalStorageLastDialogShownUseCaseRepositoryInterface {
    private PreferencesWrapper preferencesWrapper;

    public ExternalStorageLastDialogShownUseCaseRepository(PreferencesWrapper preferencesWrapper) {
        this.preferencesWrapper = preferencesWrapper;
    }

    @Override
    public boolean check() {
        return this.preferencesWrapper.externalStorageLastDialogWasShown();
    }
}
