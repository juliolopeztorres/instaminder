package oob.instagramapitest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import io.realm.Realm;
import oob.instagramapitest.Model.Database.Photo;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.realm = Realm.getDefaultInstance();

        Button uploadButton = this.findViewById(R.id.uploadButton);
        uploadButton.setOnClickListener(this);

        /*this.realm.beginTransaction();
        this.realm.delete(Photo.class);
        this.realm.commitTransaction();*/

        // this.addMockPhotos();

        // GET PHOTO STUFF
        //List<Photo> photos = this.realm.where(Photo.class).findAll();

        // Find
        // this.realm.where(Photo.class).greaterThanOrEqualTo("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse("2018-07-02 13:00:00")).findAll()
    }

    private void addMockPhotos() {
        final Photo photo = new Photo();
        final Photo photo2 = new Photo();

        byte[] buffer;

        try {
            InputStream is = getAssets().open("example_image.jpg");
            buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        photo.setId(UUID.randomUUID().toString());
        photo.setFileBuffer(buffer);
        photo.setCaption("Awesome photo");

        photo2.setId(UUID.randomUUID().toString());
        photo2.setFileBuffer(buffer);
        photo2.setCaption("HEY YO");

        try {
            photo.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse("2018-07-03 16:22:00"));
            photo2.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse("2018-07-03 14:30:00"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<Photo> photos = new ArrayList<Photo>() {{
            add(photo);
            add(photo2);
        }};

        this.realm.beginTransaction();
        this.realm.insertOrUpdate(photos);
        this.realm.commitTransaction();
    }

    @Override
    public void onClick(View v) {
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);

        Photo photo = this.realm.where(Photo.class).findFirst();

        alarmIntent.putExtra("photoId", photo.getId());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (manager == null) {
            Toast.makeText(this, "Could not scheduled the upload as there is no Alarm manager on Device", Toast.LENGTH_LONG).show();
            return;
        }

        manager.setAlarmClock(new AlarmManager.AlarmClockInfo(
                photo.getDate().getTime()
                // Calendar.getInstance().getTimeInMillis() + 5 * 60 * 1000
                , null), pendingIntent);

        Toast.makeText(this, "Photo Scheduled correctly. Wait for it", Toast.LENGTH_LONG).show();
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

    instagram.sendRequest(new CustomInstagramUploadVideoRequest(video, "Amazing video", MainActivity.this));

    video.deleteOnExit();*/
}
