package oob.instagramapitest.ApplicationComponent.DependencyInjection;

import android.content.Context;
import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;
import oob.instagramapitest.Util.PreferencesWrapper;

@Module
public class PreferencesModule {
    private Context context;
    private SharedPreferences preferences;

    public PreferencesModule(Context context, String preferencesName) {
        this.context = context;
        this.preferences = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE);
    }

    @Provides
    @BaseApplicationScopeInterface
    Context provideContext() {
        return this.context;
    }

    @Provides
    @BaseApplicationScopeInterface
    PreferencesWrapper providePreferencesWrapper() {
        return new PreferencesWrapper(this.preferences);
    }
}
