package ir.roohi.farshid.reminderpro.views.activities

import android.os.Bundle
import android.os.Handler
import ir.roohi.farshid.reminderpro.R

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 12/28/18.
 */
class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hasFullScreen()
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            DashboardActivity.start(this)
            finish()
        }, 1000)
    }
}