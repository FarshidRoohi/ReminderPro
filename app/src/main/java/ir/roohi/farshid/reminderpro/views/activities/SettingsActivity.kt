package ir.roohi.farshid.reminderpro.views.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.view.ViewCompat
import com.nex3z.togglebuttongroup.SingleSelectToggleGroup
import com.nex3z.togglebuttongroup.SingleSelectToggleGroup.OnCheckedChangeListener
import ir.roohi.farshid.reminderpro.R
import kotlinx.android.synthetic.main.activity_settings.*
import org.greenrobot.eventbus.EventBus
import javax.security.auth.login.LoginException


/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 1/17/19.
 */
class SettingsActivity : BaseActivity(), OnCheckedChangeListener {

    private var currentIdSelect = R.id.choiceEn

    override fun onCheckedChanged(group: SingleSelectToggleGroup?, checkedId: Int) {
        if (currentIdSelect != checkedId) {
            currentIdSelect = checkedId

            currentLanguage = if (currentIdSelect == R.id.choiceFa) "FA" else "EN"
            sharedPreferences!!.edit().putString("LANGUAGE", currentLanguage!!).apply()
            setLocale(this.currentLanguage!!)
            EventBus.getDefault().post("change language $currentLanguage")
        }
    }


    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, SettingsActivity::class.java))
        }
    }

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        toolbar.rotateLayoutDirection()

        toolbar.getLeftImageView().setOnClickListener { finish() }

        groupToggle.setOnCheckedChangeListener(this)
        currentIdSelect = if (this.currentLanguage == "FA") R.id.choiceFa else R.id.choiceEn
        groupToggle.check(currentIdSelect)
    }
}