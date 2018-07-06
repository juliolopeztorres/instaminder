package oob.instagramapitest.HomeComponent.Framework;

import android.app.Activity;
import android.content.DialogInterface;
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

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import oob.instagramapitest.ApplicationComponent.BaseApplication;
import oob.instagramapitest.HomeComponent.Domain.CheckNickPasswordStoredUseCase.CheckNickPasswordStoredUseCase;
import oob.instagramapitest.HomeComponent.Domain.GetInstagramUserInformationUseCase.GetInstagramUserInformationUseCase;
import oob.instagramapitest.HomeComponent.Domain.GetInstagramUserInformationUseCase.Model.InstagramUserInformation;
import oob.instagramapitest.HomeComponent.Domain.LoginWithNewInformationUseCase.LoginWithNewInformationUseCase;
import oob.instagramapitest.HomeComponent.Domain.ViewInterface;
import oob.instagramapitest.HomeComponent.Framework.Adapter.PhotoCardAdapter;
import oob.instagramapitest.HomeComponent.Framework.DependencyInjection.DaggerHomeComponentInterface;
import oob.instagramapitest.HomeComponent.Framework.DependencyInjection.HomeComponentInterface;
import oob.instagramapitest.HomeComponent.Framework.DependencyInjection.HomeComponentModule;
import oob.instagramapitest.OptionsComponent.Framework.OptionsActivity;
import oob.instagramapitest.Util.DialogUtil;
import oob.instagramapitest.R;

public class HomeActivity extends AppCompatActivity implements ViewInterface {
    private static final String TAG = "HomeActivity";

    @BindView(R.id.photoCardRecyclerView)
    RecyclerView photoCardRecyclerView;

    @Inject
    CheckNickPasswordStoredUseCase checkNickPasswordStoredUseCase;
    @Inject
    GetInstagramUserInformationUseCase getInstagramUserInformationUseCase;
    @Inject
    LoginWithNewInformationUseCase loginWithNewInformationUseCase;

    private View followersIndicator;

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

        // this.realm = Realm.getDefaultInstance();

        this.init();

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

    private void init() {
        this.setUpHomeIcon();
        this.tintActionBarTextColor();

        this.setUpPhotoCardList();

        if (!this.checkNickPasswordStoredUseCase.check()) {
            this.goToOptionsComponent();
            return;
        }

        this.loginWithNewInformationUseCase.login();
    }

    private void setUpPhotoCardList() {
        this.photoCardRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.photoCardRecyclerView.setAdapter(new PhotoCardAdapter());
    }

    private void goToOptionsComponent() {
        this.startActivityForResult(new Intent(this, OptionsActivity.class), OptionsActivity.REQUEST_CODE_SAVE_USER_INFORMATION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OptionsActivity.REQUEST_CODE_SAVE_USER_INFORMATION) {
            if (resultCode == Activity.RESULT_OK) {
                this.loginWithNewInformationUseCase.login();
            } else {
                DialogUtil.showAlertDialog(
                        this,
                        this.getString(R.string.home_component_dialog_user_info_warning_title),
                        this.getString(R.string.home_component_dialog_user_info_error_message),
                        this.getString(R.string.home_component_dialog_user_info_action_label),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                HomeActivity.this.goToOptionsComponent();
                            }
                        },
                        true);
            }

            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onLoginSuccess() {
        this.getInstagramUserInformationUseCase.get();
    }

    @Override
    public void showError(final String message) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogUtil.showAlertDialog(
                        HomeActivity.this,
                        HomeActivity.this.getString(R.string.home_component_dialog_user_info_error_title),
                        message,
                        HomeActivity.this.getString(android.R.string.ok)
                );
            }
        });
    }

    @Override
    public void onGetUserInformationSuccess(final InstagramUserInformation instagramUserInformation) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                HomeActivity.this.setTitle("@" + instagramUserInformation.getNick());

                if (HomeActivity.this.followersIndicator != null) {
                    ((TextView) HomeActivity.this.followersIndicator.findViewById(R.id.following))
                            .setText(
                                    String.format(HomeActivity.this.getString(R.string.home_component_follow_followers_format), instagramUserInformation.getFollowing(), instagramUserInformation.getFollowers())
                            );
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_home_component, menu);

        this.followersIndicator = menu.getItem(0).getActionView();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.goToOptionsComponent();
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

    /*private void addMockPhotos() {
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
    }*/

    /*@Override
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
    }*/

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
