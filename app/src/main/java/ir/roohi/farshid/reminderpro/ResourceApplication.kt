package ir.roohi.farshid.reminderpro

import android.content.Context
import android.os.Environment

import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2018.
 */


class ResourceApplication(val context: Context) {

    private val storage: Storage = Storage(context)
    private var dirApplication: String

    init {
        this.initCalligraphy()
        this.dirApplication = Environment.getExternalStorageDirectory().absolutePath
    }

    private fun initCalligraphy() {
        ViewPump.init(ViewPump.builder().addInterceptor(CalligraphyInterceptor(
                CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/IRSANS_light.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build())).build())
    }

    companion object {

        var applicationResource: ResourceApplication? = null
            private set

        fun build(context: Context) {
            applicationResource = ResourceApplication(context)
        }
    }

    fun getStorage(): Storage {
        return storage
    }

    fun getDirApplication(): String {
        return dirApplication
    }

    fun getDirSoundSave(): String {
        return this.dirApplication + "/reminderPro/record/"
    }

}
