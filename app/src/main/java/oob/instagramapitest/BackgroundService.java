package oob.instagramapitest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import io.realm.Realm;
import oob.instagramapitest.Model.Database.Photo;

public class BackgroundService extends Service {
    private static final String TAG = "BackgroundService";

    private boolean running = false;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        this.running = true;

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    do {
                        Log.d(TAG, "while");
                        BackgroundService.this.uploadPhotos();
                        Thread.sleep(5 * 1000);
                        // Thread.sleep(5 * 60000);
                    }
                    while(BackgroundService.this.running && BackgroundService.this.isNetworkAvailable());
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return Service.START_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent){
        Intent restartServiceIntent = new Intent(getApplicationContext(), this.getClass());
        restartServiceIntent.setPackage(getPackageName());

        PendingIntent restartServicePendingIntent = PendingIntent.getService(getApplicationContext(), 1, restartServiceIntent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmService = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmService.set(
                AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + 1000,
                restartServicePendingIntent);
        super.onTaskRemoved(rootIntent);
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void uploadPhotos() {
        List<Photo> photos = Realm.getDefaultInstance().where(Photo.class)
                .lessThanOrEqualTo("date", Calendar.getInstance().getTime())
                .findAll();

        Photo photo = photos.get(0);
    }
}
