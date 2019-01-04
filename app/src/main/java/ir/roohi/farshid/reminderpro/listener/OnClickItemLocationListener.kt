package ir.roohi.farshid.reminderpro.listener

import ir.roohi.farshid.reminderpro.model.LocationEntity

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 1/5/19.
 */
interface OnClickItemLocationListener {

    fun onClickItemLocation(position: Int, element: LocationEntity)
}