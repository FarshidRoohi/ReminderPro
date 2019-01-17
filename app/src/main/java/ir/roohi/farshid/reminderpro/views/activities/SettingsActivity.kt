package ir.roohi.farshid.reminderpro.views.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import ir.roohi.farshid.reminderpro.R
import kotlinx.android.synthetic.main.activity_settings.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 1/17/19.
 */
class SettingsActivity : BaseActivity() {



    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, SettingsActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        toolbar.getLeftImageView().setOnClickListener { finish() }

        btnChangeLanguage.text =
                if (this.currentLanguage == "FA") getString(R.string.change_to_en) else getString(R.string.change_to_fa)

        btnChangeLanguage.setOnClickListener {
            currentLanguage = if (currentLanguage!! == "EN") "FA" else "EN"
            sharedPreferences!!.edit().putString("LANGUAGE", currentLanguage!!).apply()
            setLocale(this.currentLanguage!!)
            EventBus.getDefault().post("change language $currentLanguage")
            recreate()
        }

    }
}