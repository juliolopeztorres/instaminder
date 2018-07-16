package oob.instagramapitest.AddPhotoScheduleComponent.Framework;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.model.AspectRatio;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import oob.instagramapitest.AddPhotoScheduleComponent.Domain.SavePhotoUseCase.Model.Photo;
import oob.instagramapitest.AddPhotoScheduleComponent.Domain.SavePhotoUseCase.SavePhotoUseCase;
import oob.instagramapitest.AddPhotoScheduleComponent.Domain.ViewInterface;
import oob.instagramapitest.AddPhotoScheduleComponent.Framework.DependencyInjection.AddPhotoScheduleComponentInterface;
import oob.instagramapitest.AddPhotoScheduleComponent.Framework.DependencyInjection.AddPhotoScheduleComponentModule;
import oob.instagramapitest.AddPhotoScheduleComponent.Framework.DependencyInjection.DaggerAddPhotoScheduleComponentInterface;
import oob.instagramapitest.ApplicationComponent.BaseApplication;
import oob.instagramapitest.R;
import oob.instagramapitest.Util.ByteUtil;
import oob.instagramapitest.Util.DialogUtil;
import oob.instagramapitest.Util.LogUtil;
import oob.instagramapitest.Util.ViewUtil;

public class AddPhotoScheduleActivity extends AppCompatActivity implements ViewInterface {
    private static final int REQUEST_CODE_SEARCH_PHOTO = 1;
    private static final String PHOTO_PLACEHOLDER_NAME = "photoEditingSpaceReserve";

    @BindView(R.id.photoName)
    EditText photoName;
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

    @Inject
    SavePhotoUseCase savePhotoUseCase;

    private byte[] photoBuffer;
    private Calendar calendar = Calendar.getInstance();

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
        this.initPhotoDateTime();
    }

    private void initPhotoDateTime() {
        this.photoDate.setText(SimpleDateFormat.getDateInstance().format(AddPhotoScheduleActivity.this.calendar.getTime()));
        this.photoTime.setText(SimpleDateFormat.getTimeInstance().format(AddPhotoScheduleActivity.this.calendar.getTime()));
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

    @OnClick(R.id.photoDate)
    public void onPhotoDateClicked(final TextView textView) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                0,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        AddPhotoScheduleActivity.this.calendar.set(year, month, dayOfMonth);
                        textView.setText(SimpleDateFormat.getDateInstance().format(AddPhotoScheduleActivity.this.calendar.getTime()));
                    }
                },
                AddPhotoScheduleActivity.this.calendar.get(Calendar.YEAR),
                AddPhotoScheduleActivity.this.calendar.get(Calendar.MONTH),
                AddPhotoScheduleActivity.this.calendar.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }

    @OnClick(R.id.photoTime)
    public void onPhotoTimeClicked(final TextView textView) {
        TimePickerDialog datePickerDialog = new TimePickerDialog(
                this,
                0,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        AddPhotoScheduleActivity.this.calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        AddPhotoScheduleActivity.this.calendar.set(Calendar.MINUTE, minute);
                        textView.setText(SimpleDateFormat.getTimeInstance().format(AddPhotoScheduleActivity.this.calendar.getTime()));
                    }
                },
                AddPhotoScheduleActivity.this.calendar.get(Calendar.HOUR_OF_DAY),
                AddPhotoScheduleActivity.this.calendar.get(Calendar.MINUTE),
                true
        );

        datePickerDialog.show();
    }

    @OnClick(R.id.photoImagePreviewContainer)
    public void onPhotoImagePreviewContainerClicked() {
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

        photo.setName(this.photoName.getText().toString())
                .setCaption(this.photoCaption.getText().toString())
                .setBuffer(this.photoBuffer)
                .setDate(this.calendar.getTime());

        if (!Photo.validate(photo)) {
            DialogUtil.showAlertDialog(this, this.getString(R.string.dialog_user_info_error_title), this.getString(R.string.add_photo_component_dialog_save_photo_error_message), this.getString(android.R.string.ok));
            return;
        }

        this.savePhotoUseCase.save(photo);

        this.setResult(Activity.RESULT_OK);
        this.finish();
    }

    private File getTempFile() {
        File file = new File(this.getCacheDir() + "/" + PHOTO_PLACEHOLDER_NAME);
        file.deleteOnExit();

        return file;
    }

    private UCrop.Options getUCropOptions() {
        UCrop.Options options = new UCrop.Options();

        options.setAspectRatioOptions(1,
                new AspectRatio(this.getString(R.string.edit_photo_component_vertical_aspect_ratio_title), 4, 5), // VERTICAL
                new AspectRatio(this.getString(R.string.edit_photo_component_square_aspect_ratio_title), 1, 1), // SQUARE
                new AspectRatio(this.getString(R.string.edit_photo_component_horizontal_aspect_ratio_title), 16, 9) // LANDSCAPE
        );

        options.setToolbarColor(ContextCompat.getColor(this, R.color.colorAccentWhite));
        options.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        options.setActiveWidgetColor(ContextCompat.getColor(this, R.color.colorPrimary));
        options.setToolbarWidgetColor(ContextCompat.getColor(this, R.color.colorPrimary));


        options.setToolbarTitle(this.getResources().getString(R.string.edit_photo_component_title));

        return options;
    }
}
