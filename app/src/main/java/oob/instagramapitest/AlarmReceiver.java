package oob.instagramapitest;

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
import java.io.IOException;

import dev.niekirk.com.instagram4android.Instagram4Android;
import dev.niekirk.com.instagram4android.requests.InstagramUploadPhotoRequest;
import io.realm.Realm;
import oob.instagramapitest.Model.Database.Photo;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String TAG = "AlarmReceiver";

    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;

        if (!this.isNetworkAvailable()) {
            log("onReceive: No internet available");
            return;
        }

        log("onReceive: Call to upload method");
        this.uploadPhoto(intent.getExtras() != null ? intent.getExtras().getString("photoId") : null);
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
                this.context.getResources().getString(R.string.username), // TODO: Inject/Recover values dynamically
                this.context.getResources().getString(R.string.password),
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
                return null;
            }

            this.setup();
            if (!this.login()) {
                return null;
            }

            File photoCached = this.getCachedFile(photo);

            if (photoCached == null) {
                return null;
            }

            if (this.uploadPhoto(photoCached, photo.getCaption())) {
                log("doInBackground: Finished successfully");

                realm.beginTransaction();
                photo.deleteFromRealm();
                realm.commitTransaction();

                log("doInBackground: Photo removed from DB successfully");
            }

            log("doInBackground: Mark image to be deleted when turning the device off");
            photoCached.deleteOnExit();

            return null;
        }

        private boolean uploadPhoto(File photoCached, String caption) {
            log("doInBackground: Uploading using API");
            try {
                this.instagramAPI.sendRequest(new InstagramUploadPhotoRequest(photoCached, caption));
                return true;
            } catch (IOException e) {
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
                Bitmap bitmap = BitmapFactory.decodeByteArray(photo.getFileBuffer(), 0, photo.getFileBuffer().length);
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
                this.instagramAPI.login();

                return true;
            } catch (IOException e) {
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
    }
}
