package oob.instaminder.Util.InstagramAPI;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import dev.niekirk.com.instagram4android.InstagramConstants;
import dev.niekirk.com.instagram4android.requests.InstagramRequest;
import dev.niekirk.com.instagram4android.requests.InstagramUploadPhotoRequest;
import dev.niekirk.com.instagram4android.requests.internal.InstagramConfigureVideoRequest;
import dev.niekirk.com.instagram4android.requests.internal.InstagramExposeRequest;
import dev.niekirk.com.instagram4android.requests.payload.InstagramUploadVideoResult;
import dev.niekirk.com.instagram4android.requests.payload.StatusResult;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class CustomInstagramUploadVideoRequest extends InstagramRequest<StatusResult> {

    @NonNull
    private File videoFile;
    @NonNull
    private String caption;

    private File thumbnailFile;
    private File defaultThumbnailFile;

    public String getUrl() {
        return "upload/video/";
    }

    public String getMethod() {
        return "POST";
    }

    public StatusResult execute() throws IOException {
        String uploadId = String.valueOf(System.currentTimeMillis());
        Request post = this.createHttpRequest(this.createMultipartBody(uploadId));
        Response response = this.api.getClient().newCall(post).execute();
        Throwable var4 = null;

        StatusResult var12;
        try {
            this.api.setLastResponse(response);
            int resultCode = response.code();
            if (response.body() == null) {
                throw new RuntimeException("Error happened in video upload session start: Body response is empty");
            }
            ResponseBody responseBody = response.body();

            String content = responseBody != null ? responseBody.string() : "NO CONTENT";
            Log.d("UPLOAD", "First phase result " + resultCode + ": " + content);
            InstagramUploadVideoResult firstPhaseResult = this.parseJson(content, InstagramUploadVideoResult.class);
            if (!firstPhaseResult.getStatus().equalsIgnoreCase("ok")) {
                throw new RuntimeException("Error happened in video upload session start: " + firstPhaseResult.getMessage());
            }

            String uploadUrl = (firstPhaseResult.getVideo_upload_urls().get(3)).get("url").toString();
            String uploadJob = (firstPhaseResult.getVideo_upload_urls().get(3)).get("job").toString();
            StatusResult uploadJobResult = this.api.sendRequest(new CustomInstagramUploadVideoJobRequest(uploadId, uploadUrl, uploadJob, this.videoFile));
            Log.d("UPLOAD", "Upload result: " + uploadJobResult);
            if (!uploadJobResult.getStatus().equalsIgnoreCase("ok")) {
                throw new RuntimeException("Error happened in video upload submit job: " + uploadJobResult.getMessage());
            }

            StatusResult thumbnailResult = this.configureThumbnail(uploadId);
            if (!thumbnailResult.getStatus().equalsIgnoreCase("ok")) {
                throw new IllegalArgumentException("Failed to configure thumbnail: " + thumbnailResult.getMessage());
            }

            var12 = this.api.sendRequest(new InstagramExposeRequest());
        } catch (Throwable var21) {
            var4 = var21;
            throw var21;
        } finally {
            if (response != null) {
                if (var4 != null) {
                    try {
                        response.close();
                    } catch (Throwable var20) {
                        var4.addSuppressed(var20);
                    }
                } else {
                    response.close();
                }
            }
        }

        return var12;
    }

    private StatusResult configureThumbnail(String uploadId) throws IOException {
        Bitmap thumbnail;
        if (this.thumbnailFile == null) {
            FileOutputStream fos = new FileOutputStream(this.defaultThumbnailFile);
            thumbnail = ThumbnailUtils.createVideoThumbnail(this.videoFile.getAbsolutePath(), 2);
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();

            this.thumbnailFile = this.defaultThumbnailFile;
        } else {
            thumbnail = this.fileToBitmap(this.thumbnailFile);
        }

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(this.videoFile.getAbsolutePath());
        long timeInMillis = Long.parseLong(retriever.extractMetadata(9));
        this.holdOn();
        StatusResult thumbnailResult = this.api.sendRequest(new InstagramUploadPhotoRequest(this.thumbnailFile, this.caption, uploadId));
        Log.d("UPLOAD", "Thumbnail result: " + thumbnailResult);
        StatusResult configureResult = this.api.sendRequest(InstagramConfigureVideoRequest.builder().uploadId(uploadId).caption(this.caption).duration(timeInMillis).width(thumbnail.getWidth()).height(thumbnail.getHeight()).build());
        Log.d("UPLOAD", "Video configure result: " + configureResult);

        this.thumbnailFile.deleteOnExit();

        return configureResult;
    }

    private MultipartBody createMultipartBody(String uploadId) throws IOException {
        return (new MultipartBody.Builder(this.api.getUuid())).setType(MultipartBody.FORM).addFormDataPart("upload_id", uploadId).addFormDataPart("_uuid", this.api.getUuid()).addFormDataPart("_csfrtoken", this.api.getOrFetchCsrf(null)).addFormDataPart("media_type", "2").build();
    }

    private Request createHttpRequest(MultipartBody body) {
        String url = "https://i.instagram.com/api/v1/" + this.getUrl();
        Log.d("UPLOAD", "URL Upload: " + url);
        return (new okhttp3.Request.Builder()).url(url).addHeader("X-IG-Capabilities", "3Q4=").addHeader("X-IG-Connection-Type", "WIFI").addHeader("Cookie2", "$Version=1").addHeader("Accept-Language", "en-US").addHeader("Host", "i.instagram.com").addHeader("Connection", "close").addHeader("User-Agent", InstagramConstants.USER_AGENT).post(body).build();
    }

    private void holdOn() {
        try {
            TimeUnit.SECONDS.sleep(5L);
        } catch (InterruptedException var2) {
            var2.printStackTrace();
        }
    }

    public String getPayload() {
        return null;
    }

    public StatusResult parseResult(int statusCode, String content) {
        return this.parseJson(statusCode, content, StatusResult.class);
    }

    private Bitmap fileToBitmap(File image) {
        return BitmapFactory.decodeFile(image.getAbsolutePath());
    }

    public CustomInstagramUploadVideoRequest(@NonNull File videoFile, @NonNull String caption, File thumbnailFile) {
        this.videoFile = videoFile;
        this.caption = caption;
        this.thumbnailFile = thumbnailFile;
    }

    public CustomInstagramUploadVideoRequest(@NonNull File videoFile, @NonNull String caption, @NonNull Context context) {
        this.videoFile = videoFile;
        this.caption = caption;
        this.defaultThumbnailFile = new File(context.getCacheDir() + "/example_video_thumbnail_cached.jpg");
    }
}
