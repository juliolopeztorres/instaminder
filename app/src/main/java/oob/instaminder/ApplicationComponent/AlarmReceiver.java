package oob.instaminder.ApplicationComponent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;

import dev.niekirk.com.instagram4android.Instagram4Android;
import dev.niekirk.com.instagram4android.requests.InstagramUploadPhotoRequest;
import dev.niekirk.com.instagram4android.requests.payload.InstagramLoginResult;
import io.realm.Realm;
import oob.instaminder.Util.Database.Photo;
import oob.instaminder.Util.PreferencesWrapper;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String TAG = "AlarmReceiver";

    private Context context;
    private PreferencesWrapper preferencesWrapper;
    private static String log = "-----------";

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        this.preferencesWrapper = ((BaseApplication) this.context.getApplicationContext()).getComponent().getPreferencesWrapper();
        // In order to init Realm, just in case
        ((BaseApplication) this.context.getApplicationContext()).getComponent().getRealm();

        String photoId = intent.getExtras() != null ? intent.getExtras().getString("photoId") : null;
        if (!this.isNetworkAvailable()) {
            log("onReceive: No internet available");
            updatePhotoState(photoId);
            return;
        }

        log("onReceive: Call to upload method");
        log = "-----------";
        this.uploadPhoto(photoId);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void uploadPhoto(final String photoId) {
        if (photoId == null) {
            log("doInBackground: No photoId present on the request");
            return;
        }

        new UploadAsyncTask(
                this.context,
                this.preferencesWrapper.getUsername(),
                this.preferencesWrapper.getPassword(),
                photoId
        ).execute();
    }

    private static class UploadAsyncTask extends AsyncTask<Void, Void, Void> {
        private String photoId;
        private Instagram4Android instagramAPI;

        private File cacheDir;
        private String username;
        private String password;

        UploadAsyncTask(Context context, String username, String password, String photoId) {
            this.cacheDir = context.getCacheDir();
            this.username = username;
            this.password = password;

            this.photoId = photoId;
            this.instagramAPI = this.build();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Realm realm = Realm.getDefaultInstance();
            Photo photo = realm.where(Photo.class).equalTo("id", this.photoId).findFirst();

            if (photo == null) {
                log("doInBackground: Could not find any photo");
                updatePhotoState(this.photoId);
                return null;
            }

            this.setup();
            if (!this.login()) {
                updatePhotoState(this.photoId);
                return null;
            }

            File photoCached = this.getCachedFile(photo);

            if (photoCached == null) {
                updatePhotoState(this.photoId);
                return null;
            }

            if (this.uploadPhoto(photoCached, photo.getCaption())) {
                log("doInBackground: Finished successfully");

                realm.beginTransaction();
                photo.deleteFromRealm();
                realm.commitTransaction();
                this.photoId = null;

                log("doInBackground: Photo removed from DB successfully");
            }

            log("doInBackground: Trying to delete the image...");
            if (!photoCached.delete()) {
                log("doInBackground: Problem deleting the image on Cache folder");
            }

            updatePhotoState(this.photoId);
            log("doInBackground: Process finished");
            return null;
        }

        private boolean uploadPhoto(File photoCached, String caption) {
            log("doInBackground: Uploading using API");
            try {
                this.instagramAPI.sendRequest(new InstagramUploadPhotoRequest(photoCached, caption));
                return true;
            } catch (Exception e) {
                log("doInBackground: Error uploading the photo to instagram", e);
            }

            return false;
        }

        @Nullable
        private File getCachedFile(Photo photo) {
            log("doInBackground: Creating cache image");

            File photoCached = new File(this.cacheDir + String.format("/%s.jpg", photo.getId()));
            try {
                log("doInBackground: Decode byte array and compress to JPG");

                FileOutputStream fos = new FileOutputStream(photoCached);
                Bitmap bitmap = BitmapFactory.decodeByteArray(photo.getBuffer(), 0, photo.getBuffer().length);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);

                fos.close();
            } catch (Exception e) {
                log("doInBackground: getCachedFile ERROR", e);
                return null;
            }

            return photoCached;
        }

        private Instagram4Android build() {
            return Instagram4Android.builder().username(this.username).password(this.password).build();
        }

        private void setup() {
            log("doInBackground: Setting Instagram API up");
            this.instagramAPI.setup();
        }

        private boolean login() {
            try {
                log("doInBackground: Login ...");
                InstagramLoginResult result = this.instagramAPI.login();

                if (result.getStatus().equalsIgnoreCase("ok")) {
                    return true;
                } else {
                    log("doInBackground: Login ERROR. Maybe password incorrect?");
                }
            } catch (Exception e) {
                log("doInBackground: Login ERROR", e);
            }

            return false;
        }
    }

    static void log(String message) {
        log(message, null);
    }

    static void log(String message, @Nullable Throwable throwable) {
        Log.d(TAG, message, throwable);
        log += "\n\n" + message + (throwable != null ? throwable.getMessage() : "");
    }

    static void updatePhotoState(String photoId) {
        if (photoId == null) {
            return;
        }

        Realm realm = Realm.getDefaultInstance();
        Photo photo = realm.where(Photo.class).equalTo("id", photoId).findFirst();

        if (photo == null) {
            return;
        }
        realm.beginTransaction();
        photo.setLog(log);
        photo.setState(Photo.ERROR);
        realm.commitTransaction();
    }
}
