package ir.roohi.farshid.reminderpro.views.activities

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.preference.PreferenceManager
import ir.roohi.farshid.reminderpro.R
import kotlinx.android.synthetic.main.activity_map.*
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay


/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 1/1/19.
 */
class MapActivity : BaseActivity() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, MapActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.getInstance().load(applicationContext, PreferenceManager.getDefaultSharedPreferences(this))
        setContentView(R.layout.activity_map)


        mapView.setMultiTouchControls(true)
        mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)
        mapView.setMultiTouchControls(true)
        mapView.setHasTransientState(true)
        mapView.controller.setZoom(5)


        val mMyLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(this), mapView)

        mMyLocationOverlay.setPersonIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_person_pin))


        val mapController = mapView.controller

        mMyLocationOverlay.enableMyLocation()
        mMyLocationOverlay.enableFollowLocation()
        mMyLocationOverlay.isDrawAccuracyEnabled = true
        mMyLocationOverlay.runOnFirstFix {
            runOnUiThread {
                mapController.animateTo(mMyLocationOverlay.myLocation)
                mapController.setZoom(18)
            }
        }
        mapView.overlays.add(mMyLocationOverlay)


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

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }
}