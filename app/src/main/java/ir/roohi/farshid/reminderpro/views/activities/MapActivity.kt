package ir.roohi.farshid.reminderpro.views.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import ir.roohi.farshid.reminderpro.R
import kotlinx.android.synthetic.main.activity_map.*
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay


/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 1/1/19.
 */
class MapActivity : BaseActivity() {

    private var mMyLocationOverlay: MyLocationNewOverlay? = null

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, MapActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.getInstance().load(applicationContext, PreferenceManager.getDefaultSharedPreferences(this))
        setContentView(R.layout.activity_map)


        mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)
        mapView.setMultiTouchControls(true)
        mapView.setHasTransientState(true)
        mapView.controller.setZoom(15)
        mapView.dispatchSetSelected(true)
        mapView.setBuiltInZoomControls(false)
        fabMyLocation.setOnClickListener { myLocation() }
        myLocation()


//        val compassOverlay = CompassOverlay(this, InternalCompassOrientationProvider(this), mapView)
//        compassOverlay.enableCompass()
//        mapView.overlays.add(compassOverlay)

        //your items
//        val items = ArrayList<OverlayItem>()
//        items.add(OverlayItem("Title", "Description", GeoPoint(0.0, 0.0))) // Lat/Lon decimal degrees
//
//        val mOverlay = ItemizedOverlayWithFocus<OverlayItem>(this,items,
//                object : ItemizedIconOverlay.OnItemGestureListener<OverlayItem> {
//                    override fun onItemSingleTapUp(index: Int, item: OverlayItem): Boolean {
//                        //do something
//                        return true
//                    }
//
//                    override fun onItemLongPress(index: Int, item: OverlayItem): Boolean {
//                        return false
//                    }
//                })
//
//        mOverlay.setFocusItemsOnTap(true)
//        mapView.overlays.add(mOverlay)


    }


    private fun myLocation() {
        val mapController = mapView.controller

        if (mMyLocationOverlay == null) {
            mMyLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(this), mapView)
//            mMyLocationOverlay!!.setPersonIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_person_pin))
            val gp = GeoPoint(35.6980012, 51.3337879)
            mapController.setCenter(gp)
        }
        mMyLocationOverlay!!.enableMyLocation()
        mMyLocationOverlay!!.enableFollowLocation()
        mMyLocationOverlay!!.isDrawAccuracyEnabled = true
        mMyLocationOverlay!!.runOnFirstFix {
            runOnUiThread {
                mapController.animateTo(mMyLocationOverlay!!.myLocation)
                mapController.setZoom(18)
            }
        }
        mapView.overlays.add(mMyLocationOverlay)

    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }
}