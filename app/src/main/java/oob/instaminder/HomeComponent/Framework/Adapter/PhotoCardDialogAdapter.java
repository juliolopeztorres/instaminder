package oob.instaminder.HomeComponent.Framework.Adapter;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import oob.instaminder.HomeComponent.Domain.GetAllPhotosUseCase.Model.Photo;
import oob.instaminder.R;
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


    private AlertDialog dialog;
    private Context context;
    private Photo photo;
    private PhotoCardDialogEvent callback;
    private Calendar calendar = Calendar.getInstance();


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
        this.photoTitle.setText(String.format("%s (%s)", this.photo.getName(), this.photo.getCaption()));
        this.photoDate.setText(SimpleDateFormat.getDateInstance().format(photo.getDate()));
        this.photoTime.setText(SimpleDateFormat.getTimeInstance().format(photo.getDate()));
        this.photoLog.setText(this.photo.getLog());

        if (photo.getState().equals(oob.instaminder.Util.Database.Photo.ERROR) && !this.photo.getLog().isEmpty()) {
            ViewUtil.makeViewVisible(this.showLogContainer);
        }
    }

    public void show() {
        this.dialog.show();
    }

    @OnClick(R.id.photoDate)
    void onPhotoDateClicked(final TextView textView) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this.context,
                0,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        PhotoCardDialogAdapter.this.calendar.set(year, month, dayOfMonth);
                        textView.setText(SimpleDateFormat.getDateInstance().format(PhotoCardDialogAdapter.this.calendar.getTime()));
                    }
                },
                PhotoCardDialogAdapter.this.calendar.get(Calendar.YEAR),
                PhotoCardDialogAdapter.this.calendar.get(Calendar.MONTH),
                PhotoCardDialogAdapter.this.calendar.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }

    @OnClick(R.id.photoTime)
    void onPhotoTimeClicked(final TextView textView) {
        TimePickerDialog datePickerDialog = new TimePickerDialog(
                this.context,
                0,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        PhotoCardDialogAdapter.this.calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        PhotoCardDialogAdapter.this.calendar.set(Calendar.MINUTE, minute);
                        textView.setText(SimpleDateFormat.getTimeInstance().format(PhotoCardDialogAdapter.this.calendar.getTime()));
                    }
                },
                PhotoCardDialogAdapter.this.calendar.get(Calendar.HOUR_OF_DAY),
                PhotoCardDialogAdapter.this.calendar.get(Calendar.MINUTE),
                true
        );

        datePickerDialog.show();
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
        this.callback.OnSaveClicked(this.photo, this.calendar.getTime());
    }

    @OnClick(R.id.removeContainer)
    void onRemoveContainerClicked() {
        this.dialog.dismiss();
        this.callback.OnRemoveClicked(this.photo);
    }
}
