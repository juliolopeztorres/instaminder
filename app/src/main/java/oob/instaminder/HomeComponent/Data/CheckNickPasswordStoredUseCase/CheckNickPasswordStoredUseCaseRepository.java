package oob.instaminder.HomeComponent.Data.CheckNickPasswordStoredUseCase;

import oob.instaminder.HomeComponent.Domain.CheckNickPasswordStoredUseCase.CheckNickPasswordStoredUseCaseRepositoryInterface;
import oob.instaminder.Util.PreferencesWrapper;

public class CheckNickPasswordStoredUseCaseRepository implements CheckNickPasswordStoredUseCaseRepositoryInterface {

    private PreferencesWrapper preferencesWrapper;

    public CheckNickPasswordStoredUseCaseRepository(PreferencesWrapper preferencesWrapper) {
        this.preferencesWrapper = preferencesWrapper;
    }

    @Override
    public boolean check() {
        return !this.preferencesWrapper.getUsername().isEmpty();
    }
}
