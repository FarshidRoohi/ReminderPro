package ir.roohi.farshid.reminderpro.listener

import ir.roohi.farshid.reminderpro.model.LocationEntity

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 1/14/19.
 */
interface OnMultiSelectLocationListener {
    fun onMultiSelectLocation(items: ArrayList<LocationEntity>)
}