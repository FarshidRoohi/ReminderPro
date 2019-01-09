package ir.roohi.farshid.reminderpro

import android.app.Application
import com.mapbox.mapboxsdk.Mapbox

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2018.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        ResourceApplication.build(this)
        Mapbox.getInstance(this, getString(R.string.api_mapbox_key))
    }
}
