package ir.roohi.farshid.reminderpro.views.activities

import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import ir.roohi.farshid.reminderpro.R
import kotlinx.android.synthetic.main.activity_splash.*

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
        }, 3000)
    }
}