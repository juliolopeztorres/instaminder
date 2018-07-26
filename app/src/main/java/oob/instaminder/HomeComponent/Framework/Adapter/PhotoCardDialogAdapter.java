package oob.instaminder.HomeComponent.Framework.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import oob.instaminder.HomeComponent.Domain.GetAllPhotosUseCase.Model.Photo;
import oob.instaminder.R;
import oob.instaminder.Util.DateTimePickerHelper;
import oob.instaminder.Util.StringUtil;
import oob.instaminder.Util.ViewUtil;

public class PhotoCardDialogAdapter {
    private static final int TITLE_CHARACTERS_LIMIT = 45;

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
        void OnSaveClicked(Photo photo, Date date);

        void OnRemoveClicked(Photo photo);
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
        this.photoTitle.setText(String.format("%s", StringUtil.limitIfGreaterThan(this.photo.getCaption(), TITLE_CHARACTERS_LIMIT)));
        this.photoLog.setText(this.photo.getLog());

        if (this.photo.getState().equals(oob.instaminder.Util.Database.Photo.ERROR) && !this.photo.getLog().isEmpty()) {
            ViewUtil.makeViewVisible(this.showLogContainer);
        }

        this.dateTimePickerHelper = new DateTimePickerHelper(this.context, this.photoDate, this.photoTime);
        this.photoDate.setText(SimpleDateFormat.getDateInstance().format(this.photo.getDate()));
        this.photoTime.setText(SimpleDateFormat.getTimeInstance().format(this.photo.getDate()));
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
        this.dialog.dismiss();
        this.callback.OnSaveClicked(this.photo, this.dateTimePickerHelper.get());
    }

    @OnClick(R.id.removeContainer)
    void onRemoveContainerClicked() {
        this.dialog.dismiss();
        this.callback.OnRemoveClicked(this.photo);
    }
}
