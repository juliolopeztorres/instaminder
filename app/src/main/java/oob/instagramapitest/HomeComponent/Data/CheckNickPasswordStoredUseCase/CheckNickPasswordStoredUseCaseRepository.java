package oob.instagramapitest.HomeComponent.Data.CheckNickPasswordStoredUseCase;

import oob.instagramapitest.HomeComponent.Domain.CheckNickPasswordStoredUseCase.CheckNickPasswordStoredUseCaseRepositoryInterface;
import oob.instagramapitest.Util.PreferencesWrapper;

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
