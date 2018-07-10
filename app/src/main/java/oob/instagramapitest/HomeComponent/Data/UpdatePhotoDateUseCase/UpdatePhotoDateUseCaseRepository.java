package oob.instagramapitest.HomeComponent.Data.UpdatePhotoDateUseCase;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Date;

import io.realm.Realm;
import oob.instagramapitest.ApplicationComponent.AlarmReceiver;
import oob.instagramapitest.HomeComponent.Domain.UpdatePhotoDateUseCase.UpdatePhotoDateUseCaseRepositoryInterface;
import oob.instagramapitest.Util.Database.Photo;

public class UpdatePhotoDateUseCaseRepository implements UpdatePhotoDateUseCaseRepositoryInterface {
    private Context context;
    private Realm realm;

    public UpdatePhotoDateUseCaseRepository(Context context, Realm realm) {
        this.context = context;
        this.realm = realm;
    }

    @Override
    public void update(String photoId, Date date) {
        Photo photo = this.realm.where(Photo.class).equalTo("id", photoId).findFirst();

        if (photo == null) {
            return;
        }

        this.removeAlarm(photo);

        this.realm.beginTransaction();
        photo.setDate(date);
        photo.setState(Photo.PENDING);
        this.realm.commitTransaction();

        this.setUpAlarm(photo);
    }

    private void setUpAlarm(Photo photo) {
        Intent alarmIntent = new Intent(this.context, AlarmReceiver.class);
        alarmIntent.putExtra("photoId", photo.getId());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context, (int) photo.getDate().getTime(), alarmIntent, 0);

        AlarmManager manager = (AlarmManager) this.context.getSystemService(Context.ALARM_SERVICE);
        if (manager == null) {
            return;
        }

        manager.setAlarmClock(new AlarmManager.AlarmClockInfo(
                photo.getDate().getTime()
                , null), pendingIntent);
    }

    private void removeAlarm(Photo photo) {
        Intent alarmIntent = new Intent(this.context, AlarmReceiver.class);
        alarmIntent.putExtra("photoId", photo.getId());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context, (int) photo.getDate().getTime(), alarmIntent, 0);

        AlarmManager manager = (AlarmManager) this.context.getSystemService(Context.ALARM_SERVICE);
        if (manager == null) {
            return;
        }

        manager.cancel(pendingIntent);
    }
}
