package oob.instaminder.AddPhotoScheduleComponent.Framework;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;
import com.yalantis.ucrop.model.AspectRatio;

import java.io.File;
import java.io.FileNotFoundException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import oob.instaminder.AddPhotoScheduleComponent.Domain.ExternalStorageLastDialogShownUseCase.ExternalStorageLastDialogShownUseCase;
import oob.instaminder.AddPhotoScheduleComponent.Domain.GetNumberAdShownUseCase.GetNumberAdShownUseCase;
import oob.instaminder.AddPhotoScheduleComponent.Domain.IncreaseNumberAdShownUseCase.IncreaseNumberAdShownUseCase;
import oob.instaminder.AddPhotoScheduleComponent.Domain.MarkExternalStorageLastDialogAsShownUseCase.MarkExternalStorageLastDialogAsShownUseCase;
import oob.instaminder.AddPhotoScheduleComponent.Domain.SavePhotoUseCase.Model.Photo;
import oob.instaminder.AddPhotoScheduleComponent.Domain.SavePhotoUseCase.SavePhotoUseCase;
import oob.instaminder.AddPhotoScheduleComponent.Domain.ViewInterface;
import oob.instaminder.AddPhotoScheduleComponent.Framework.DependencyInjection.AddPhotoScheduleComponentInterface;
import oob.instaminder.AddPhotoScheduleComponent.Framework.DependencyInjection.AddPhotoScheduleComponentModule;
import oob.instaminder.AddPhotoScheduleComponent.Framework.DependencyInjection.DaggerAddPhotoScheduleComponentInterface;
import oob.instaminder.ApplicationComponent.BaseApplication;
import oob.instaminder.R;
import oob.instaminder.Util.ByteUtil;
import oob.instaminder.Util.DateTimePickerHelper;
import oob.instaminder.Util.DialogUtil;
import oob.instaminder.Util.LogUtil;
import oob.instaminder.Util.ViewUtil;

public class AddPhotoScheduleActivity extends AppCompatActivity implements ViewInterface, TextWatcher {
    public static final String INTENT_USERNAME_KEY = "usernameKey";
    public static final String INTENT_PROFILE_PHOTO_URL_KEY = "profilePhotoUrlKey";
    private static final int REQUEST_CODE_SEARCH_PHOTO = 1;
    private static final int REQUEST_CODE_EXTERNAL_STORAGE_PERMISSION_REQUEST = 2;
    private static final String PHOTO_PLACEHOLDER_NAME = "photoEditingSpaceReserve";
    private static final int NUMBER_OF_ADS_UNTIL_VIDEO = 10;

    @BindView(R.id.photoCaption)
    EditText photoCaption;
    @BindView(R.id.photoDate)
    TextView photoDate;
    @BindView(R.id.photoTime)
    TextView photoTime;
    @BindView(R.id.photoImagePreview)
    ImageView photoImagePreview;
    @BindView(R.id.photoTapToSearchLabel)
    View photoTapToSearchLabel;
    @BindView(R.id.userProfilePhoto)
    ImageView userProfilePhoto;
    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.usernameBottom)
    TextView usernameBottom;
    @BindView(R.id.captionPreview)
    TextView captionPreview;

    @Inject
    SavePhotoUseCase savePhotoUseCase;
    @Inject
    GetNumberAdShownUseCase getNumberAdShownUseCase;
    @Inject
    IncreaseNumberAdShownUseCase increaseNumberAdShownUseCase;
    @Inject
    ExternalStorageLastDialogShownUseCase externalStorageLastDialogShownUseCase;
    @Inject
    MarkExternalStorageLastDialogAsShownUseCase markExternalStorageLastDialogAsShownUseCase;

    private byte[] photoBuffer;
    private DateTimePickerHelper dateTimePickerHelper;
    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo_schedule);

        ButterKnife.bind(this);

        AddPhotoScheduleComponentInterface component = DaggerAddPhotoScheduleComponentInterface.builder()
                .baseApplicationComponentInterface(((BaseApplication) this.getApplication()).getComponent())
                .addPhotoScheduleComponentModule(new AddPhotoScheduleComponentModule(this))
                .build();
        component.inject(this);

        this.init();
    }

    private void init() {
        this.setBackButton();
        this.tintActionBarTextColor();
        this.setUpPreviewToolbarInformation();
        this.setUpPhotoCaptionListener();
        this.setUpDateTimeListener();
        this.setUpAd();
    }

    private void setUpAd() {
        MobileAds.initialize(this, this.getString(R.string.ad_id));

        this.interstitialAd = new InterstitialAd(this);

        if (this.getNumberAdShownUseCase.get() % NUMBER_OF_ADS_UNTIL_VIDEO == 0) {
            this.interstitialAd.setAdUnitId(this.getString(R.string.ad_unit_id_video));
        } else {
            this.interstitialAd.setAdUnitId(this.getString(R.string.ad_unit_id_text_image));
        }

        this.interstitialAd.loadAd(new AdRequest.Builder().build());
    }

    private void setUpDateTimeListener() {
        this.dateTimePickerHelper = new DateTimePickerHelper(this, null, this.photoDate, this.photoTime);
    }

    private void setUpPhotoCaptionListener() {
        this.photoCaption.addTextChangedListener(this);
    }

    private void setUpPreviewToolbarInformation() {
        Intent intent = this.getIntent();

        String username = intent.getStringExtra(INTENT_USERNAME_KEY);
        String profilePhotoUrl = intent.getStringExtra(INTENT_PROFILE_PHOTO_URL_KEY);

        if (username != null) {
            this.username.setText(username);
            this.usernameBottom.setText(username);
        }

        if (profilePhotoUrl != null) {
            Glide.with(this).load(profilePhotoUrl).into(this.userProfilePhoto);
        }
    }

    private void setBackButton() {
        ActionBar actionBar = this.getSupportActionBar();

        if (actionBar == null) {
            return;
        }
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.add_photo_component_title);
    }

    private void tintActionBarTextColor() {
        Toolbar actionBarToolbar = this.findViewById(R.id.action_bar);
        if (actionBarToolbar == null) {
            return;
        }
        actionBarToolbar.setTitleTextColor(this.getResources().getColor(R.color.colorPrimary));
        actionBarToolbar.setBackgroundColor(this.getResources().getColor(R.color.colorAccentWhite));

        Drawable icon = actionBarToolbar.getNavigationIcon();
        if (icon == null) {
            return;
        }

        icon.setColorFilter(this.getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return true;
    }

    @OnClick(R.id.photoImagePreviewContainer)
    public void onPhotoImagePreviewContainerClicked() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_EXTERNAL_STORAGE_PERMISSION_REQUEST);
            return;
        }

        this.pickPhotoFromStorage();
    }

    private void pickPhotoFromStorage() {
        Intent chooserIntent = Intent
                .createChooser(
                        new Intent(Intent.ACTION_GET_CONTENT).setType("image/*"),
                        this.getString(R.string.add_photo_component_multiple_intent_title))
                .putExtra(
                        Intent.EXTRA_INITIAL_INTENTS,
                        new Intent[]{
                                new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI).setType("image/*")
                        }
                );

        this.startActivityForResult(chooserIntent, REQUEST_CODE_SEARCH_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_SEARCH_PHOTO) {
            if (resultCode == Activity.RESULT_OK && data.getData() != null) {
                this.onPhotoLoadedFromDisk(data.getData());
            }
            return;
        }

        if (requestCode == UCrop.REQUEST_CROP) {
            if (resultCode == RESULT_OK) {
                this.onPhotoEdited(UCrop.getOutput(data));
            }

            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_EXTERNAL_STORAGE_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                this.onPhotoImagePreviewContainerClicked();
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    DialogUtil.showAlertDialog(
                            this,
                            this.getString(R.string.dialog_user_info_warning_title),
                            this.getString(R.string.dialog_user_grant_external_read_permission_rationale),
                            this.getString(R.string.dialog_user_grant_external_read_permission_rationale_ok_label),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ActivityCompat.requestPermissions(AddPhotoScheduleActivity.this,
                                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                            REQUEST_CODE_EXTERNAL_STORAGE_PERMISSION_REQUEST);
                                }
                            },
                            false
                    );

                    return;
                }

                if (!this.externalStorageLastDialogShownUseCase.check()) {
                    DialogUtil.showAlertDialog(
                            this,
                            this.getString(R.string.dialog_user_info_warning_title),
                            this.getString(R.string.dialog_user_grant_external_read_permission_last_deny),
                            this.getString(android.R.string.ok),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    AddPhotoScheduleActivity.this.markExternalStorageLastDialogAsShownUseCase.mark();
                                    AddPhotoScheduleActivity.this.pickPhotoFromStorage();
                                }
                            },
                            false
                    );
                } else {
                    this.pickPhotoFromStorage();
                }
            }
        }
    }

    private void onPhotoLoadedFromDisk(Uri photoUri) {
        this.goToEditPhotoComponent(photoUri);
    }

    private void goToEditPhotoComponent(Uri photoUri) {
        UCrop.of(
                photoUri,
                Uri.fromFile(this.getTempFile())
        ).withOptions(
                this.getUCropOptions()
        ).start(this);
    }

    private void onPhotoEdited(Uri photoUri) {
        ViewUtil.makeViewGone(this.photoTapToSearchLabel);

        Glide.with(this).load(photoUri)
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(this.photoImagePreview);

        try {
            this.photoBuffer = ByteUtil.convertInputStreamToByteArray(this.getContentResolver().openInputStream(photoUri));
        } catch (FileNotFoundException e) {
            LogUtil.log("Could not parse image to byte[]", e);
        }
    }

    @OnClick(R.id.btnSavePhoto)
    public void onBtnSavePhotoClicked() {
        Photo photo = new Photo();

        photo.setCaption(this.photoCaption.getText().toString())
                .setBuffer(this.photoBuffer)
                .setDate(this.dateTimePickerHelper.get());

        if (!Photo.validate(photo)) {
            DialogUtil.showAlertDialog(this, this.getString(R.string.dialog_user_info_error_title), this.getString(R.string.add_photo_component_dialog_save_photo_error_message), this.getString(android.R.string.ok));
            return;
        }

        this.savePhotoUseCase.save(photo);

        this.showAd();

        this.setResult(Activity.RESULT_OK);
        this.finish();
    }

    private void showAd() {
        if (!((BaseApplication) this.getApplication()).isFirstLaunch() && this.interstitialAd.isLoaded()) {
            this.interstitialAd.show();
            this.increaseNumberAdShownUseCase.increase();
        }
    }

    private File getTempFile() {
        return new File(this.getCacheDir() + "/" + PHOTO_PLACEHOLDER_NAME);
    }

    private UCrop.Options getUCropOptions() {
        UCrop.Options options = new UCrop.Options();

        options.setAspectRatioOptions(1,
                new AspectRatio(this.getString(R.string.edit_photo_component_vertical_aspect_ratio_title), 4, 5), // VERTICAL
                new AspectRatio(this.getString(R.string.edit_photo_component_square_aspect_ratio_title), 1, 1), // SQUARE
                new AspectRatio(this.getString(R.string.edit_photo_component_horizontal_aspect_ratio_title), 16, 9) // LANDSCAPE
        );

        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.NONE, UCropActivity.SCALE);
        options.setToolbarColor(ContextCompat.getColor(this, R.color.colorAccentWhite));
        options.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        options.setActiveWidgetColor(ContextCompat.getColor(this, R.color.colorPrimary));
        options.setToolbarWidgetColor(ContextCompat.getColor(this, R.color.colorPrimary));

        options.setToolbarTitle(this.getResources().getString(R.string.edit_photo_component_title));

        return options;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence text, int start, int before, int count) {
        this.captionPreview.setText(
                Html.fromHtml(this.getFormattedCaptionForHtml(text.toString()))
        );
    }

    private String getFormattedCaptionForHtml(String captionEntered) {
        if (captionEntered == null || captionEntered.isEmpty()) {
            return "";
        }

        String[] textParts = captionEntered.split(" ");
        StringBuilder formattedText = new StringBuilder();
        for (String textPart : textParts) {
            formattedText.append(
                    String.format(
                            "<font color='%s'>%s</font>",
                            textPart.charAt(0) == '#' ?
                                    this.getString(R.string.add_photo_component_caption_preview_hashTag_color) :
                                    this.getString(R.string.add_photo_component_caption_preview_black_color),
                            textPart)
            ).append(" ");
        }

        return formattedText.toString();
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}
