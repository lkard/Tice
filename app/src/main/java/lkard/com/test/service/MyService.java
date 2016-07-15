package lkard.com.test.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

import lkard.com.test.Location.MyLocationListener;
import lkard.com.test.R;
import lkard.com.test.activity.LocationActivity;

public class MyService extends Service implements LocationListener {
    private LocationManager locationManager;
    private NotificationManager nm;
    private final int NotifiKey = 127;
    @Override
    public void onCreate() {
        super.onCreate();
        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        nm = (NotificationManager)this.getSystemService(NOTIFICATION_SERVICE);
        if (locationManager.getAllProviders().contains(LocationManager.NETWORK_PROVIDER))
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

        if (locationManager.getAllProviders().contains(LocationManager.GPS_PROVIDER))
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

    }
    public void showNatification(){
        Notification.Builder builder = new Notification.Builder(this);
        Intent intent = new Intent(getApplicationContext(), LocationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent,PendingIntent.FLAG_CANCEL_CURRENT);
        builder
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.ic_launcher))
                .setTicker("Вы можете узнать свое место положения!!!")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentTitle("Моя локация")
                .setContentText("Появился доступ к обновлению данных места положения, вы можете узнать свои кординаты");
        Notification notification = builder.build();
        nm.notify(NotifiKey,notification);
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
    @Override
    public void onProviderEnabled(String provider) {
        showNatification();
    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
