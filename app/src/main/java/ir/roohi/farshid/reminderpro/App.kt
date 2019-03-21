package ir.roohi.farshid.reminderpro

import androidx.multidex.MultiDexApplication

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2018.
 */
class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        ResourceApplication.build(this)
    }
}
