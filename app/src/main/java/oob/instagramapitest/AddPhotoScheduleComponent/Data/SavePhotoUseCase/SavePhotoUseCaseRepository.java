package oob.instagramapitest.AddPhotoScheduleComponent.Data.SavePhotoUseCase;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import io.realm.Realm;
import oob.instagramapitest.AddPhotoScheduleComponent.Data.SavePhotoUseCase.Mapper.PhotoMapper;
import oob.instagramapitest.AddPhotoScheduleComponent.Domain.SavePhotoUseCase.Model.Photo;
import oob.instagramapitest.AddPhotoScheduleComponent.Domain.SavePhotoUseCase.SavePhotoUseCaseRepositoryInterface;
import oob.instagramapitest.ApplicationComponent.AlarmReceiver;

public class SavePhotoUseCaseRepository implements SavePhotoUseCaseRepositoryInterface {
    private Context context;
    private Realm realm;

    public SavePhotoUseCaseRepository(Context context, Realm realm) {
        this.context = context;
        this.realm = realm;
    }

    @Override
    public void save(Photo photo) {
        this.realm.beginTransaction();
        oob.instagramapitest.Util.Database.Photo photoDB = this.realm.copyToRealm(PhotoMapper.map(photo));
        this.realm.commitTransaction();

        if (photoDB != null && !photoDB.getId().isEmpty()) {
            this.setUpAlarm(photoDB);
        }
    }

    private void setUpAlarm(oob.instagramapitest.Util.Database.Photo photo) {
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
}
