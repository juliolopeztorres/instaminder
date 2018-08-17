package oob.instaminder.AddPhotoScheduleComponent.Data.IncreaseNumberAdShownUseCase;

import oob.instaminder.AddPhotoScheduleComponent.Domain.IncreaseNumberAdShownUseCase.IncreaseNumberAdShownUseCaseRepositoryInterface;
import oob.instaminder.Util.PreferencesWrapper;

public class IncreaseNumberAdShownUseCaseRepository implements IncreaseNumberAdShownUseCaseRepositoryInterface {
    private PreferencesWrapper preferencesWrapper;

    public IncreaseNumberAdShownUseCaseRepository(PreferencesWrapper preferencesWrapper) {
        this.preferencesWrapper = preferencesWrapper;
    }

    @Override
    public void increase() {
        this.preferencesWrapper.increaseNumberAdShown();
    }
}
