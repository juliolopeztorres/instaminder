package oob.instaminder.HomeComponent.Framework.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import oob.instaminder.HomeComponent.Domain.GetAllPhotosUseCase.Model.Photo;
import oob.instaminder.R;
import oob.instaminder.Util.DateTimePickerHelper;
import oob.instaminder.Util.ViewUtil;

public class PhotoCardDialogAdapter {
    @BindView(R.id.photoTitle)
    TextView photoTitle;
    @BindView(R.id.photoDate)
    TextView photoDate;
    @BindView(R.id.photoTime)
    TextView photoTime;
    @BindView(R.id.photoLog)
    TextView photoLog;
    @BindView(R.id.showLogContainer)
    View showLogContainer;
    @BindView(R.id.showLogLabel)
    TextView showLogLabel;
    @BindView(R.id.photoCaption)
    EditText photoCaption;
    @BindView(R.id.photoCaptionContainer)
    TextInputLayout photoCaptionContainer;

    private AlertDialog dialog;
    private Context context;
    private Photo photo;
    private PhotoCardDialogEvent callback;
    private DateTimePickerHelper dateTimePickerHelper;

    public PhotoCardDialogAdapter(Context context, Photo photo, PhotoCardDialogEvent callback) {
        this.context = context;
        this.photo = photo;
        this.callback = callback;

        this.inflateDialog();
        this.setUpViewData();
    }

    public interface PhotoCardDialogEvent {
        void onSaveClicked(Photo photo, Date date);

        void onRemoveClicked(Photo photo);
    }

    private void inflateDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this.context);

        LayoutInflater inflater = LayoutInflater.from(this.context);
        @SuppressLint("InflateParams") View dialogView = inflater.inflate(R.layout.activity_home_card_photo_dialog, null);
        dialogBuilder.setView(dialogView);

        this.dialog = dialogBuilder.create();
        ButterKnife.bind(this, dialogView);
    }

    private void setUpViewData() {
        this.photoTitle.setText(this.context.getString(R.string.home_component_dialog_photo_title));
        this.photoCaption.setText(this.photo.getCaption());
        this.photoLog.setText(this.photo.getLog());

        if (this.photo.getState().equals(oob.instaminder.Util.Database.Photo.ERROR) && !this.photo.getLog().isEmpty()) {
            ViewUtil.makeViewVisible(this.showLogContainer);
        }

        this.dateTimePickerHelper = new DateTimePickerHelper(this.context, this.photo.getDate(), this.photoDate, this.photoTime);
    }

    public void show() {
        this.dialog.show();
    }

    @OnClick(R.id.showLogContainer)
    void onShowLogContainerClicked() {
        if (this.photoLog.getVisibility() == View.VISIBLE) {
            ViewUtil.makeViewGone(this.photoLog);
            this.showLogLabel.setText(this.context.getResources().getString(R.string.home_component_dialog_photo_show_log_label));

            return;
        }
        ViewUtil.makeViewVisible(this.photoLog);
        this.showLogLabel.setText(this.context.getResources().getString(R.string.home_component_dialog_photo_hide_log_label));
    }

    @OnClick(R.id.saveContainer)
    void onSaveContainerClicked() {
        if (this.validateInputs()) {
            this.photoCaptionContainer.setError("");
            this.dialog.dismiss();

            this.photo.setCaption(this.photoCaption.getText().toString());
            this.callback.onSaveClicked(this.photo, this.dateTimePickerHelper.get());
            return;
        }

        this.photoCaptionContainer.setError(this.context.getString(R.string.add_photo_component_dialog_save_photo_error_message));
    }

    private boolean validateInputs() {
        return !this.photoCaption.getText().toString().isEmpty();
    }

    @OnClick(R.id.removeContainer)
    void onRemoveContainerClicked() {
        this.dialog.dismiss();
        this.callback.onRemoveClicked(this.photo);
    }
}
