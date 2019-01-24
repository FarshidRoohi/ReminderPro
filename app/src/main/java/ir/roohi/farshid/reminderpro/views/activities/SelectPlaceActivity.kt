package ir.roohi.farshid.reminderpro.views.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PointF
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.mapbox.android.core.location.LocationEngineListener
import com.mapbox.android.core.location.LocationEnginePriority
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.mapboxsdk.annotations.Marker
import com.mapbox.mapboxsdk.annotations.MarkerView
import com.mapbox.mapboxsdk.annotations.MarkerViewOptions
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.location.LocationComponentOptions
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.location.modes.RenderMode
import com.mapbox.mapboxsdk.maps.MapboxMap
import ir.roohi.farshid.reminderpro.R
import ir.roohi.farshid.reminderpro.Storage
import ir.roohi.farshid.reminderpro.customViews.AlertDialog
import ir.roohi.farshid.reminderpro.keys.MAP_STYLE_URL
import ir.roohi.farshid.reminderpro.listener.OnInformationLocationListener
import ir.roohi.farshid.reminderpro.listener.OnPermissionRequestListener
import ir.roohi.farshid.reminderpro.map.PulseMarkerViewAdapter
import ir.roohi.farshid.reminderpro.viewModel.LocationViewModel
import ir.roohi.farshid.reminderpro.views.bottomSheet.InformationLocationBottomSheet
import kotlinx.android.synthetic.main.activity_select_place.*


/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 1/10/19.
 */
class SelectPlaceActivity : BaseActivity(), OnPermissionRequestListener {

    private var mapboxMap: MapboxMap? = null
    private var addressPin: Marker? = null
    private var userMarker: MarkerView? = null
    private var dropPinView: ImageView? = null
    private lateinit var viewModel: LocationViewModel

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, SelectPlaceActivity::class.java))
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_place)
        mapView.onCreate(savedInstanceState)
        createDropPin()

        viewModel = ViewModelProviders.of(this).get(LocationViewModel::class.java)

        var url = Storage(this)[String::class.java, MAP_STYLE_URL]
        if (url == null) {
            url = getString(R.string.map_default_style)
        }
        mapView.setStyleUrl(url!!)

        requestPermission(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
            this
        )

        fabMyLocation.setOnClickListener { enableLocationComponent() }

        mapView.getMapAsync {
            mapboxMap = it
            mapboxMap!!.uiSettings.isAttributionEnabled = false
            mapboxMap!!.uiSettings.isLogoEnabled = false
            enableLocationComponent()
            mapboxMap!!.markerViewManager.addMarkerViewAdapter(
                PulseMarkerViewAdapter(
                    this
                )
            )
            trackUserLocationView(userMarker)
        }

        btnSelect.setOnClickListener {

            val bottomSheet =
                InformationLocationBottomSheet(supportFragmentManager, object : OnInformationLocationListener {
                    override fun onInformationLocation(title: String, desc: String?, distance: Int) {

                        if (mapboxMap != null) {
                            val position: LatLng = getLocationPickerLocation()

                            //Create new one
                            addressPin = mapboxMap!!.addMarker(MarkerViewOptions().title(title).position(position))
                            mapboxMap!!.selectMarker(addressPin!!)

                            viewModel.add(title, desc, true, position, distance)
                        }
                    }
                })
            bottomSheet.show()


        }

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

    private fun createDropPin() {
        val density = resources.displayMetrics.density

        dropPinView = ImageView(this)
        dropPinView!!.setImageResource(R.drawable.ic_pin)
        val params = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            Gravity.CENTER
        )
        params.bottomMargin = (12 * density).toInt()
        dropPinView!!.layoutParams = params

        mapView.addView(dropPinView)
    }

    /**
     * Tracks the camera to animate the marker when overlapping with the picker.
     * Makes sure the marker actually points to the user's position by tracking it.
     */
    private fun trackUserLocationView(markerView: MarkerView?) {
        val circleDiameterSize = resources.getDimension(R.dimen.circle_size)

        //Track camera changes to check for overlap
        mapboxMap!!.setOnCameraChangeListener(object : MapboxMap.OnCameraChangeListener {

            private var pulseAnimation: Animation? = null

            override fun onCameraChange(position: CameraPosition) {
                if (markerView == null) {
                    return
                }

                //Make drop pin visible, if it wasn't already
                showDropPin()

                //Get the distance from the tip of the location picker to the MarkerView
                val distance = getLocationPickerLocation().distanceTo(markerView.position)

                //If closeby, animate, otherwise, stop animation
                val view = mapboxMap!!.markerViewManager.getView(markerView)
                if (view != null) {
                    val backgroundView = view.findViewById<ImageView>(R.id.background_imageview)
                    if (pulseAnimation == null && distance < 0.5f * circleDiameterSize) {
                        pulseAnimation = AnimationUtils.loadAnimation(this@SelectPlaceActivity, R.anim.pulse)
                        pulseAnimation!!.repeatCount = Animation.INFINITE
                        pulseAnimation!!.repeatMode = Animation.RESTART
                        backgroundView.startAnimation(pulseAnimation)
                    } else if (pulseAnimation != null && distance >= 0.5f * circleDiameterSize) {
                        backgroundView.clearAnimation()
                        pulseAnimation = null
                    }
                }
            }
        })

        mapboxMap!!.locationComponent.locationEngine!!.addLocationEngineListener(object : LocationEngineListener {
            override fun onLocationChanged(location: Location?) {
                if (location != null && markerView != null) {
                    markerView.position = LatLng(location)
                }
            }

            override fun onConnected() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })

    }

    private fun showDropPin() {
        if (dropPinView != null && this.dropPinView!!.visibility !== View.VISIBLE) {
            dropPinView!!.visibility = View.VISIBLE
        }
    }

    private fun getLocationPickerLocation(): LatLng {
        return mapboxMap!!.projection.fromScreenLocation(
            PointF((dropPinView!!.left + dropPinView!!.width / 2).toFloat(), dropPinView!!.bottom.toFloat())
        )
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


    override fun onAllow(permission: String) {
//        if (checkPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION))) {
//
//        }
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


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
}
