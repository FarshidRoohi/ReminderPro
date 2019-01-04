package ir.roohi.farshid.reminderpro.views.bottomSheet

import android.annotation.SuppressLint
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.zarinpal.libs.bottomsheet.BottomSheetModal
import com.zarinpal.libs.views.ZarinButton
import ir.roohi.farshid.reminderpro.R
import ir.roohi.farshid.reminderpro.customViews.CustomInputEditText
import ir.roohi.farshid.reminderpro.listener.OnInformationLocationListener

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 1/5/19.
 */
@SuppressLint("ValidFragment")
class InformationLocationBottomSheet constructor(fm: FragmentManager, val listener: OnInformationLocationListener) : BottomSheetModal(fm) {


    override fun getView(view: View?) {

        val btnOk = view!!.findViewById<ZarinButton>(R.id.btnOk)
        val edtTitle = view.findViewById<CustomInputEditText>(R.id.edtTitle)
        val edtDesc = view.findViewById<CustomInputEditText>(R.id.edtDesc)

        edtTitle.edt!!.imeOptions = EditorInfo.IME_ACTION_DONE
        edtTitle.edt!!.setSingleLine(true)
        edtTitle.edt!!.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                edtTitle.clearFocus()
                edtDesc.requestFocus()
            }
            false
        }

        edtDesc.edt!!.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                validation(edtTitle.text, edtDesc.text)
            }
            false
        }
        btnOk.setOnClickListener {
            validation(edtTitle.text, edtDesc.text)
        }

    }

    override fun getLayout(): Int {
        return R.layout.boottom_sheet_location_info
    }

    private fun validation(title: String, desc: String) {

        if (title.isEmpty()) {
            Toast.makeText(context, getString(R.string.error_empty_title), Toast.LENGTH_SHORT).show()
            return
        }

        listener.onInformationLocation(title, desc)
        dismissAllowingStateLoss()

    }


}