package ir.roohi.farshid.reminderpro.views.activities

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Build
import android.os.Bundle
import android.support.annotation.ColorRes
import android.support.annotation.LayoutRes
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import ir.roohi.farshid.reminderpro.ResourceApplication
import java.util.*

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2018.
 */
@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    private var listener: OnPermissionRequestListener? = null

    companion object {

        private const val PERMISSION_REQUEST_ID = 901
    }

    var resourceApp: ResourceApplication? = null
        private set


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.resourceApp = ResourceApplication.applicationResource

    }

    fun setContentView(@LayoutRes layoutResID: Int, @ColorRes color: Int) {
        super.setContentView(layoutResID)
        setStatusBarColor(color)
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


    @TargetApi(Build.VERSION_CODES.M)
    fun checkPermissions(permissions: Array<String>): Boolean {
        for (p in permissions) {
            if ((checkSelfPermission(p) != PackageManager.PERMISSION_GRANTED)) return false
        }
        return true
    }

    interface OnPermissionRequestListener {
        fun onAllow(permission: String)

        fun onDenied(permission: String)
    }

}
