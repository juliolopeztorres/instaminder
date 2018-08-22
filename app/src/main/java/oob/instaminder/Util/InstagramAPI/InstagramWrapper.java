package oob.instaminder.Util.InstagramAPI;

import android.os.AsyncTask;

import java.io.File;
import java.io.IOException;

import dev.niekirk.com.instagram4android.Instagram4Android;
import dev.niekirk.com.instagram4android.requests.InstagramSearchUsernameRequest;
import dev.niekirk.com.instagram4android.requests.InstagramUploadPhotoRequest;
import dev.niekirk.com.instagram4android.requests.payload.InstagramLoginResult;
import dev.niekirk.com.instagram4android.requests.payload.InstagramSearchUsernameResult;
import dev.niekirk.com.instagram4android.requests.payload.InstagramUser;
import oob.instaminder.Util.LogUtil;

public class InstagramWrapper {
    private Instagram4Android instagramAPI;

    public void loginWithNewInformation(String username, String password, LoginCallback loginCallback) {
        this.instagramAPI = Instagram4Android.builder().username(username).password(password).build();
        this.instagramAPI.setup();
        this.login(loginCallback);
    }

    public interface LoginCallback {
        void onLoginSuccess();

        void onLoginError(String message);
    }

    private void login(final LoginCallback loginCallback) {
        new NetworkAsyncTask(new Task() {
            @Override
            public void execute() {
                try {
                    InstagramLoginResult instagramLoginResult = InstagramWrapper.this.instagramAPI.login();
                    if (instagramLoginResult.getStatus().equalsIgnoreCase("ok")) {
                        loginCallback.onLoginSuccess();
                        return;
                    }

                    loginCallback.onLoginError(instagramLoginResult.getMessage());
                } catch (IOException e) {
                    LogUtil.log("Login ERROR", e);
                    loginCallback.onLoginError(e.getMessage());
                }
            }
        }).execute();
    }

    public interface GetUserInformationCallback {
        void onGetUserInformationSuccess(InstagramUser instagramUser);

        void onGetUserInformationError(String message);
    }

    public void getUserInformation(final String username, final GetUserInformationCallback callback) {
        new NetworkAsyncTask(new Task() {
            @Override
            public void execute() {
                try {
                    InstagramSearchUsernameResult result = InstagramWrapper.this.instagramAPI.sendRequest(new InstagramSearchUsernameRequest(username));
                    callback.onGetUserInformationSuccess(result.getUser());
                } catch (IOException e) {
                    LogUtil.log("Get User Information ERROR", e);
                    callback.onGetUserInformationError(e.getMessage());
                }
            }
        }).execute();
    }

    public boolean uploadPhoto(File photoCached, String caption) {
        LogUtil.log("Uploading using API");
        try {
            this.instagramAPI.sendRequest(new InstagramUploadPhotoRequest(photoCached, caption));
            return true;
        } catch (IOException e) {
            LogUtil.log("Error uploading the photo to instagram", e);
        }

        return false;
    }

    private interface Task {
        void execute();
    }

    private static class NetworkAsyncTask extends AsyncTask<Void, Void, Void> {
        private Task task;

        NetworkAsyncTask(Task task) {
            this.task = task;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            this.task.execute();

            return null;
        }
    }
}

// Upload a video
    /*File video = new File(getCacheDir() + "/example_video_cached.mp4");
    if (!video.exists())
        try {

            InputStream is = getAssets().open("example_video.mp4");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();


            FileOutputStream fos = new FileOutputStream(video);
            fos.write(buffer);
            fos.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    instagram.sendRequest(new CustomInstagramUploadVideoRequest(video, "Amazing video", HomeActivity.this));

    video.deleteOnExit();*/