package lkard.com.test.Location;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import java.util.Date;

/**
 * Created by LKARD on 02.08.2015.
 */
public class MyLocationListener implements LocationListener {
    private String mGpsStatus;
    private String mNetworkStatus;
    private double mLatitude;
    private double mLongitude;
    private Date mTime;
    private LocationManager locationManager;

    public String getmGpsStatus() {
        return mGpsStatus;
    }

    public String getmNetworkStatus() {
        return mNetworkStatus;
    }

    public double getmLatitude() {
        return mLatitude;
    }

    public double getmLongitude() {
        return mLongitude;
    }

    public Date getmTime() {
        return mTime;
    }



    public MyLocationListener(LocationManager lm){
        locationManager = lm;
    }
    @Override
    public void onLocationChanged(Location location) {
        setLatLonitude(location);
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        if (provider.equals(LocationManager.GPS_PROVIDER)) {
            mGpsStatus = String.valueOf(status);
        } else if (provider.equals(LocationManager.NETWORK_PROVIDER)) {
            mNetworkStatus = String.valueOf(status);
        }
    }
    @Override
    public void onProviderEnabled(String provider) {
        checkEnable();
        setLatLonitude(locationManager.getLastKnownLocation(provider));
    }
    @Override
    public void onProviderDisabled(String provider) {
        checkEnable();
    }
    private void checkEnable(){
        mGpsStatus = String.valueOf(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER));
        mNetworkStatus = String.valueOf(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER));
    }
    private void setLatLonitude(Location location){
        if(location !=null){
            mTime = new Date(location.getTime());
            mLatitude = location.getLatitude();
            mLongitude = location.getLongitude();
        }
    }
}
