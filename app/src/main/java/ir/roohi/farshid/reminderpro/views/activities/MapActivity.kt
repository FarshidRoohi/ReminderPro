package ir.roohi.farshid.reminderpro.views.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.mapbox.mapboxsdk.Mapbox
import ir.roohi.farshid.reminderpro.R
import ir.roohi.farshid.reminderpro.customViews.AlertDialog
import ir.roohi.farshid.reminderpro.listener.OnPermissionRequestListener
import kotlinx.android.synthetic.main.activity_map.*


/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 1/1/19.
 */
class MapActivity : BaseActivity(), OnPermissionRequestListener {


    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, MapActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(this, "pk.eyJ1IjoiZmFyc2hpZHJvb2hpIiwiYSI6ImNqcW05aHBwZjE5N2o0OG5ubmsxMnc4cWYifQ.2RXagyMWkt5gft6NLdLCtw")
        setContentView(R.layout.activity_map)
        mapView.onCreate(savedInstanceState)
    }

    override fun onAllow(permission: String) {
        if (checkPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION))) {
        }
    }

    override fun onDenied(permission: String) {
        val alertBuilder = AlertDialog.Builder(supportFragmentManager,
                getString(R.string.permission), getString(R.string.permission_location))
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