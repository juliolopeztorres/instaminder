package oob.instagramapitest.Util;

import android.support.annotation.Nullable;
import android.util.Log;

public class LogUtil {
    private static final String TAG = "LogUtil";

    public static void log(String message) {
        log(message, null);
    }

    public static void log(String message, @Nullable Throwable throwable) {
        Log.d(TAG, message, throwable);
    }
}
