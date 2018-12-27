package ir.roohi.farshid.reminderpro.views.bottomSheet

import android.annotation.SuppressLint
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.zarinpal.libs.bottomsheet.BottomSheetModal
import com.zarinpal.libs.views.ZarinButton
import ir.roohi.farshid.reminderpro.R
import ir.roohi.farshid.reminderpro.customViews.CustomInputEditText

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 12/26/18.
 */
@SuppressLint("ValidFragment")
class NameBottomSheet constructor(fm: FragmentManager) : BottomSheetModal(fm) {

    var listener: OnTitleListener? = null

    override fun getView(view: View?) {
        val btnOk = view!!.findViewById<ZarinButton>(R.id.btnOk)
        val edtTitle = view.findViewById<CustomInputEditText>(R.id.edtTitle)
        btnOk.setOnClickListener {

            if (edtTitle.text.isEmpty()) {
                Toast.makeText(context, getString(R.string.error_empty_title), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            listener!!.onTitle(edtTitle.text)
            dismissAllowingStateLoss()
        }
    }

    override fun getLayout(): Int {
        return R.layout.boottom_sheet_name
    }

    interface OnTitleListener {
        fun onTitle(title: String)
    }

}