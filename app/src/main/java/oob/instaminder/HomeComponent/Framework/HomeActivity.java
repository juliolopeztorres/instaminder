package oob.instaminder.HomeComponent.Framework;

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
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import oob.instaminder.AddPhotoScheduleComponent.Framework.AddPhotoScheduleActivity;
import oob.instaminder.ApplicationComponent.BaseApplication;
import oob.instaminder.HomeComponent.Domain.CheckNickPasswordStoredUseCase.CheckNickPasswordStoredUseCase;
import oob.instaminder.HomeComponent.Domain.GetAllPhotosUseCase.GetAllPhotosUseCase;
import oob.instaminder.HomeComponent.Domain.GetAllPhotosUseCase.Model.Photo;
import oob.instaminder.HomeComponent.Domain.GetInstagramUserInformationUseCase.GetInstagramUserInformationUseCase;
import oob.instaminder.HomeComponent.Domain.GetInstagramUserInformationUseCase.Model.InstagramUserInformation;
import oob.instaminder.HomeComponent.Domain.LoginWithNewInformationUseCase.LoginWithNewInformationUseCase;
import oob.instaminder.HomeComponent.Domain.RemovePhotoUseCase.RemovePhotoUseCase;
import oob.instaminder.HomeComponent.Domain.UpdatePhotoDateUseCase.UpdatePhotoDateUseCase;
import oob.instaminder.HomeComponent.Domain.ViewInterface;
import oob.instaminder.HomeComponent.Framework.Adapter.PhotoCardAdapter;
import oob.instaminder.HomeComponent.Framework.Adapter.PhotoCardDialogAdapter;
import oob.instaminder.HomeComponent.Framework.DependencyInjection.DaggerHomeComponentInterface;
import oob.instaminder.HomeComponent.Framework.DependencyInjection.HomeComponentInterface;
import oob.instaminder.HomeComponent.Framework.DependencyInjection.HomeComponentModule;
import oob.instaminder.OptionsComponent.Framework.OptionsActivity;
import oob.instaminder.Util.DialogUtil;
import oob.instaminder.R;

public class HomeActivity extends AppCompatActivity implements ViewInterface, PhotoCardAdapter.OnPhotoCardEvent, PhotoCardDialogAdapter.PhotoCardDialogEvent {
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
    private InstagramUserInformation instagramUserInformation;

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
        this.getAllPhotosUseCase.fetchAll();
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
        this.instagramUserInformation = instagramUserInformation;
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
    public void onPhotoClicked(Photo photo) {
        new PhotoCardDialogAdapter(this, photo, this).show();
    }

    @Override
    public void onAddNewPhotoClicked() {
        Intent intent = new Intent(this, AddPhotoScheduleActivity.class);
        if (this.instagramUserInformation != null) {
            intent.putExtra(AddPhotoScheduleActivity.INTENT_USERNAME_KEY, this.instagramUserInformation.getNick());
            intent.putExtra(AddPhotoScheduleActivity.INTENT_PROFILE_PHOTO_URL_KEY, this.instagramUserInformation.getProfilePicUrl());
        }

        this.startActivity(intent);
    }

    @Override
    public void onSaveClicked(Photo photo, Date date) {
        this.updatePhotoDateUseCase.update(photo.getId(), date);
    }

    @Override
    public void onRemoveClicked(Photo photo) {
        this.removePhotoUseCase.remove(photo.getId());
    }

    @Override
    public void loadPhotos(List<Photo> photoList) {
        this.photoCardAdapter.setPhotos(photoList);
    }
}
