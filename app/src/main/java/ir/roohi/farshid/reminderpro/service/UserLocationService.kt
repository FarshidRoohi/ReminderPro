package ir.roohi.farshid.reminderpro.service

import android.Manifest
import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import ir.roohi.farshid.reminderpro.R
import ir.roohi.farshid.reminderpro.keys.NOTIFICATION_CHANNEL_ID
import ir.roohi.farshid.reminderpro.model.LocationEntity
import ir.roohi.farshid.reminderpro.views.activities.AlarmActivity
import ir.roohi.farshid.reminderpro.views.activities.LocationListActivity


/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 1/5/19.
 */
@SuppressLint("LogNotTimber")
class UserLocationService : Service() {

    private val NOTIFICATION_ID = 100001

    private val MINIMUM_DISTANCE_CHANGE_FOR_UPDATES: Long = 5 // Meters
    private val MINIMUM_TIME_BETWEEN_UPDATES: Long = 3000 // in Milliseconds
    private var locationManager: LocationManager? = null
    private var instance: UserLocationService? = null
    private var isGPSEnabled = false
    private var isNetworkEnabled = false
    private val TAG = "LOCATION_SERVICE"

    private var notificationManager: NotificationManager? = null
    private var notificationBuilder: NotificationCompat.Builder? = null

    private var locationList: ArrayList<LocationEntity> = java.util.ArrayList()

    override fun onBind(arg0: Intent): IBinder? {
        return null
    }

    @SuppressLint("MissingPermission")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        instance = this

        this.locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        this.isGPSEnabled = this.locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
        this.isNetworkEnabled = this.locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        this.notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        this.notificationBuilder = NotificationCompat.Builder(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.notificationBuilder!!.setChannelId(
                createNotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    "notification location reminder"
                )
            )
        }

        this.notificationBuilder!!.setContentTitle(getString(R.string.locations))
        this.notificationBuilder!!.setContentText(getString(R.string.location_service_active))
        this.notificationBuilder!!.setAutoCancel(false)
        this.notificationBuilder!!.setSmallIcon(R.drawable.ic_pin)

        val intentNotification = Intent(this, LocationListActivity::class.java)
        intentNotification.action = Intent.ACTION_MAIN
        intentNotification.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        val pendingIntent = PendingIntent.getActivity(this, 101, intentNotification, PendingIntent.FLAG_UPDATE_CURRENT)
        this.notificationBuilder!!.setContentIntent(pendingIntent)

        locationList = (intent!!.getParcelableArrayListExtra("locationEntity"))
        locationList.forEach {
            Log.i(LOCATION_SERVICE, "title ${it.title} status : ${it.status}")
            if (it.status) {
                startForeground(NOTIFICATION_ID, this.notificationBuilder!!.build())
                locationListener()
                return START_STICKY
            }
        }

        kill()

        return START_STICKY
    }

    @SuppressLint("MissingPermission")
    private fun locationListener() {

        if (isAllowPermission()) {
            val provider = if (this.isGPSEnabled) LocationManager.GPS_PROVIDER else LocationManager.NETWORK_PROVIDER

            this.locationManager!!.requestLocationUpdates(
                provider,
                MINIMUM_TIME_BETWEEN_UPDATES,
                MINIMUM_DISTANCE_CHANGE_FOR_UPDATES.toFloat(),
                MyLocationListener()
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String {
        val chan = NotificationChannel(
            channelId,
            channelName, NotificationManager.IMPORTANCE_NONE
        )
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }

    fun isRunnig(): Boolean {
        return instance != null
    }

    override fun onDestroy() {
        super.onDestroy()
        instance = null
        locationManager = null
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
        if (instance == null) {
            locationList.clear()
            return
        }
        locationList.forEach {
            val selectLocation = Location("SelectLocation")
            selectLocation.longitude = it.longitude
            selectLocation.latitude = it.latitude
            if (location.distanceTo(selectLocation) <= it.distance) {
                AlarmActivity.start(this, it)
                kill()
                return
            }
        }
    }

    private fun kill() {
        notificationManager!!.cancelAll()
        stopSelf()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            stopForeground(true)
        }
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