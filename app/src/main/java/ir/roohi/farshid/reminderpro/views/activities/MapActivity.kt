package ir.roohi.farshid.reminderpro.views.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.mapbox.android.core.location.LocationEngineListener
import com.mapbox.android.core.location.LocationEnginePriority
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.mapboxsdk.location.LocationComponentOptions
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.location.modes.RenderMode
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import ir.roohi.farshid.reminderpro.R
import ir.roohi.farshid.reminderpro.customViews.AlertDialog
import ir.roohi.farshid.reminderpro.listener.OnPermissionRequestListener
import kotlinx.android.synthetic.main.activity_map.*
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.camera.CameraPosition
import androidx.core.app.ShareCompat.IntentBuilder
import com.mapbox.mapboxsdk.plugins.places.picker.PlacePicker
import com.mapbox.mapboxsdk.plugins.places.picker.model.PlacePickerOptions


/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 1/1/19.
 */
class MapActivity : BaseActivity(), OnPermissionRequestListener, OnMapReadyCallback {


    private var mapboxMap: MapboxMap? = null

    private val REQUEST_CODE = 241

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, MapActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        mapView.onCreate(savedInstanceState)

//        mapView.setStyleUrl("mapbox://styles/farshidroohi/cjqpsbsar015p2qmk8zmwqkxy")
        mapView.setStyleUrl("mapbox://styles/farshidroohi/cjqpsdw2v19br2sqwpr1m10ta")
        requestPermission(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
            this
        )
        mapView.getMapAsync(this)

        fabMyLocation.setOnClickListener {
            enableLocationComponent()
        }
        fabSelectPlace.setOnClickListener {
            goToPickerActivity()
        }


    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == Activity.RESULT_CANCELED) {
//            Log.i("myTag", "result : canceled")
//
//        } else if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
//            val cameraFuture = PlacePicker.getPlace(data)
//            Log.i("myTag", "result : ${cameraFuture!!.address()}")
//            Log.i("myTag", "result : ${cameraFuture!!.placeName()}")
//            Log.i("myTag", "result : ${cameraFuture!!.toJson()}")
//        }
//    }

    private fun goToPickerActivity() {
//        startActivityForResult(
//            PlacePicker.IntentBuilder()
//                .accessToken(getString(R.string.api_mapbox_key))
//                .placeOptions(
//                    PlacePickerOptions.builder()
//                        .statingCameraPosition(
//                            CameraPosition.Builder()
//                                .target(LatLng(35.7064704, 51.3171456)).zoom(16.0).build()
//                        )
//                        .build()
//                )
//                .build(this), REQUEST_CODE
//        )
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

        val locationComponent = mapboxMap!!.locationComponent

        locationComponent.activateLocationComponent(this, getLocationComponent())
        locationComponent.locationEngine!!.priority = LocationEnginePriority.BALANCED_POWER_ACCURACY
        locationComponent.locationEngine!!.interval = 500
        locationComponent.locationEngine!!.activate()
        locationComponent.locationEngine!!.requestLocationUpdates()

        Log.i("myTAG", "longitude : ${locationComponent.locationEngine!!.lastLocation.longitude}")
        Log.i("myTAG", "latitude : ${locationComponent.locationEngine!!.lastLocation.latitude}")

        locationComponent.locationEngine!!.addLocationEngineListener(object : LocationEngineListener {
            override fun onLocationChanged(location: Location?) {
            }

            override fun onConnected() {
            }

        })

        locationComponent.isLocationComponentEnabled = true

        locationComponent.cameraMode = CameraMode.TRACKING
        locationComponent.renderMode = RenderMode.NORMAL
        locationComponent.zoomWhileTracking(20.0)

    }

    private fun getLocationComponent(): LocationComponentOptions {
        return LocationComponentOptions.builder(this)
            .trackingGesturesManagement(true)
            .accuracyColor(ContextCompat.getColor(this, R.color.colorAccent))
            .accuracyAnimationEnabled(true)
            .enableStaleState(true)
            .compassAnimationEnabled(true)
            .build()
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