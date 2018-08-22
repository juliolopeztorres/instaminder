package oob.instaminder.AddPhotoScheduleComponent.Data.GetNumberAdShownUseCase;

import oob.instaminder.AddPhotoScheduleComponent.Domain.GetNumberAdShownUseCase.GetNumberAdShownUseCaseRepositoryInterface;
import oob.instaminder.Util.PreferencesWrapper;

public class GetNumberAdShownUseCaseRepository implements GetNumberAdShownUseCaseRepositoryInterface {
    private PreferencesWrapper preferencesWrapper;

    public GetNumberAdShownUseCaseRepository(PreferencesWrapper preferencesWrapper) {
        this.preferencesWrapper = preferencesWrapper;
    }

    @Override
    public int get() {
        return this.preferencesWrapper.getNumberAdShown();
    }
}
