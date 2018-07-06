package oob.instagramapitest.ApplicationComponent.DependencyInjection;

import android.content.Context;
import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;

@Module
public class PreferencesModule {
    private Context context;
    private String preferencesName;

    public PreferencesModule(Context context, String preferencesName) {
        this.context = context;
        this.preferencesName = preferencesName;
    }

    @Provides
    @BaseApplicationScopeInterface
    SharedPreferences providePreferences() {
        return this.context.getSharedPreferences(this.preferencesName, Context.MODE_PRIVATE);
    }
}
