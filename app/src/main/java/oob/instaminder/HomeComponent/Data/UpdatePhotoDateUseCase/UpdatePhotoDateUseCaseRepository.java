package oob.instaminder.HomeComponent.Data.UpdatePhotoDateUseCase;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Date;

import io.realm.Realm;
import oob.instaminder.ApplicationComponent.AlarmReceiver;
import oob.instaminder.HomeComponent.Domain.UpdatePhotoDateUseCase.UpdatePhotoDateUseCaseRepositoryInterface;
import oob.instaminder.Util.Database.Photo;

public class UpdatePhotoDateUseCaseRepository implements UpdatePhotoDateUseCaseRepositoryInterface {
    private Context context;
    private Realm realm;

    public UpdatePhotoDateUseCaseRepository(Context context, Realm realm) {
        this.context = context;
        this.realm = realm;
    }

    @Override
    public void update(oob.instaminder.HomeComponent.Domain.GetAllPhotosUseCase.Model.Photo photo, Date date) {
        Photo photoDB = this.realm.where(Photo.class).equalTo("id", photo.getId()).findFirst();

        if (photoDB == null) {
            return;
        }

        this.removeAlarm(photoDB);

        this.realm.beginTransaction();
        photoDB.setCaption(photo.getCaption());
        photoDB.setDate(date);
        photoDB.setState(Photo.PENDING);
        this.realm.commitTransaction();

        this.setUpAlarm(photoDB);
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
