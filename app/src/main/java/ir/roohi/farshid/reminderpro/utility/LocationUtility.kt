package ir.roohi.farshid.reminderpro.utility

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.app.ActivityCompat

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2/16/19.
 */
class LocationUtility(val context: Context) {

    private var locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    private var isGPSEnabled = false
    private var isNetworkEnabled = false

    init {
        this.isGPSEnabled = this.locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        this.isNetworkEnabled = this.locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    @SuppressLint("MissingPermission")
    fun isFarLocation(lat: Double, lon: Double, distance: Int): Boolean {

        if (!isAllowPermission()) return false

        val provider = if (this.isGPSEnabled) LocationManager.GPS_PROVIDER else LocationManager.NETWORK_PROVIDER

        val currentLocation = this.locationManager.getLastKnownLocation(provider) ?: return true

        val location = Location("location_select")
        location.latitude = lat
        location.longitude = lon
        return currentLocation.distanceTo(location) > distance
    }

    private fun isAllowPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

}