package ir.roohi.farshid.reminderpro.views.activities

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.Window
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.Nullable
import ir.roohi.farshid.reminderpro.ResourceApplication
import ir.roohi.farshid.reminderpro.listener.IEventBus
import ir.roohi.farshid.reminderpro.listener.OnPermissionRequestListener
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.*

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2018.
 */
@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity(), IEventBus {

    private var listener: OnPermissionRequestListener? = null
    public var sharedPreferences: SharedPreferences? = null
    public var currentLanguage: String? = null

    companion object {

        private const val PERMISSION_REQUEST_ID = 901
    }

    var resourceApp: ResourceApplication? = null
        private set


    override fun onCreate(savedInstanceState: Bundle?) {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        this.currentLanguage = sharedPreferences!!.getString("LANGUAGE", "EN")
        setLocale(this.currentLanguage!!)
        super.onCreate(savedInstanceState)
        this.resourceApp = ResourceApplication.applicationResource

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    fun setContentView(@LayoutRes layoutResID: Int, @ColorRes color: Int) {
        super.setContentView(layoutResID)
        setStatusBarColor(color)
    }

    fun hasFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    fun setStatusBarColor(@ColorRes color: Int) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return
        }

        val window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = contextCompatColor(color)
    }

    fun contextCompatColor(@ColorRes color: Int): Int {
        return ContextCompat.getColor(this, color)
    }


    fun <T : ViewDataBinding> setBindingView(@LayoutRes layout: Int): T {
        return DataBindingUtil.setContentView(this, layout)
    }

    fun showMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermission(permissions: Array<String>, listener: OnPermissionRequestListener) {


        this.listener = listener

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            this.listener!!.onAllow("Current SDK Version lower than API 23")
            return
        }


        val permissionList = ArrayList<String>()


        for (permission in permissions) {
            if (checkPermissions(arrayOf(permission))) {
                this.listener!!.onAllow(permission)
                continue
            }
            permissionList.add(permission)
        }

        if (permissionList.size <= 0) {
            return
        }
        requestPermissions(permissionList.toTypedArray(), PERMISSION_REQUEST_ID)


    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {


        if (requestCode != PERMISSION_REQUEST_ID) {
            return
        }

        for (i in permissions.indices) {

            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                this.listener!!.onAllow(permissions[i])
                continue
            }

            listener!!.onDenied(permissions[i])

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    override fun onChangeLanguage(str: String) {
        recreate()
    }


    open fun setLocale(lang: String) {
        val locale = Locale(lang)
        val res = resources
        val displayMatris = res.displayMetrics
        val configuration = res.configuration
        configuration.setLayoutDirection(locale)
        configuration.locale = locale
        res.updateConfiguration(configuration, displayMatris)

    }

    fun checkPermissions(permissions: Array<String>): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }
        for (p in permissions) {
            if ((checkSelfPermission(p) != PackageManager.PERMISSION_GRANTED)) return false
        }
        return true
    }
}
