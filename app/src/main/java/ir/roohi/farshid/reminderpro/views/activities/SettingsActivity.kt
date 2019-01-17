package ir.roohi.farshid.reminderpro.views.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import ir.roohi.farshid.reminderpro.R
import kotlinx.android.synthetic.main.activity_settings.*
import java.util.*

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 1/17/19.
 */
class SettingsActivity : BaseActivity() {

    private var sharedPreferences: SharedPreferences? = null
    private var currentLanguage: String? = null

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, SettingsActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        this.currentLanguage = sharedPreferences!!.getString("LANGUAGE", "EN")
        setLocale(this.currentLanguage!!)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        toolbar.getLeftImageView().setOnClickListener { finish() }

        btnChangeLanguage.text =
                if (this.currentLanguage == "FA") getString(R.string.change_to_en) else getString(R.string.change_to_fa)

        btnChangeLanguage.setOnClickListener {
            currentLanguage = if (currentLanguage!! == "EN") "FA" else "EN"
            sharedPreferences!!.edit().putString("LANGUAGE", currentLanguage!!).apply()
            setLocale(this.currentLanguage!!)
            recreate()
        }

    }

    private fun setLocale(lang: String) {
        val locale = Locale(lang)
        val res = resources
        val displayMatris = res.displayMetrics
        val configuration = res.configuration
        configuration.locale = locale
        res.updateConfiguration(configuration, displayMatris)

    }
}