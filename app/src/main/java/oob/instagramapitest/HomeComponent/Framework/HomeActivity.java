package oob.instagramapitest.HomeComponent.Framework;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import oob.instagramapitest.ApplicationComponent.BaseApplication;
import oob.instagramapitest.HomeComponent.Framework.Adapter.PhotoCardAdapter;
import oob.instagramapitest.HomeComponent.Framework.DependencyInjection.DaggerHomeComponentInterface;
import oob.instagramapitest.HomeComponent.Framework.DependencyInjection.HomeComponentInterface;
import oob.instagramapitest.HomeComponent.Framework.DependencyInjection.HomeComponentModule;
import oob.instagramapitest.OptionsComponent.Framework.OptionsActivity;
import oob.instagramapitest.Z_Deprecated.AlarmReceiver;
import oob.instagramapitest.Z_Deprecated.Database.Model.Photo;
import oob.instagramapitest.R;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private Realm realm;

    @BindView(R.id.photoCardRecyclerView)
    RecyclerView photoCardRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        HomeComponentInterface component = DaggerHomeComponentInterface.builder()
                .baseApplicationComponentInterface(((BaseApplication) this.getApplication()).getComponent())
                .homeComponentModule(new HomeComponentModule(this))
                .build();
        component.inject(this);

        this.realm = Realm.getDefaultInstance();

        this.setUpHomeIcon();
        this.tintActionBarTextColor();
        this.setTitle("@Name");

        this.photoCardRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.photoCardRecyclerView.setAdapter(new PhotoCardAdapter());

        /*this.realm = Realm.getDefaultInstance();

        Button uploadButton = this.findViewById(R.id.uploadButton);
        uploadButton.setOnClickListener(this);*/

        /*this.realm.beginTransaction();
        this.realm.delete(Photo.class);
        this.realm.commitTransaction();*/

        // this.addMockPhotos();

        // GET PHOTO STUFF
        //List<Photo> photos = this.realm.where(Photo.class).findAll();

        // Find
        // this.realm.where(Photo.class).greaterThanOrEqualTo("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse("2018-07-02 13:00:00")).findAll()
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_home_component, menu);

        ((TextView) menu.getItem(0).getActionView().findViewById(R.id.following)).setText(String.format(this.getString(R.string.home_component_follow_followers_format), "1234", "1234"));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.startActivity(new Intent(this, OptionsActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    private void tintActionBarTextColor() {
        Toolbar actionBarToolbar = this.findViewById(R.id.action_bar);
        if (actionBarToolbar != null) {
            actionBarToolbar.setTitleTextColor(this.getResources().getColor(R.color.colorPrimary));
            actionBarToolbar.setBackgroundColor(this.getResources().getColor(R.color.colorAccentWhite));
        }
    }

    private void setUpHomeIcon() {
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar == null) {
            return;
        }

        actionBar.setHomeAsUpIndicator(R.drawable.ic_option_primary);
        actionBar.setDisplayHomeAsUpEnabled(true);
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

    instagram.sendRequest(new CustomInstagramUploadVideoRequest(video, "Amazing video", HomeActivity.this));

    video.deleteOnExit();*/
}
