package ir.roohi.farshid.reminderpro.listener

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 1/4/19.
 */
interface OnPermissionRequestListener {

    fun onAllow(permission: String)

    fun onDenied(permission: String)
}