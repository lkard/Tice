package lkard.com.test.activity;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import lkard.com.test.Location.MyLocationListener;
import lkard.com.test.R;
import lkard.com.test.service.MyService;

/**
 * Created by LKARD on 02.08.2015.
 */
public class LocationActivity extends Activity {
    TextView tvEnabledGPS;
    TextView tvStatusGPS;
    TextView tvLonGPS;
    TextView tvLenGPS;
    TextView tvTimeGPS;
    TextView tvEnabledNET;
    TextView tvStatusNET;
    TextView tvLonNET;
    TextView tvLenNET;
    TextView tvTimeNET;
    double len;
    double lon;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        tvEnabledGPS = (TextView) findViewById(R.id.al_gps_active);
        tvStatusGPS = (TextView) findViewById(R.id.al_gps_state);
        tvLonGPS = (TextView) findViewById(R.id.al_gps_lon);
        tvLenGPS = (TextView) findViewById(R.id.al_gps_len);
        tvTimeGPS = (TextView) findViewById(R.id.al_gps_time);
        tvEnabledNET = (TextView) findViewById(R.id.al_net_active);
        tvStatusNET = (TextView) findViewById(R.id.al_net_state);
        tvLonNET = (TextView) findViewById(R.id.al_net_lon);
        tvLenNET = (TextView) findViewById(R.id.al_net_len);
        tvTimeNET = (TextView) findViewById(R.id.al_net_time);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        stopService(new Intent(this, MyService.class));
        if (locationManager.getAllProviders().contains(LocationManager.NETWORK_PROVIDER))
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

        if (locationManager.getAllProviders().contains(LocationManager.GPS_PROVIDER))
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        checkEnabled();
    }

    @Override
    protected void onPause() {
        super.onPause();
        startService(new Intent(this, MyService.class));
        locationManager.removeUpdates(locationListener);
    }

    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);
        }

        @Override
        public void onProviderDisabled(String provider) {
            checkEnabled();
        }

        @Override
        public void onProviderEnabled(String provider) {
            checkEnabled();
            showLocation(locationManager.getLastKnownLocation(provider));
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            if (provider.equals(LocationManager.GPS_PROVIDER)) {
                tvStatusGPS.setText(getString(R.string.state)+ " " + String.valueOf(status));
            } else if (provider.equals(LocationManager.NETWORK_PROVIDER)) {
                tvStatusNET.setText(getString(R.string.state)+ " " + String.valueOf(status));
            }
        }
    };

    private void showLocation(Location location) {
        if (location == null) {
            return;
        }
        if (location.getProvider().equals( LocationManager.GPS_PROVIDER)) {

            tvLenGPS.setText(getString(R.string.latitude) + " " + String.valueOf(location.getLatitude()));
            tvLonGPS.setText(getString(R.string.longitude) + " " + String.valueOf(location.getLongitude()));
            tvTimeGPS.setText(getString(R.string.time) + " " + new Date(location.getTime()).toString());

        } else if (location.getProvider().equals( LocationManager.NETWORK_PROVIDER)) {

            tvLenNET.setText(getString(R.string.latitude) + " " + String.valueOf(location.getLatitude()));
            tvLonNET.setText(getString(R.string.longitude) + " " + String.valueOf(location.getLongitude()));
            tvTimeNET.setText(getString(R.string.time) + " " + new Date(location.getTime()).toString());
        }
        len = location.getLatitude();
        lon = location.getLongitude();
    }

    private void checkEnabled() {
        tvEnabledGPS.setText(getString(R.string.active) + String.valueOf(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)));
        tvEnabledNET.setText(getString(R.string.active) + String.valueOf(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)));
    }

    public void SettingsClick(View view) {
        startActivity(new Intent( android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    }
    public void startMapClick(View v){
        /*try{
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("geo:" + String.valueOf(len) + "," + String.valueOf(lon)));
            startActivity(intent);
        }catch (Exception e){
            Toast.makeText(this,"U don't have app to do that",Toast.LENGTH_LONG).show();
        }*/
        Uri gmmIntentUri = Uri.parse("geo:" + String.valueOf(len) + "," + String.valueOf(lon));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }else{
            Toast.makeText(this,"U don't Map Application",Toast.LENGTH_LONG).show();
        }


    }
}
