package ir.roohi.farshid.reminderpro.utility

import android.view.View
import androidx.databinding.BindingAdapter

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 4/3/20.
 */

@BindingAdapter("app:isVisible")
fun isVisible(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

//@BindingAdapter("app:toastMessage")
//fun showToastMessage(view: View, strID: Int) {
//    if (strID == 0) {
//        return
//    }
//    val msg = view.context.getString(strID)
//    view.context.showMsg(msg)
//}
