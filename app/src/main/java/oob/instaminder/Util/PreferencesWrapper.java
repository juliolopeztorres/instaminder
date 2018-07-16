package oob.instaminder.Util;

import android.content.SharedPreferences;
import android.util.Base64;

public class PreferencesWrapper {
    private String PREFERENCES_NICK_KEY = "preferences_nick_key";
    private String PREFERENCES_PASSWORD_KEY = "preferences_password_key";

    private SharedPreferences preferences;

    public PreferencesWrapper(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public String getUsername() {
        return this.decode(this.preferences.getString(PREFERENCES_NICK_KEY, ""));
    }

    public String getPassword() {
        return this.decode(this.preferences.getString(PREFERENCES_PASSWORD_KEY, ""));
    }

    public void saveUserLoginInformation(String nick, String password) {
        this.preferences.edit()
                .putString(PREFERENCES_NICK_KEY, this.encode(nick))
                .putString(PREFERENCES_PASSWORD_KEY, this.encode(password))
                .apply();
    }

    private String encode(String toEncode) {
        return new String(Base64.encode(toEncode.getBytes(), Base64.DEFAULT));
    }

    private String decode(String toDecode) {
        return new String(Base64.decode(toDecode.getBytes(), Base64.DEFAULT));
    }
}
