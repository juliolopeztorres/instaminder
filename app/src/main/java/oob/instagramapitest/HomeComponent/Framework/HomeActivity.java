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

import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import oob.instagramapitest.AddPhotoScheduleComponent.Framework.AddPhotoScheduleActivity;
import oob.instagramapitest.ApplicationComponent.BaseApplication;
import oob.instagramapitest.HomeComponent.Domain.CheckNickPasswordStoredUseCase.CheckNickPasswordStoredUseCase;
import oob.instagramapitest.HomeComponent.Domain.GetAllPhotosUseCase.GetAllPhotosUseCase;
import oob.instagramapitest.HomeComponent.Domain.GetAllPhotosUseCase.Model.Photo;
import oob.instagramapitest.HomeComponent.Domain.GetInstagramUserInformationUseCase.GetInstagramUserInformationUseCase;
import oob.instagramapitest.HomeComponent.Domain.GetInstagramUserInformationUseCase.Model.InstagramUserInformation;
import oob.instagramapitest.HomeComponent.Domain.LoginWithNewInformationUseCase.LoginWithNewInformationUseCase;
import oob.instagramapitest.HomeComponent.Domain.RemovePhotoUseCase.RemovePhotoUseCase;
import oob.instagramapitest.HomeComponent.Domain.UpdatePhotoDateUseCase.UpdatePhotoDateUseCase;
import oob.instagramapitest.HomeComponent.Domain.ViewInterface;
import oob.instagramapitest.HomeComponent.Framework.Adapter.PhotoCardAdapter;
import oob.instagramapitest.HomeComponent.Framework.Adapter.PhotoCardDialogAdapter;
import oob.instagramapitest.HomeComponent.Framework.DependencyInjection.DaggerHomeComponentInterface;
import oob.instagramapitest.HomeComponent.Framework.DependencyInjection.HomeComponentInterface;
import oob.instagramapitest.HomeComponent.Framework.DependencyInjection.HomeComponentModule;
import oob.instagramapitest.OptionsComponent.Framework.OptionsActivity;
import oob.instagramapitest.Util.DialogUtil;
import oob.instagramapitest.R;

public class HomeActivity extends AppCompatActivity implements ViewInterface, PhotoCardAdapter.OnPhotoCardEvent, PhotoCardDialogAdapter.PhotoCardDialogEvent {
    private static final String TAG = "HomeActivity";

    @BindView(R.id.photoCardRecyclerView)
    RecyclerView photoCardRecyclerView;

    @Inject
    CheckNickPasswordStoredUseCase checkNickPasswordStoredUseCase;
    @Inject
    GetInstagramUserInformationUseCase getInstagramUserInformationUseCase;
    @Inject
    LoginWithNewInformationUseCase loginWithNewInformationUseCase;
    @Inject
    GetAllPhotosUseCase getAllPhotosUseCase;
    @Inject
    UpdatePhotoDateUseCase updatePhotoDateUseCase;
    @Inject
    RemovePhotoUseCase removePhotoUseCase;

    private View followersIndicator;
    private PhotoCardAdapter photoCardAdapter;

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

        this.init();
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

    private void setUpPhotoCardList() {
        this.photoCardRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        this.photoCardAdapter = new PhotoCardAdapter(this, this);
        this.photoCardRecyclerView.setAdapter(this.photoCardAdapter);
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
                        this.getString(R.string.dialog_user_info_warning_title),
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
                        HomeActivity.this.getString(R.string.dialog_user_info_error_title),
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

    @Override
    protected void onResume() {
        super.onResume();

        this.photoCardAdapter.setPhotos(this.getAllPhotosUseCase.getAll());
    }

    @Override
    public void onPhotoClicked(Photo photo) {
        new PhotoCardDialogAdapter(this, photo, this).show();
    }

    @Override
    public void onAddNewPhotoClicked() {
        this.startActivity(new Intent(this, AddPhotoScheduleActivity.class));
    }

    @Override
    public void OnSaveClicked(Photo photo, Date date) {
        this.updatePhotoDateUseCase.update(photo.getId(), date);
        photo.setDate(date);
        photo.setState(oob.instagramapitest.Util.Database.Photo.PENDING);
        this.photoCardAdapter.notifyItemChanged(photo);
    }

    @Override
    public void OnRemoveClicked(Photo photo) {
        this.removePhotoUseCase.remove(photo.getId());
        this.photoCardAdapter.notifyItemRemoved(photo);
    }
}