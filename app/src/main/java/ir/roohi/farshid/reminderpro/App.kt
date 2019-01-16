package ir.roohi.farshid.reminderpro

import android.os.StrictMode
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
        ResourceApplication.build(this)
        MultiDex.install(applicationContext)
        Mapbox.getInstance(this, getString(R.string.api_mapbox_key))
        Fabric.Builder(applicationContext).build()

        // for share audio file strict mode
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
    }
}
