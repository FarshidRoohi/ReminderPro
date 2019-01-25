package ir.roohi.farshid.reminderpro.views.bottomSheet

import android.annotation.SuppressLint
import android.location.Location
import android.text.Html
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.zarinpal.libs.bottomsheet.BottomSheetModal
import com.zarinpal.libs.views.ZarinButton
import ir.roohi.farshid.reminderpro.R
import ir.roohi.farshid.reminderpro.customViews.CustomInputEditText
import ir.roohi.farshid.reminderpro.listener.OnInformationLocationListener
import ir.roohi.farshid.reminderpro.model.LocationEntity


/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 1/5/19.
 */
@SuppressLint("ValidFragment")
class InformationLocationBottomSheet constructor(fm: FragmentManager, val listener: OnInformationLocationListener) :
    BottomSheetModal(fm) {

    var modelMap: LocationEntity? = null
    var myLocation: Location? = null
    var selectLocation: Location? = null

    private val step = 10
    private val max = 1500
    private val min = 50
    private var meter: Int = 100

    override fun getLayout(): Int {
        return R.layout.boottom_sheet_location_info
    }


    override fun getView(view: View?) {

        val btnOk = view!!.findViewById<ZarinButton>(R.id.btnOk)
        val txtDistance = view.findViewById<TextView>(R.id.txtDistance)
        val edtTitle = view.findViewById<CustomInputEditText>(R.id.edtTitle)
        val edtDesc = view.findViewById<CustomInputEditText>(R.id.edtDesc)
        val seekBar = view.findViewById<SeekBar>(R.id.seekBar)
        val txtDistanceMeterLocationSelect = view.findViewById<TextView>(R.id.txtDistanceLocation)
        txtDistance.text = String.format(getString(R.string.distance_value), 100)

        seekBar.max = (max - min) / step
        seekBar.progress = meter

        if (modelMap != null) {
            edtTitle.text = modelMap!!.title
            edtDesc.text = modelMap!!.text.toString()
            txtDistance.text = String.format(getString(R.string.distance_value), modelMap!!.distance)
            meter = modelMap!!.distance
        }
        if (myLocation != null && selectLocation != null) {
            val distance: Int = myLocation!!.distanceTo(selectLocation!!).toInt()

            val text = Html.fromHtml(
                getString(R.string.distance_my_location) + "   <b>  "
                        + distance + "  </b>   " + getString(R.string.meter)
            )

            txtDistanceMeterLocationSelect.text = text
        }

        edtTitle.edt!!.imeOptions = EditorInfo.IME_ACTION_NEXT
        edtTitle.edt!!.setSingleLine(true)
        edtDesc.edt!!.maxLines = 2

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                meter = min + (progress * step)
                txtDistance.text = String.format(getString(R.string.distance_value), meter)
            }

        })


        edtTitle.edt!!.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                edtTitle.clearFocus()
                edtDesc.edt!!.requestFocus()
            }
            false
        }

        edtDesc.edt!!.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                validation(edtTitle.text, edtDesc.text, meter)
            }
            false
        }
        btnOk.setOnClickListener {
            validation(edtTitle.text, edtDesc.text, meter)
        }

    }

    private fun validation(title: String, desc: String, distance: Int) {

        if (title.isEmpty()) {
            Toast.makeText(context, getString(R.string.error_empty_title), Toast.LENGTH_SHORT).show()
            return
        }

        listener.onInformationLocation(title, desc, distance)
        dismissAllowingStateLoss()

    }


}