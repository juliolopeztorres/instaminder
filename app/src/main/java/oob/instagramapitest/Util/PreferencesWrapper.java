package oob.instagramapitest.Util;

import android.content.SharedPreferences;

import oob.instagramapitest.ApplicationComponent.ApplicationConstant;

public class PreferencesWrapper {
    private SharedPreferences preferences;

    public PreferencesWrapper(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public String getUsername() {
        return this.preferences.getString(ApplicationConstant.PREFERENCES_NICK_KEY, "");
    }

    public String getPassword() {
        return this.preferences.getString(ApplicationConstant.PREFERENCES_PASSWORD_KEY, "");
    }

    public void saveUserLoginInformation(String nick, String password) {
        this.preferences.edit()
                .putString(ApplicationConstant.PREFERENCES_NICK_KEY, nick)
                .putString(ApplicationConstant.PREFERENCES_PASSWORD_KEY, password)
                .apply();
    }
}
