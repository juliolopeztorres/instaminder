package oob.instaminder.HomeComponent.Data.GetInstagramUserInformationUseCase;

import dev.niekirk.com.instagram4android.requests.payload.InstagramUser;
import oob.instaminder.HomeComponent.Domain.GetInstagramUserInformationUseCase.GetInstagramUserInformationUseCaseRepositoryInterface;
import oob.instaminder.HomeComponent.Domain.GetInstagramUserInformationUseCase.Model.InstagramUserInformation;
import oob.instaminder.Util.InstagramAPI.InstagramWrapper;
import oob.instaminder.Util.PreferencesWrapper;

public class GetInstagramUserInformationUseCaseRepository implements GetInstagramUserInformationUseCaseRepositoryInterface {
    private InstagramWrapper instagramWrapper;
    private PreferencesWrapper preferencesWrapper;

    public GetInstagramUserInformationUseCaseRepository(InstagramWrapper instagramWrapper, PreferencesWrapper preferencesWrapper) {
        this.instagramWrapper = instagramWrapper;
        this.preferencesWrapper = preferencesWrapper;
    }

    @Override
    public void get(final Callback callback) {
        this.instagramWrapper.getUserInformation(this.preferencesWrapper.getUsername(), new InstagramWrapper.GetUserInformationCallback() {
            @Override
            public void onGetUserInformationSuccess(InstagramUser instagramUser) {
                callback.onGetUserInformationSuccess(new InstagramUserInformation(
                        instagramUser.getUsername(),
                        String.valueOf(instagramUser.getFollowing_count()),
                        String.valueOf(instagramUser.getFollower_count())
                ));
            }

            @Override
            public void onGetUserInformationError(String message) {
                callback.onGetUserInformationError(message);
            }
        });
    }
}
