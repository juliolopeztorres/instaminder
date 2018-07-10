package oob.instagramapitest.Util;

import android.content.SharedPreferences;

public class PreferencesWrapper {
    private String PREFERENCES_NICK_KEY = "preferences_nick_key";
    private String PREFERENCES_PASSWORD_KEY = "preferences_password_key";

    private SharedPreferences preferences;

    public PreferencesWrapper(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public String getUsername() {
        return this.preferences.getString(PREFERENCES_NICK_KEY, "");
    }

    public String getPassword() {
        return this.preferences.getString(PREFERENCES_PASSWORD_KEY, "");
    }

    public void saveUserLoginInformation(String nick, String password) {
        this.preferences.edit()
                .putString(PREFERENCES_NICK_KEY, nick)
                .putString(PREFERENCES_PASSWORD_KEY, password)
                .apply();
    }
}
