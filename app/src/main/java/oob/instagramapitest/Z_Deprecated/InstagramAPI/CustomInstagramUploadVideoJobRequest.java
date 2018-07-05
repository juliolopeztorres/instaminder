package oob.instagramapitest.Z_Deprecated.InstagramAPI;

import android.util.Log;

import java.io.File;
import java.io.IOException;

import dev.niekirk.com.instagram4android.InstagramConstants;
import dev.niekirk.com.instagram4android.requests.InstagramRequest;
import dev.niekirk.com.instagram4android.requests.internal.InstagramUploadVideoJobRequest;
import dev.niekirk.com.instagram4android.requests.payload.StatusResult;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class CustomInstagramUploadVideoJobRequest extends InstagramRequest<StatusResult> {
    private final String TAG = InstagramUploadVideoJobRequest.class.getCanonicalName();
    private String uploadId;
    private String uploadUrl;
    private String uploadJob;
    private File videoFile;

    CustomInstagramUploadVideoJobRequest(String uploadId, String uploadUrl, String uploadJob, File videoFile) {
        this.uploadId = uploadId;
        this.uploadUrl = uploadUrl;
        this.uploadJob = uploadJob;
        this.videoFile = videoFile;
    }

    public String getUrl() {
        return this.uploadUrl;
    }

    public String getMethod() {
        return "POST";
    }

    private Request createHTTPRequest() {
        return (new okhttp3.Request.Builder()).url(this.getUrl())
                .addHeader("X-IG-Capabilities", "3Q4=")
                .addHeader("X-IG-Connection-Type", "WIFI")
                .addHeader("Cookie2", "$Version=1")
                .addHeader("Accept-Language", "en-US")
                .addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("Session-ID", uploadId)
                .addHeader("Connection", "keep-alive")
                .addHeader("job", uploadJob)
                .addHeader("Host", "upload.instagram.com")
                .addHeader("User-Agent", InstagramConstants.USER_AGENT)
                .post(createRequestBody())
                .build();
    }

    private RequestBody createRequestBody() {
        return new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addPart(Headers.of("Content-Disposition", "attachment; filename=\"video.mp4\""),
                        RequestBody.create(MediaType.parse("video/mp4"), videoFile)
                ).build();
    }

    public StatusResult execute() throws IOException {
        String url = getUrl();
        Log.i(TAG, "Upload URL: " + url);
        Response response = this.api.getClient().newCall(createHTTPRequest()).execute();

        this.api.setLastResponse(response);
        int resultCode = response.code();
        ResponseBody responseBody = response.body();

        String content = responseBody != null ? responseBody.string() : "NO CONTENT";
        Log.d(TAG, "MultiPart upload results: [" + resultCode + "] " + content);

        if (resultCode != 200 && resultCode != 201) {
            throw new IllegalStateException("Failed uploading video (" + resultCode + "): " + content);
        }
        return new StatusResult("ok");
    }

    public String getPayload() {
        return null;
    }

    public StatusResult parseResult(int statusCode, String content) {
        return this.parseJson(statusCode, content, StatusResult.class);
    }
}
