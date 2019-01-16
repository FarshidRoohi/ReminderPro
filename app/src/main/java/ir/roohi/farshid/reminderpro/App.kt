package ir.roohi.farshid.reminderpro

import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.mapbox.mapboxsdk.Mapbox
import io.fabric.sdk.android.Fabric

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2018.
 */
class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(applicationContext)
        ResourceApplication.build(this)
        Mapbox.getInstance(this, getString(R.string.api_mapbox_key))
        Fabric.Builder(applicationContext).build()
    }
}
