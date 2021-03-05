package ir.roohi.farshid.reminderpro

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import androidx.multidex.MultiDex
import com.google.firebase.analytics.FirebaseAnalytics
import com.mapbox.mapboxsdk.Mapbox
import io.fabric.sdk.android.Fabric

import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import ir.roohi.farshid.reminderpro.utility.getDeviceName
import java.io.File

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2018.
 */


class ResourceApplication(val context: Context) {

    private val storage: Storage = Storage(context)
    private var dirApplication: String
    private var firebaseAnalytics = FirebaseAnalytics.getInstance(context)
    private var bundleLog: Bundle? = null

    init {
        this.initCalligraphy()
        this.dirApplication = Environment.getExternalStorageDirectory().absolutePath
        File(getDirSoundSave()).mkdirs()
        MultiDex.install(context)
        Mapbox.getInstance(context, context.getString(R.string.api_mapbox_key))
        Fabric.Builder(context).build()


        // for share audio file strict mode
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
    }

    companion object {

        var applicationResource: ResourceApplication? = null
            private set

        fun build(context: Context) {
            applicationResource = ResourceApplication(context)
        }
    }

    public fun getAnalytics(): FirebaseAnalytics {
        return this.firebaseAnalytics
    }

    fun getBundleAnalytics(): Bundle {
        if (bundleLog == null){
            bundleLog = Bundle()
            bundleLog!!.putString("USER_DEVICE", getDeviceName())
        }
        return bundleLog!!

    }

    private fun initCalligraphy() {
        ViewPump.init(
            ViewPump.builder().addInterceptor(
                CalligraphyInterceptor(
                    CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/IRSANS_light.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
                )
            ).build()
        )
    }


    fun mkdirsSoundDirectory(): Boolean = File(getDirSoundSave()).mkdirs()


    fun getStorage(): Storage = storage

    fun getDirApplication(): String = dirApplication

    fun getDirSoundSave(): String {
        return String.format(
            "%s%s%s%s",
            this.dirApplication,
            "/android/data/",
            this.context.packageName!!.toString(),
            "/record/"
        )
    }

}
