package ir.roohi.farshid.reminderpro.service

import android.Manifest
import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.core.app.ActivityCompat

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 1/5/19.
 */
class UserLocationService :Service() {

    private val MINIMUM_DISTANCE_CHANGE_FOR_UPDATES: Long = 10 // Meters
    private val MINIMUM_TIME_BETWEEN_UPDATES: Long = 5000 // in Milliseconds
    private var locationManager: LocationManager? = null
    private var instance: UserLocationService? = null
    internal var isGPSEnabled = false
    internal var isNetworkEnabled = false
    private val TAG = "LOCATION_SERVICE"

    override fun onBind(arg0: Intent): IBinder? {
        return null
    }

    @SuppressLint("MissingPermission")
    override fun onCreate() {
        super.onCreate()
        instance = this

        this.locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        this.isGPSEnabled = this.locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
        this.isNetworkEnabled = this.locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (isAllowPermission()) {

            val provider = if (this.isGPSEnabled) LocationManager.GPS_PROVIDER else LocationManager.NETWORK_PROVIDER

            this.locationManager!!.requestLocationUpdates(
                    provider,
                    MINIMUM_TIME_BETWEEN_UPDATES,
                    MINIMUM_DISTANCE_CHANGE_FOR_UPDATES.toFloat(),
                    MyLocationListener())

            // Location location = this.locationManager.getLastKnownLocation(provider);
            //Log.i(TAG, "onCreate location : " + location.toString());


            //            if (this.isGPSEnabled) {
            //                this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
            //                        MINIMUM_TIME_BETWEEN_UPDATES,
            //                        MINIMUM_DISTANCE_CHANGE_FOR_UPDATES, new MyLocationListener());
            //
            //                Location location = locationManager
            //                        .getLastKnownLocation(LocationManager.GPS_PROVIDER);
            //
            //            } else if (isNetworkEnabled) {
            //                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
            //                        MINIMUM_TIME_BETWEEN_UPDATES,
            //                        MINIMUM_DISTANCE_CHANGE_FOR_UPDATES, new MyLocationListener());
            //                Location location = locationManager
            //                        .getLastKnownLocation(LocationManager.GPS_PROVIDER);
            //                Log.i(TAG, "onCreate: " + location.toString());
            //            }
        }
    }

    fun isRunnig(): Boolean {
        return instance != null
    }

    override fun onDestroy() {
        super.onDestroy()
        instance = null
        Log.d(TAG, "GPS Service destroyed ...")
    }

    private inner class MyLocationListener : LocationListener {
        override fun onLocationChanged(location: Location) {
            checkLocation(location)
        }

        override fun onStatusChanged(s: String, i: Int, b: Bundle) {}

        override fun onProviderDisabled(s: String) {}

        override fun onProviderEnabled(s: String) {}
    }

    private fun checkLocation(location: Location) {

    }


    private fun isAllowPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) === PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) === PackageManager.PERMISSION_GRANTED
    }

}