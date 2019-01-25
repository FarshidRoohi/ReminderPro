package ir.roohi.farshid.reminderpro.service

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationManager
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
import androidx.core.app.NotificationCompat
import ir.roohi.farshid.reminderpro.keys.NOTIFICATION_CHANNEL_ID
import ir.roohi.farshid.reminderpro.model.LocationEntity

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 1/5/19.
 */
@SuppressLint("LogNotTimber")
class UserLocationService : Service() {

    private val MINIMUM_DISTANCE_CHANGE_FOR_UPDATES: Long = 5 // Meters
    private val MINIMUM_TIME_BETWEEN_UPDATES: Long = 5000 // in Milliseconds
    private var locationManager: LocationManager? = null
    private var instance: UserLocationService? = null
    private var isGPSEnabled = false
    private var isNetworkEnabled = false
    private val TAG = "LOCATION_SERVICE"

    private var locationItem: LocationEntity? = null

    private var notificationManager: NotificationManager? = null
    private var notificationBuilder: NotificationCompat.Builder? = null

    override fun onBind(arg0: Intent): IBinder? {
        return null
    }

    @SuppressLint("MissingPermission")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        instance = this
        Log.d(TAG, "GPS onStartCommand ...")

        this.notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        this.notificationBuilder = NotificationCompat.Builder(this)
        this.notificationBuilder!!.setChannelId(NOTIFICATION_CHANNEL_ID)


         locationItem  = intent!!.getParcelableExtra("locationEntity")

        Log.d(TAG, " service tag start : title : ${locationItem!!.title}")

        this.locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        this.isGPSEnabled = this.locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
        this.isNetworkEnabled = this.locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (isAllowPermission()) {

            val provider = if (this.isGPSEnabled) LocationManager.GPS_PROVIDER else LocationManager.NETWORK_PROVIDER

            this.locationManager!!.requestLocationUpdates(
                provider,
                MINIMUM_TIME_BETWEEN_UPDATES,
                MINIMUM_DISTANCE_CHANGE_FOR_UPDATES.toFloat(),
                MyLocationListener()
            )
        }


        return START_STICKY
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
        Log.d(TAG, "location :latitude :  ${location.latitude} || longitude : ${location.longitude}")

        val selectLocation = Location("SelectLocation")
        selectLocation.longitude = locationItem!!.longitude
        selectLocation.latitude = locationItem!!.latitude
        Log.i(TAG, "location distance : ${location.distanceTo(selectLocation)}")
    }


    private fun isAllowPermission(): Boolean {

        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

}