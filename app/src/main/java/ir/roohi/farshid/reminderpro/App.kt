package ir.roohi.farshid.reminderpro

import android.app.Application

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2018.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        ResourceApplication.build(this)

    }
}
