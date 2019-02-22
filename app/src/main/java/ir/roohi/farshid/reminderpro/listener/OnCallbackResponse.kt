package ir.roohi.farshid.reminderpro.listener

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2/22/19.
 */
interface OnCallbackResponse {
    fun onSuccess(response: String)
    fun onFailure(data: String)
}