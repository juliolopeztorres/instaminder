package oob.instagramapitest.HomeComponent.Data.RemovePhotoUseCase;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import io.realm.Realm;
import oob.instagramapitest.HomeComponent.Domain.RemovePhotoUseCase.RemovePhotoUseCaseRepositoryInterface;
import oob.instagramapitest.Util.Database.Photo;
import oob.instagramapitest.ApplicationComponent.AlarmReceiver;

public class RemovePhotoUseCaseRepository implements RemovePhotoUseCaseRepositoryInterface {
    private Context context;
    private Realm realm;

    public RemovePhotoUseCaseRepository(Context context, Realm realm) {
        this.context = context;
        this.realm = realm;
    }

    @Override
    public void remove(String photoId) {
        Photo photo = this.realm.where(Photo.class).equalTo("id", photoId).findFirst();

        if (photo == null) {
            return;
        }

        this.removeAlarm(photo);

        this.realm.beginTransaction();
        photo.deleteFromRealm();
        this.realm.commitTransaction();
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
