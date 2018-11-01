package ir.roohi.farshid.reminderpro.views.activities

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Build
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import ir.roohi.farshid.reminderpro.ResourceApplication
import java.util.*

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2018.
 */
@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {
    private var listener: OnPermissionRequestListener? = null

    var resourceApp: ResourceApplication? = null
        private set


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.resourceApp = ResourceApplication.applicationResource

    }


    fun <T : ViewDataBinding> setBindingView(@LayoutRes layout: Int): T {
        return DataBindingUtil.setContentView(this, layout)
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
            if (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
                this.listener!!.onAllow(permission)
                continue
            }
            permissionList.add(permission)
        }

        if (permissionList.size <= 0) {
            return
        }
        requestPermissions(
                permissionList.toTypedArray(), PERMISSION_REQUEST_ID
        )


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

    interface OnPermissionRequestListener {
        fun onAllow(permission: String)

        fun onDenied(permission: String)
    }

    companion object {

        private const val PERMISSION_REQUEST_ID = 901
    }
}
