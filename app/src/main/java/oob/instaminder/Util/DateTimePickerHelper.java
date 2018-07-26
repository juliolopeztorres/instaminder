package oob.instaminder.Util;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimePickerHelper {

    private Calendar calendar = Calendar.getInstance();

    public DateTimePickerHelper(final Context context, final TextView date, final TextView time) {
        date.setText(SimpleDateFormat.getDateInstance().format(this.calendar.getTime()));
        time.setText(SimpleDateFormat.getTimeInstance().format(this.calendar.getTime()));

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        context,
                        0,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                DateTimePickerHelper.this.calendar.set(year, month, dayOfMonth);
                                date.setText(SimpleDateFormat.getDateInstance().format(DateTimePickerHelper.this.calendar.getTime()));
                            }
                        },
                        DateTimePickerHelper.this.calendar.get(Calendar.YEAR),
                        DateTimePickerHelper.this.calendar.get(Calendar.MONTH),
                        DateTimePickerHelper.this.calendar.get(Calendar.DAY_OF_MONTH)
                );

                datePickerDialog.show();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog datePickerDialog = new TimePickerDialog(
                        context,
                        0,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                calendar.set(Calendar.MINUTE, minute);
                                time.setText(SimpleDateFormat.getTimeInstance().format(DateTimePickerHelper.this.calendar.getTime()));
                            }
                        },
                        DateTimePickerHelper.this.calendar.get(Calendar.HOUR_OF_DAY),
                        DateTimePickerHelper.this.calendar.get(Calendar.MINUTE),
                        true
                );

                datePickerDialog.show();
            }
        });
    }

    public Date get() {
        return this.calendar.getTime();
    }
}
