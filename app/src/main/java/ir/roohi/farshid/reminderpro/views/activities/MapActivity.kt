package ir.roohi.farshid.reminderpro.views.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.mapbox.mapboxsdk.Mapbox
import ir.roohi.farshid.reminderpro.R
import ir.roohi.farshid.reminderpro.customViews.AlertDialog
import ir.roohi.farshid.reminderpro.listener.OnPermissionRequestListener
import kotlinx.android.synthetic.main.activity_map.*
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.mapboxsdk.constants.Style
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.location.LocationComponent
import com.mapbox.mapboxsdk.location.modes.RenderMode
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import androidx.core.content.ContextCompat
import com.mapbox.mapboxsdk.location.LocationComponentOptions
import com.mapbox.mapboxsdk.maps.MapView


/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 1/1/19.
 */
class MapActivity : BaseActivity(), OnPermissionRequestListener, OnMapReadyCallback {


    private var mapboxMap: MapboxMap? = null

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, MapActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(this, getString(R.string.api_mapbox_key))
        setContentView(R.layout.activity_map)
        mapView.onCreate(savedInstanceState)
        requestPermission(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION), this)
        mapView.getMapAsync(this)

    }

    override fun onMapReady(mpx: MapboxMap) {
        mapboxMap = mpx
        mapboxMap!!.uiSettings.isAttributionEnabled = false
        mapboxMap!!.uiSettings.isLogoEnabled = false

        enableLocationComponent()
    }

    @SuppressLint("MissingPermission")
    private fun enableLocationComponent() {

        if (!PermissionsManager.areLocationPermissionsGranted(this)) {
            return
        }
        val options = LocationComponentOptions.builder(this)
            .trackingGesturesManagement(true)
            .accuracyColor(ContextCompat.getColor(this, R.color.colorAccent))
            .build()

        val locationComponent = mapboxMap!!.locationComponent

        locationComponent.activateLocationComponent(this, options)

        locationComponent.isLocationComponentEnabled = true
        locationComponent.activateLocationComponent(this)

        locationComponent.cameraMode = CameraMode.TRACKING
        locationComponent.renderMode = RenderMode.COMPASS
    }

    override fun onAllow(permission: String) {
        if (checkPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION))) {

        }
    }

    override fun onDenied(permission: String) {
        val alertBuilder = AlertDialog.Builder(
            supportFragmentManager,
            getString(R.string.permission), getString(R.string.permission_location)
        )
        alertBuilder.setBtnPositive(getString(R.string.yes), View.OnClickListener {
            requestPermission(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), this)
            alertBuilder.dialog!!.dismissAllowingStateLoss()
        })
        alertBuilder.setBtnNegative(getString(R.string.no), View.OnClickListener {
            finish()
        })
        alertBuilder.build().show()
    }

    public override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    public override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    public override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    public override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
}